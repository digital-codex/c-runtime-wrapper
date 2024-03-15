package dev.codex.java.wrapper.runtime;
final class UniStd {
    private UniStd() {
        super();
    }

    static native int close(int fd);
}