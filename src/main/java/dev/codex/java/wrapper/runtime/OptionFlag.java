package dev.codex.java.wrapper.runtime;

public enum OptionFlag {
    CREAT(0x00000100),
    EXCLUSIVE(0x00000200),
    NO_CONTROL_TERMINAL(0x00000400),
    TRUNCATE(0x00001000),
    APPEND(0x00002000),
    NONBLOCKING(0x00004000),
    DSYNC(0x00010000),
    ASYNC(0x00020000),


    DIRECTORY(0x00200000),


    CLOSE_ON_EXEC(0x02000000),
    SYNC(0x04010000);

    private final int value;

    OptionFlag(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}