package dev.codex.java.runtime.type;

import dev.codex.java.runtime.unix.NativeRuntimeWrapper;

public interface Pointer extends AutoCloseable {
    MemoryAddress address();

    long getSize();
    void setSize(long size);

    default void close() {
        NativeRuntimeWrapper.free(this);
    }
}