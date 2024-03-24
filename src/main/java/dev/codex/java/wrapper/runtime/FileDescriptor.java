package dev.codex.java.wrapper.runtime;

import dev.codex.java.wrapper.exception.IllegalArgumentException;

import java.util.Objects;

public class FileDescriptor {
    private final int fd;
    private final AccessFlag mode;
    private final OptionFlags options;

    FileDescriptor(int fd, AccessFlag mode, OptionFlag... options) {
        if (fd <= 0) {
            throw new IllegalArgumentException("fd", "cannot be less than or equal to 0");
        }

        this.fd = fd;
        this.mode = mode;
        this.options = new OptionFlags(options);
    }

    public int fd() {
        return this.fd;
    }

    public AccessFlag mode() {
        return this.mode;
    }

    public OptionFlags options() {
        return this.options;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof FileDescriptor that)) return false;
        return this.fd == that.fd && Objects.equals(this.mode, that.mode) && Objects.equals(this.options, that.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.fd, this.mode, this.options);
    }
}