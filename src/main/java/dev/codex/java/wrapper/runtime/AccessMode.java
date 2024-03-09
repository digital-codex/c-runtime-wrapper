package dev.codex.java.wrapper.runtime;

public enum AccessMode {
    READ_ONLY(0x00000000),
    WRITE_ONLY(0x00000001),
    READ_WRITE(0x00000002);

    private final int value;

    AccessMode(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}