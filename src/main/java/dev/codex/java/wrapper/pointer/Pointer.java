package dev.codex.java.wrapper.pointer;

import dev.codex.java.wrapper.runtime.GLibCWrapper;

public interface Pointer extends AutoCloseable {
    long address();

    default void close() throws Exception {
        GLibCWrapper.free(this);
    }
}