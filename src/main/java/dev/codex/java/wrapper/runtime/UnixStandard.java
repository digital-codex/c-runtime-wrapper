package dev.codex.java.wrapper.runtime;
final class UnixStandard {
    private UnixStandard() {
        super();
    }

    static native int close(int fd);
}