package dev.codex.java.wrapper.runtime;

import dev.codex.java.wrapper.exception.IllegalArgumentException;
import dev.codex.java.wrapper.exception.InvalidPointerTypeException;
import dev.codex.java.wrapper.type.Pointer;

import java.util.HashMap;
import java.util.Map;

public class PointerFactory {
    private static final Long FILE_SIZE = 216L;
    private static final Long FPOS_T_SIZE = 16L;
    private static final Long STRUCT_IFREQ_SIZE = 40L;

    public static final Map<Class<?>, Long> sizes = new HashMap<>();
    static {
        PointerFactory.sizes.put(FileStream.class, PointerFactory.FILE_SIZE);
        PointerFactory.sizes.put(FileStream.Position.class, PointerFactory.FPOS_T_SIZE);
        PointerFactory.sizes.put(InterfaceRequest.class, PointerFactory.STRUCT_IFREQ_SIZE);
    }

    @FunctionalInterface
    public interface Constructor {
        Pointer construct(Long address, long size);
    }

    public static final Map<Class<?>, Constructor> constructors = new HashMap<>();
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
            throw new InvalidPointerTypeException(clazz);
        }

        return size;
    }

    static <T extends Pointer> PointerBuilder<T> instantiate(Class<T> clazz) throws InvalidPointerTypeException {
        return PointerFactory.instantiate(1, clazz);
    }

    static <T extends Pointer> PointerBuilder<T> instantiate(long n, Class<T> clazz) throws InvalidPointerTypeException {
        return new PointerBuilder<>(clazz, n);
    }

    public static class PointerBuilder<T extends Pointer> {
        private final Class<T> clazz;
        private final Constructor constructor;
        private Long address;
        private long size;

        private PointerBuilder(Class<T> clazz, long n) {
            Constructor constructor = PointerFactory.constructors.get(clazz);
            if (constructor == null) {
                throw new InvalidPointerTypeException(clazz);
            }

            this.clazz = clazz;
            this.constructor = constructor;

            if (n <= 0) {
                throw new IllegalArgumentException("size", "cannot be less than or equal to 0");
            }
            this.size = PointerFactory.sizeof(clazz) * n;
        }

        public PointerBuilder<T> at(Long address) {
            this.address = address;
            return this;
        }

        public PointerBuilder<T> with(long size) {
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