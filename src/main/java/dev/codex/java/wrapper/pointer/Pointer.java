package dev.codex.java.wrapper.pointer;

import dev.codex.java.wrapper.runtime.CRuntimeWrapper;

public interface Pointer extends AutoCloseable {
    long address();

    default void close() throws Exception {
        CRuntimeWrapper.free(this);
    }
}