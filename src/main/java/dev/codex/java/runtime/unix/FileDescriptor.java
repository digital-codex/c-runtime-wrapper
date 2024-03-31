package dev.codex.java.runtime.unix;

import dev.codex.java.runtime.exception.IllegalArgumentException;
import dev.codex.java.runtime.unix.constant.AccessCode;

import java.util.Arrays;
import java.util.Objects;

public class FileDescriptor {
    private final int fd;
    private final AccessCode mode;
    private final AccessCode.OptionFlag[] options;

    FileDescriptor(int fd, AccessCode mode, AccessCode.OptionFlag... options) {
        if (fd <= 0) {
            throw new IllegalArgumentException("fd", "cannot be less than or equal to 0");
        }

        this.fd = fd;
        this.mode = mode;
        this.options = options;
    }

    public int fd() {
        return this.fd;
    }

    public AccessCode mode() {
        return this.mode;
    }

    public AccessCode.OptionFlag[] options() {
        return this.options;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof FileDescriptor that)) return false;
        return this.fd == that.fd && Objects.equals(this.mode, that.mode) && Arrays.equals(this.options, that.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.fd, this.mode, Arrays.hashCode(this.options));
    }
}