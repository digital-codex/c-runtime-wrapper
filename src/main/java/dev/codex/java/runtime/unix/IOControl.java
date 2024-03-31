package dev.codex.java.runtime.unix;

class IOControl {
    private IOControl() {
        super();
    }

    static native int ioctl(int fd, long code, Long request);
}