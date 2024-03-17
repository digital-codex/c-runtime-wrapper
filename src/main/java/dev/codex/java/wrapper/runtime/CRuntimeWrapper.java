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

    // stdio.h
    public static int fclose(FileStream stream) {
        return StandardIO.fclose(stream.address());
    }

    public static FileStream fopen(String filename, String modes) {
        return new FileStream(StandardIO.fopen(filename, modes), PointerFactory.sizeof(FileStream.class));
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


    // fcntl.h
    public static int open(String file, OptionFlag...flags) {
        return CRuntimeWrapper.open(file, AccessMode.READ_ONLY, flags);
    }

    public static int open(String file, AccessMode mode, OptionFlag...flags) {
        return FileControl.open(file, OptionFlag.of(flags).value() | mode.value());
    }

    // unistd.h
    public static int close(int fd) {
        return UniStd.close(fd);
    }

    public static Error strerror() {
        return new Error(null);
    }
}