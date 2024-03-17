package dev.codex.java.wrapper.runtime;

import dev.codex.java.wrapper.exception.IllegalPointerType;
import dev.codex.java.wrapper.type.Pair;
import dev.codex.java.wrapper.type.Pointer;

import java.util.HashMap;
import java.util.Map;

public class PointerFactory {
    private static final Long FPOS_T_SIZE = 16L;
    private static final Long FILE_SIZE = 216L;
    private static final Long STRUCT_IFREQ_SIZE = 40L;

    @FunctionalInterface
    public interface Constructor {
        Pointer construct(Long address, long size);
    }

    public static final Map<Class<?>, Pair<Constructor, Long>> types = new HashMap<>();
    static {
        PointerFactory.types.put(FilePosition.class, Pair.of(FilePosition::new, PointerFactory.FPOS_T_SIZE));
        PointerFactory.types.put(FileStream.class, Pair.of(FileStream::new, PointerFactory.FILE_SIZE));
        PointerFactory.types.put(InterfaceRequest.class, Pair.of(InterfaceRequest::new, PointerFactory.STRUCT_IFREQ_SIZE));
    }

    private PointerFactory() {
        super();
    }

    public static Long size(Class<? extends Pointer> clazz) {
        Long size = PointerFactory.types.get(clazz).right();
        if (size == null) {
            throw new IllegalPointerType(clazz);
        }

        return size;
    }

    static <T extends Pointer> T instantiate(Class<T> clazz, Long address) throws IllegalPointerType {
        return PointerFactory.instantiate(clazz, address, 1);
    }

    static <T extends Pointer> T instantiate(Class<T> clazz, Long address, long n) throws IllegalPointerType {
        Pair<Constructor, Long> pointer = PointerFactory.types.get(clazz);
        if (pointer == null) {
            throw new IllegalPointerType(clazz);
        }

        return clazz.cast(pointer.left().construct(address, pointer.right() * n));
    }

    static <T extends Pointer> T instantiateWith(Class<T> clazz, Long address, long size) throws IllegalPointerType {
        Constructor constructor = PointerFactory.types.get(clazz).left();
        if (constructor == null) {
            throw new IllegalPointerType(clazz);
        }

        return clazz.cast(constructor.construct(address, size));
    }
}