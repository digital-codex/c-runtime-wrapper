package dev.codex.java.glibc;

import dev.codex.java.pointer.Pointer;

import java.util.HashMap;
import java.util.Map;

public final class GLibWrapper {
    static {
        System.loadLibrary("glib-wrapper");
    }
    private GLibWrapper() {
        super();
    }

    private static final Map<Class<?>, Long> sizes = new HashMap<>();

    private static final Long FILE_SIZE = 216L;
    private static final Long STRUCT_IFREQ_SIZE = 40L;
    static {
        GLibWrapper.sizes.put(File.class, GLibWrapper.FILE_SIZE);
        GLibWrapper.sizes.put(
                InterfaceRequest.class, GLibWrapper.STRUCT_IFREQ_SIZE
        );
    }

    @FunctionalInterface
    private interface Constructor {
        Pointer construct(long address);
    }
    private static final Map<Class<?>, Constructor> constructors
            = new HashMap<>();
    static {
        GLibWrapper.RegisterConstructor(File.class, File::new);
        GLibWrapper.RegisterConstructor(
                InterfaceRequest.class, InterfaceRequest::new
        );
    }

    private static <T> void RegisterConstructor(Class<T> type, Constructor constructor) {
        GLibWrapper.constructors.put(type, constructor);
    }

    // stdio.h
    public static File fopen(String filename, String modes) {
        return new File(StdIO.fopen(filename, modes));
    }

    public static long fread(Pointer ptr, long size, long n, File stream) {
        return StdIO.fread(ptr.address(), size, n, stream.address());
    }

    public static long fwrite(Pointer ptr, long size, long n, File stream) {
        return StdIO.fwrite(ptr.address(), size, n, stream.address());
    }

    public static int fseek(File stream, long off, int whence) {
        return StdIO.fseek(stream.address(), off, whence);
    }

    public static int ftell(File stream) {
        return StdIO.ftell(stream.address());
    }

    public static void rewind(File stream) {
        StdIO.rewind(stream.address());
    }

    // stdlib.h
    public static <T> Pointer malloc(Class<T> type) {
        return GLibWrapper.malloc(type, 1);
    }

    //TODO(treyvon): param validation i.e multiple > 1 and array type
    //TODO(treyvon): null check on map.get()
    public static <T> Pointer malloc(Class<T> type, int multiple) {
        return GLibWrapper.constructors.get(type)
                .construct(
                        StdLib.malloc(
                                GLibWrapper.sizes.get(type) * multiple
                        )
                );
    }

    public static void free(Pointer pointer) {
        StdLib.free(pointer.address());
    }
}