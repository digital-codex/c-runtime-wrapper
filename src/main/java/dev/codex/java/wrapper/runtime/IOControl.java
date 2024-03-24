package dev.codex.java.wrapper.runtime;

class IOControl {
    private IOControl() {
        super();
    }

    static native int ioctl(int fd, long code, Long request);
}