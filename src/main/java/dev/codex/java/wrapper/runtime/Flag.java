package dev.codex.java.wrapper.runtime;

public enum Flag {
    APPEND(0x00002000);

    private final int value;

    Flag(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}