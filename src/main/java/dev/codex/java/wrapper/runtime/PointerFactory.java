package dev.codex.java.wrapper.runtime;

import dev.codex.java.wrapper.exception.IllegalArgumentException;
import dev.codex.java.wrapper.exception.InvalidPointerTypeException;
import dev.codex.java.wrapper.type.MemoryAddress;
import dev.codex.java.wrapper.type.Pointer;

import java.util.HashMap;
import java.util.Map;

public class PointerFactory {
    public static final Map<Class<?>, Long> sizes = new HashMap<>();
    static {
        PointerFactory.sizes.put(FileStream.class, 216L);
        PointerFactory.sizes.put(FileStream.Position.class, 16L);
        PointerFactory.sizes.put(InterfaceRequest.class, 40L);
    }

    @FunctionalInterface
    private interface Constructor {
        Pointer construct(MemoryAddress address, long size);
    }

    private static final Map<Class<?>, Constructor> constructors = new HashMap<>();
    static {
        PointerFactory.constructors.put(FileStream.class, FileStream::new);
        PointerFactory.constructors.put(FileStream.Position.class, FileStream.Position::new);
        PointerFactory.constructors.put(InterfaceRequest.class, InterfaceRequest::new);
    }

    private PointerFactory() {
        super();
    }

    public static Long sizeof(Class<? extends Pointer> clazz) {
        Long size = PointerFactory.sizes.get(clazz);
        if (size == null) {
            throw new InvalidPointerTypeException("clazz", clazz);
        }

        return size;
    }

    public static <T extends Pointer> PointerBuilder<T> instantiate(Class<T> clazz) throws InvalidPointerTypeException {
        return PointerFactory.instantiate(1, clazz);
    }

    public static <T extends Pointer> PointerBuilder<T> instantiate(long n, Class<T> clazz) throws InvalidPointerTypeException {
        return new PointerBuilder<>(n, clazz);
    }

    public static class PointerBuilder<T extends Pointer> {
        private final Class<T> clazz;
        private final Constructor constructor;
        private MemoryAddress address;
        private long size;

        private PointerBuilder(long n, Class<T> clazz) {
            Constructor constructor = PointerFactory.constructors.get(clazz);
            if (constructor == null) {
                throw new InvalidPointerTypeException("clazz", clazz);
            }

            this.clazz = clazz;
            this.constructor = constructor;

            if (n <= 0) {
                throw new IllegalArgumentException("size", "cannot be less than or equal to 0");
            }
            this.size = PointerFactory.sizeof(clazz) * n;
        }

        public PointerBuilder<T> with(MemoryAddress address) {
            this.address = address;
            return this;
        }

        public PointerBuilder<T> with(MemoryAddress address, long size) {
            this.address = address;
            if (size <= 0) {
                throw new IllegalArgumentException("size", "cannot be less than or equal to 0");
            }

            this.size = size;
            return this;
        }

        public T build() {
            return this.clazz.cast(this.constructor.construct(this.address, this.size));
        }
    }
}