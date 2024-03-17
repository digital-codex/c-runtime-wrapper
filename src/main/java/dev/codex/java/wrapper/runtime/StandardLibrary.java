package dev.codex.java.wrapper.runtime;

final class StandardLibrary {
    private StandardLibrary() {
        super();
    }

    static native Long malloc(long size);
    static native void free(Long ptr);
    static native Long calloc(long nmemb, long size);
    static native Long realloc(Long ptr, long size);
    static native Long reallocarray(Long ptr, long nmemb, long size);
}