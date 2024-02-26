package dev.codex.java.wrapper.runtime;

final class StdLib {
    private StdLib() {
        super();
    }

    static native long malloc(long size);
    static native void free(long address);
}