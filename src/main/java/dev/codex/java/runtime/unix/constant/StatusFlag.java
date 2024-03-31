package dev.codex.java.runtime.unix.constant;

public enum StatusFlag implements AccessCode.OptionFlag {
    APPEND(0x00000400),
    NONBLOCKING(0x00000800),
    DESYNCHRONOUS(0x00001000),
    ASYNCHRONOUS(0x00002000),
    SYNCHRONOUS(0x00101000);

    private final int value;

    StatusFlag(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return this.value;
    }
}