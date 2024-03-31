package dev.codex.java.wrapper.type;

import dev.codex.java.wrapper.runtime.NativeRuntimeWrapper;

public interface Pointer extends AutoCloseable {
    MemoryAddress address();

    long getSize();
    void setSize(long size);

    default void close() {
        NativeRuntimeWrapper.free(this);
    }
}