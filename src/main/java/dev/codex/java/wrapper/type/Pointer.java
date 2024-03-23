package dev.codex.java.wrapper.type;

import dev.codex.java.wrapper.runtime.CRuntimeWrapper;

public interface Pointer extends AutoCloseable {
    MemoryAddress address();

    long getSize();
    void setSize(long size);

    default void close() {
        CRuntimeWrapper.free(this);
    }
}