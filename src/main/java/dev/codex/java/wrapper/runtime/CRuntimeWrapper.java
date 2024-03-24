package dev.codex.java.wrapper.runtime;

import dev.codex.java.wrapper.exception.IllegalArgumentException;
import dev.codex.java.wrapper.exception.InvalidBufferLengthException;
import dev.codex.java.wrapper.type.MemoryAddress;
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
        MemoryAddress ptr = MemoryAddress.of(StandardLibrary.malloc(PointerFactory.sizeof(clazz)));
        if (MemoryAddress.NULL.equals(ptr)) {
            throw CRuntimeWrapper.perror("malloc");
        }

        return PointerFactory.instantiate(clazz).with(ptr).build();
    }

    public static void free(Pointer pointer) {
        StandardLibrary.free(pointer.address().value());
    }

    public static <T extends Pointer> T calloc(long nmemb, Class<T> clazz) throws Error {
        if (nmemb <= 0) {
            throw Error.ILLEGAL_NUMBER_OF_ELEMENTS;
        }

        MemoryAddress ptr = MemoryAddress.of(StandardLibrary.calloc(nmemb, PointerFactory.sizeof(clazz)));
        if (MemoryAddress.NULL.equals(ptr)) {
            throw CRuntimeWrapper.perror("calloc");
        }

        return PointerFactory.instantiate(nmemb, clazz).with(ptr).build();
    }

    public static Pointer realloc(Pointer ptr, long size) throws Error {
        MemoryAddress oldAddress = ptr.address();
        if (MemoryAddress.NULL.equals(oldAddress)) {
            return CRuntimeWrapper.malloc(ptr.getClass());
        }

        if (size == 0) {
            CRuntimeWrapper.free(ptr);
            return null;
        }

        MemoryAddress newAddress = MemoryAddress.of(StandardLibrary.realloc(oldAddress.value(), size));
        if (MemoryAddress.NULL.equals(newAddress)) {
            throw CRuntimeWrapper.perror("realloc");
        }

        if (Objects.equals(oldAddress, newAddress)) {
            ptr.setSize(size);
            return ptr;
        }

        return PointerFactory.instantiate(ptr.getClass()).with(newAddress, size).build();
    }

    public static Pointer reallocarray(Pointer ptr, long nmemb, long size) throws Error {
        return CRuntimeWrapper.realloc(ptr, nmemb * size);
    }

    public static FileStream fopen(String pathname, AccessMode mode) throws Error {
        MemoryAddress address = MemoryAddress.of(StandardIO.fopen(pathname, mode.value()));
        if (MemoryAddress.NULL.equals(address)) {
            throw CRuntimeWrapper.perror("fopen");
        }
        return PointerFactory.instantiate(FileStream.class).with(address).build();
    }

    public static FileStream fdopen(FileDescriptor fd, AccessMode mode) throws Error {
        AccessAssociation association = AccessAssociation.associations.getOrDefault(mode, AccessAssociation.READ);

        if (!association.validateMode(fd.mode()) || !association.validateFlags(fd.options())) {
            throw Error.INCOMPATIBLE_ACCESS_ASSOCIATION;
        }

        MemoryAddress address = MemoryAddress.of(StandardIO.fdopen(fd.fd(), mode.value()));
        if (MemoryAddress.NULL.equals(address)) {
            throw CRuntimeWrapper.perror("fdopen");
        }
        return PointerFactory.instantiate(FileStream.class).with(address).build();
    }

    public static FileStream freopen(String pathname, AccessMode mode, FileStream stream) throws Error {
        MemoryAddress address = MemoryAddress.of(StandardIO.freopen(pathname, mode.value(), stream.address().value()));
        if (MemoryAddress.NULL.equals(address)) {
            throw CRuntimeWrapper.perror("freopen");
        }
        return PointerFactory.instantiate(FileStream.class).with(address).build();
    }

    public static void fclose(FileStream stream) throws Error {
        if (StandardIO.fclose(stream.address().value()) < 0) {
            throw CRuntimeWrapper.perror("fclose");
        }
    }

    public static long fread(byte[] ptr, long size, long nmemb, FileStream stream) throws Error {
        validateBufferLength(size, ptr.length, "size", "greater than `ptr` length");

        long n = StandardIO.fread(ptr, size, nmemb, stream.address().value());
        if (n < 0) {
            throw CRuntimeWrapper.perror("fread");
        }
        return n;
    }

    public static long fwrite(byte[] ptr, long size, long nmemb, FileStream stream) throws Error {
        validateBufferLength(size, ptr.length, "size", "greater than `ptr` length");

        long n = StandardIO.fwrite(ptr, size, nmemb, stream.address().value());
        if (n < 0) {
            throw CRuntimeWrapper.perror("fwrite");
        }
        return n;
    }

    public static void fseek(FileStream stream, long offset, Whence whence) throws Error {
        if (StandardIO.fseek(stream.address().value(), offset, whence.value()) < 0) {
            throw CRuntimeWrapper.perror("fseek");
        }
    }

    public static long ftell(FileStream stream) throws Error {
        long pos = StandardIO.ftell(stream.address().value());
        if (pos < 0) {
            throw CRuntimeWrapper.perror("ftell");
        }
        return pos;
    }

    public static void rewind(FileStream stream) throws Error {
        CRuntimeWrapper.fseek(stream, 0, Whence.SEEK_SET);
    }

    public static void fgetpos(FileStream stream, FileStream.Position pos) throws Error {
        if (StandardIO.fgetpos(stream.address().value(), pos.address().value()) < 0) {
            throw CRuntimeWrapper.perror("fgetpos");
        }
    }

    public static void fsetpos(FileStream stream, FileStream.Position pos) throws Error {
        if (StandardIO.fsetpos(stream.address().value(), pos.address().value()) < 0) {
            throw CRuntimeWrapper.perror("fsetpos");
        }
    }

    public static FileDescriptor open(String pathname, OptionFlag...flags) throws Error {
        return CRuntimeWrapper.open(pathname, AccessFlag.READ_ONLY, flags);
    }

    public static FileDescriptor open(String pathname, AccessFlag mode, OptionFlag...flags) throws Error {
        int fd = FileControl.open(pathname, OptionFlagMask.valueOf(flags) | mode.value());
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
        int fd = FileControl.openat(dirfd.fd(), pathname, OptionFlagMask.valueOf(flags) | mode.value());
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

    public static long read(FileDescriptor fd, byte[] buf, long count) throws Error {
        validateBufferLength(count, buf.length, "count", "greater than `buf` length");

        long n = UnixStandard.read(fd.fd(), buf, count);
        if (n < 0) {
            throw CRuntimeWrapper.perror("read");
        }
        return n;
    }

    public static long write(FileDescriptor fd, byte[] buf, long count) throws Error {
        validateBufferLength(count, buf.length, "count", "greater than `buf` length");

        long n = UnixStandard.write(fd.fd(), buf, count);
        if (n < 0) {
            throw CRuntimeWrapper.perror("write");
        }
        return n;
    }

    public static Error perror(String s) {
        String msg = new String(Strings.strerror());
        if (s != null && !s.equals("\0")) {
            msg = Error.MESSAGE_FORMAT.formatted(s, msg);
            return new Error(msg);
        }
        return new Error(msg);
    }

    private static void validateBufferLength(long expected, long actual, String arg, String msg) {
        if (expected > actual) {
            throw new InvalidBufferLengthException(arg, msg);
        }
    }
}