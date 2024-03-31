package dev.codex.java.runtime.unix;

final class UnixStandard {
    private UnixStandard() {
        super();
    }

    static native int close(int fd);

    static native long read(int fd, byte[] buf, long count);
    static native long write(int fd, byte[] buf, long count);
}