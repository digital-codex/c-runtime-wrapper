package dev.codex.java.runtime.unix.constant;

public enum CreationFlag implements AccessCode.OptionFlag {
    CREAT(0x00000040),
    EXCLUSIVE(0x00000080),
    NO_CONTROL_TERMINAL(0x00000100),
    TRUNCATE(0x00000200),
    DIRECTORY(0x00010000),
    NO_FOLLOW(0x00020000),
    CLOSE_ON_EXECUTE(0x00080000);

    private final int value;

    CreationFlag(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return this.value;
    }

}