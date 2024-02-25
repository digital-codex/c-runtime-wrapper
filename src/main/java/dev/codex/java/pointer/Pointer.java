package dev.codex.java.pointer;

import dev.codex.java.glibc.GLibWrapper;

public interface Pointer extends AutoCloseable {
    long address();

    default void close() throws Exception {
        GLibWrapper.free(this);
    }
}