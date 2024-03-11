package dev.codex.java.wrapper.runtime;

import dev.codex.java.wrapper.pointer.Pointer;

import java.util.HashMap;
import java.util.Map;

public final class CRuntimeWrapper {
    static {
        System.loadLibrary("glib-wrapper");
    }
    private CRuntimeWrapper() {
        super();
    }

    private static final Map<Class<?>, Long> sizes = new HashMap<>();

    private static final Long FILE_SIZE = 216L;
    private static final Long STRUCT_IFREQ_SIZE = 40L;
    static {
        CRuntimeWrapper.sizes.put(FileStream.class, CRuntimeWrapper.FILE_SIZE);
        CRuntimeWrapper.sizes.put(
                InterfaceRequest.class, CRuntimeWrapper.STRUCT_IFREQ_SIZE
        );
    }

    @FunctionalInterface
    private interface Constructor {
        Pointer construct(long address);
    }
    private static final Map<Class<?>, Constructor> constructors = new HashMap<>();
    static {
        CRuntimeWrapper.RegisterConstructor(FileStream.class, FileStream::new);
        CRuntimeWrapper.RegisterConstructor(
                InterfaceRequest.class, InterfaceRequest::new
        );
    }

    private static <T> void RegisterConstructor(Class<T> type, Constructor constructor) {
        CRuntimeWrapper.constructors.put(type, constructor);
    }

    // stdio.h
    public static int fclose(FileStream stream) {
        return StdIO.fclose(stream.address());
    }

    public static FileStream fopen(String filename, String modes) {
        return new FileStream(StdIO.fopen(filename, modes));
    }

    public static long fread(Pointer ptr, long size, long n, FileStream stream) {
        return StdIO.fread(ptr.address(), size, n, stream.address());
    }

    public static long fwrite(Pointer ptr, long size, long n, FileStream stream) {
        return StdIO.fwrite(ptr.address(), size, n, stream.address());
    }

    public static int fseek(FileStream stream, long off, int whence) {
        return StdIO.fseek(stream.address(), off, whence);
    }

    public static int ftell(FileStream stream) {
        return StdIO.ftell(stream.address());
    }

    public static void rewind(FileStream stream) {
        StdIO.rewind(stream.address());
    }

    // stdlib.h
    public static <T> Pointer malloc(Class<T> type) {
        return CRuntimeWrapper.malloc(type, 1);
    }

    //TODO(treyvon): param validation i.e multiple > 1 and array type
    //TODO(treyvon): null check on map.get()
    public static <T> Pointer malloc(Class<T> type, int multiple) {
        return CRuntimeWrapper.constructors.get(type)
                .construct(
                        StdLib.malloc(
                                CRuntimeWrapper.sizes.get(type) * multiple
                        )
                );
    }

    public static void free(Pointer pointer) {
        StdLib.free(pointer.address());
    }

    // fcntl.h
    public static int open(String file, Flag ...flags) {
        return CRuntimeWrapper.open(file, AccessMode.READ_ONLY, flags);
    }

    public static int open(String file, AccessMode mode, Flag ...flags) {
        int oflag = 0;
        for (Flag flag : flags)
            oflag = oflag | flag.value();

        return FCntl.open(file, oflag | mode.value());
    }
}