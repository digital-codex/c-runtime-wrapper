package dev.codex.java.runtime.unix;

import dev.codex.java.runtime.exception.IllegalArgumentException;
import dev.codex.java.runtime.exception.InvalidClassTypeException;
import dev.codex.java.runtime.type.MemoryAddress;
import dev.codex.java.runtime.type.Pointer;

import java.util.HashMap;
import java.util.Map;

public class PointerFactory {
    private static final Map<Class<?>, Long> sizes = new HashMap<>();
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
            throw new InvalidClassTypeException("clazz", clazz);
        }

        return size;
    }

    static <T extends Pointer> PointerBuilder<T> instantiate(Class<T> clazz) throws InvalidClassTypeException {
        return PointerFactory.instantiate(1, clazz);
    }

    static <T extends Pointer> PointerBuilder<T> instantiate(long n, Class<T> clazz) throws InvalidClassTypeException {
        return new PointerBuilder<>(n, clazz);
    }

    static class PointerBuilder<T extends Pointer> {
        private final Class<T> clazz;
        private final Constructor constructor;
        private MemoryAddress address;
        private long size;

        private PointerBuilder(long n, Class<T> clazz) {
            Constructor constructor = PointerFactory.constructors.get(clazz);
            if (constructor == null) {
                throw new InvalidClassTypeException("clazz", clazz);
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