package dev.codex.java.wrapper.runtime;

final class FileControl {
    private FileControl() {
        super();
    }

    static native int open(String pathname, int flags);
    static native int openat(int dirfd, String pathname, int flags);
}