package dev.codex.java.wrapper.runtime;

import dev.codex.java.wrapper.type.Pointer;
import dev.codex.java.wrapper.type.Error;

import java.util.Objects;

public final class CRuntimeWrapper {
    static {
        System.load("/home/treyvon/src/c-runtime-wrapper/target/library/libCRuntimeWrapper.so");
    }
    private CRuntimeWrapper() {
        super();
    }

    public static <T extends Pointer> T malloc(Class<T> clazz) throws Error {
        Long ptr = StandardLibrary.malloc(PointerFactory.sizeof(clazz));
        if (ptr == null) {
            throw CRuntimeWrapper.perror("malloc");
        }

        return PointerFactory.instantiate(clazz).at(ptr).build();
    }

    public static void free(Pointer pointer) {
        StandardLibrary.free(pointer.address());
    }

    public static <T extends Pointer> T calloc(long nmemb, Class<T> clazz) throws Error {
        if (nmemb <= 0) {
            throw Error.ILLEGAL_NUMBER_OF_ELEMENTS;
        }

        Long ptr = StandardLibrary.calloc(nmemb, PointerFactory.sizeof(clazz));
        if (ptr == null) {
            throw CRuntimeWrapper.perror("calloc");
        }

        return PointerFactory.instantiate(nmemb, clazz).at(ptr).build();
    }

    public static Pointer realloc(Pointer ptr, long size) throws Error {
        Long oldAddress = ptr.address();
        if (oldAddress == null) {
            return CRuntimeWrapper.malloc(ptr.getClass());
        }

        if (size == 0) {
            CRuntimeWrapper.free(ptr);
            return null;
        }

        Long newAddress = StandardLibrary.realloc(oldAddress, size);
        if (newAddress == null) {
            throw CRuntimeWrapper.perror("realloc");
        }

        if (Objects.equals(oldAddress, newAddress)) {
            ptr.setSize(size);
            return ptr;
        }

        return PointerFactory.instantiate(ptr.getClass()).at(newAddress).with(size).build();
    }

    public static Pointer reallocarray(Pointer ptr, long nmemb, long size) throws Error {
        return CRuntimeWrapper.realloc(ptr, nmemb * size);
    }

    public static FileStream fopen(String pathname, AccessMode mode) throws Error {
        Long address = StandardIO.fopen(pathname, mode.value());
        if (address == null) {
            throw CRuntimeWrapper.perror("fopen");
        }
        return PointerFactory.instantiate(FileStream.class).at(address).build();
    }

    public static FileStream fdopen(FileDescriptor fd, AccessMode mode) throws Error {
        AccessAssociation association = AccessAssociation.associations.getOrDefault(mode, AccessAssociation.READ);

        if (association.mode().value() != AccessFlag.READ_WRITE.value() && association.mode().value() != fd.mode().value()) {
            throw Error.INCOMPATIBLE_ACCESS_ASSOCIATION;
        }

        int value = OptionFlag.of(fd.options());
        for (OptionFlag option : association.options()) {
            if ((value & option.value()) == 0) throw Error.INCOMPATIBLE_ACCESS_ASSOCIATION;
        }
        Long address = StandardIO.fdopen(fd.fd(), mode.value());
        if (address == null) {
            throw CRuntimeWrapper.perror("fdopen");
        }
        return PointerFactory.instantiate(FileStream.class).at(address).build();
    }

    public static FileStream freopen(String pathname, AccessMode mode, FileStream stream) throws Error {
        Long address = StandardIO.freopen(pathname, mode.value(), stream.address());
        if (address == null) {
            throw CRuntimeWrapper.perror("freopen");
        }
        return PointerFactory.instantiate(FileStream.class).at(address).build();
    }

    public static void fclose(FileStream stream) throws Error {
        if (StandardIO.fclose(stream.address()) < 0) {
            throw CRuntimeWrapper.perror("fclose");
        }
    }

    //TODO: validation on size <= ptr.length
    public static long fread(byte[] ptr, long size, long nmemb, FileStream stream) throws Error {
        long n = StandardIO.fread(ptr, size, nmemb, stream.address());
        if (n < 0) {
            throw CRuntimeWrapper.perror("fread");
        }
        return n;
    }

    public static long fwrite(byte[] ptr, long size, long nmemb, FileStream stream) throws Error {
        long n = StandardIO.fwrite(ptr, size, nmemb, stream.address());
        if (n < 0) {
            throw CRuntimeWrapper.perror("fwrite");
        }
        return n;
    }

    public static void fseek(FileStream stream, long offset, Whence whence) throws Error {
        if (StandardIO.fseek(stream.address(), offset, whence.ordinal()) < 0) {
            throw CRuntimeWrapper.perror("fseek");
        }
    }

    public static long ftell(FileStream stream) throws Error {
        long pos = StandardIO.ftell(stream.address());
        if (pos < 0) {
            throw CRuntimeWrapper.perror("ftell");
        }
        return pos;
    }

    public static void rewind(FileStream stream) {
        CRuntimeWrapper.fseek(stream, 0, Whence.SEEK_SET);
    }

    public static void fgetpos(FileStream stream, FileStream.Position pos) throws Error {
        if (StandardIO.fgetpos(stream.address(), pos.address()) < 0) {
            throw CRuntimeWrapper.perror("fgetpos");
        }
    }

    public static void fsetpos(FileStream stream, FileStream.Position pos) {
        if (StandardIO.fsetpos(stream.address(), pos.address()) < 0) {
            throw CRuntimeWrapper.perror("fsetpos");
        }
    }

    public static FileDescriptor open(String pathname, OptionFlag...flags) throws Error {
        return CRuntimeWrapper.open(pathname, AccessFlag.READ_ONLY, flags);
    }

    public static FileDescriptor open(String pathname, AccessFlag mode, OptionFlag...flags) throws Error {
        int fd = FileControl.open(pathname, OptionFlag.of(flags) | mode.value());
        if (fd < 0) {
            throw CRuntimeWrapper.perror("open");
        }

        return new FileDescriptor(fd, mode, flags);
    }

    public static FileDescriptor creat(String pathname) throws Error {
        return CRuntimeWrapper.creat(pathname, AccessFlag.WRITE_ONLY);
    }

    public static FileDescriptor creat(String pathname, AccessFlag mode) throws Error {
        return CRuntimeWrapper.open(pathname, mode, OptionFlag.CREAT, OptionFlag.TRUNCATE);
    }

    public static FileDescriptor openat(FileDescriptor dirfd, String pathname, OptionFlag... flags) throws Error {
        return CRuntimeWrapper.openat(dirfd, pathname, AccessFlag.READ_ONLY, flags);
    }

    public static FileDescriptor openat(FileDescriptor dirfd, String pathname, AccessFlag mode, OptionFlag... flags) throws Error {
        int fd = FileControl.openat(dirfd.fd(), pathname, OptionFlag.of(flags) | mode.value());
        if (fd < 0) {
            throw CRuntimeWrapper.perror("openat");
        }

        return new FileDescriptor(fd, mode, flags);
    }

    public static void close(FileDescriptor fd) throws Error {
        if (UnixStandard.close(fd.fd()) < 0) {
            throw CRuntimeWrapper.perror("close");
        }
    }

    public static Error perror(String s) {
        String msg = new String(Strings.strerror());
        if (s != null && !s.equals("\0")) {
            msg = Error.MESSAGE_FORMAT.formatted(s, msg);
            return new Error(msg);
        }
        return new Error(msg);
    }
}