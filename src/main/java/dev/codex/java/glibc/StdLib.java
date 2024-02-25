package dev.codex.java.glibc;

final class StdLib {
    private StdLib() {
        super();
    }

    static native long malloc(long size);
    static native void free(long address);
}