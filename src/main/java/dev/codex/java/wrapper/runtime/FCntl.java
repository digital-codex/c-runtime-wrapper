package dev.codex.java.wrapper.runtime;

final class FCntl {
    private FCntl() {
        super();
    }

    static native int open(String file, int oflag);
}