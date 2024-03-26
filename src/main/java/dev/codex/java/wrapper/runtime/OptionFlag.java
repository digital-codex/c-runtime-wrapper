package dev.codex.java.wrapper.runtime;

import dev.codex.java.wrapper.type.Flag;

public enum OptionFlag implements Flag {
    CREAT(0x00000040),
    EXCLUSIVE(0x00000080),
    NO_CONTROL_TERMINAL(0x00000100),
    TRUNCATE(0x00000200),
    APPEND(0x00000400),
    NONBLOCKING(0x00000800),
    DESYNCHRONOUS(0x00001000),
    ASYNCHRONOUS(0x00002000),
    DIRECTORY(0x00010000),
    NO_FOLLOW(0x00020000),
    CLOSE_ON_EXECUTE(0x00080000),
    SYNCHRONOUS(0x00101000);

    private final int value;

    OptionFlag(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return this.value;
    }
}