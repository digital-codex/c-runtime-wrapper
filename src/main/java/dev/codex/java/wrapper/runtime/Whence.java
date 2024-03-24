package dev.codex.java.wrapper.runtime;

public enum Whence {
    SEEK_SET, SEEK_CURRENT, SEEK_END;

    public int value() {
        return this.ordinal();
    }
}