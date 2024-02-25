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

    // stdlib.h
    //TODO(treyvon): param validation i.e non array type
    //TODO(treyvon): null check on map.get()
    public static <T> Pointer malloc(Class<T> type) {
        return GLibWrapper.constructors.get(type).construct(
                StdLib.malloc(GLibWrapper.sizes.get(type))
        );
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