package dev.codex.java.wrapper.runtime;

import dev.codex.java.wrapper.exception.IllegalArgumentException;

public class FileDescriptor {
    private final int fd;
    private final AccessFlag mode;
    private final OptionFlag[] options;

    FileDescriptor(int fd, AccessFlag mode, OptionFlag... options) {
        if (fd <= 0) {
            throw new IllegalArgumentException("fd", "cannot be less than or equal to zero");
        }

        this.fd = fd;
        this.mode = mode;
        this.options = options;
    }

    public int fd() {
        return this.fd;
    }

    public AccessFlag mode() {
        return this.mode;
    }

    public OptionFlag[] options() {
        return this.options;
    }
}