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

    public static Pointer malloc(Class<? extends Pointer> clazz) throws Error {
        Long ptr = StandardLibrary.malloc(PointerFactory.sizeof(clazz));
        if (ptr == null) {
            throw CRuntimeWrapper.strerror();
        }

        return PointerFactory.instantiate(clazz).at(ptr).build();
    }

    public static void free(Pointer pointer) {
        StandardLibrary.free(pointer.address());
    }

    public static Pointer calloc(long nmemb, Class<? extends Pointer> clazz) throws Error {
        if (nmemb <= 0) {
            throw Error.ILLEGAL_NUMBER_OF_ELEMENTS;
        }

        Long ptr = StandardLibrary.calloc(nmemb, PointerFactory.sizeof(clazz));
        if (ptr == null) {
            throw CRuntimeWrapper.strerror();
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
            throw CRuntimeWrapper.strerror();
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
            throw CRuntimeWrapper.strerror();
        }
        return PointerFactory.instantiate(FileStream.class).at(address).build();
    }

    public static FileStream fdopen(FileDescriptor fd, AccessMode mode) {
        AccessAssociation association = AccessAssociation.associations.getOrDefault(mode, AccessAssociation.READ);
        int value = fd.mode().value() | OptionFlag.of(fd.options()).value();
        for (OptionFlag option : association.options()) {
            if ((value & option.value()) == 0) throw Error.INCOMPATIBLE_ACCESS_ASSOCIATION;
        }
        Long address = StandardIO.fdopen(fd.fd(), mode.value());
        if (address == null) {
            throw CRuntimeWrapper.strerror();
        }
        return PointerFactory.instantiate(FileStream.class).at(address).build();
    }

    public static FileStream freopen(String pathname, AccessMode mode, FileStream stream) {
        Long address = StandardIO.freopen(pathname, mode.value(), stream.address());
        if (address == null) {
            throw CRuntimeWrapper.strerror();
        }
        return PointerFactory.instantiate(FileStream.class).at(address).build();
    }

    public static void fclose(FileStream stream) {
        int error = StandardIO.fclose(stream.address());
        if (error == -1) {
            throw CRuntimeWrapper.strerror();
        }
    }

    public static long fread(Pointer ptr, long size, long n, FileStream stream) {
        return StandardIO.fread(ptr.address(), size, n, stream.address());
    }

    public static long fwrite(Pointer ptr, long size, long n, FileStream stream) {
        return StandardIO.fwrite(ptr.address(), size, n, stream.address());
    }

    public static int fseek(FileStream stream, long off, int whence) {
        return StandardIO.fseek(stream.address(), off, whence);
    }

    public static long ftell(FileStream stream) {
        return StandardIO.ftell(stream.address());
    }

    public static void rewind(FileStream stream) {
        StandardIO.rewind(stream.address());
    }

    public static FileDescriptor open(String pathname, OptionFlag...flags) throws Error {
        return CRuntimeWrapper.open(pathname, AccessFlag.READ_ONLY, flags);
    }

    public static FileDescriptor open(String pathname, AccessFlag mode, OptionFlag...flags) throws Error {
        int fd = FileControl.open(pathname, OptionFlag.of(flags).value() | mode.value());
        if (fd == -1) {
            throw CRuntimeWrapper.strerror();
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
        int fd = FileControl.openat(dirfd.fd(), pathname, OptionFlag.of(flags).value() | mode.value());
        if (fd == -1) {
            throw CRuntimeWrapper.strerror();
        }

        return new FileDescriptor(fd, mode, flags);
    }

    // unistd.h
    public static int close(FileDescriptor fd) {
        return UniStd.close(fd.fd());
    }

    public static Error strerror() {
        return new Error(null);
    }
}