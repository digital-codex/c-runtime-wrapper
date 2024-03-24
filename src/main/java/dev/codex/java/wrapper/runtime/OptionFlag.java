package dev.codex.java.wrapper.runtime;

public enum OptionFlag {
    CREAT(6),
    EXCLUSIVE(7),
    NO_CONTROL_TERMINAL(8),
    TRUNCATE(9),
    APPEND(10),
    NONBLOCKING(11),
    DSYNC(12),
    ASYNC(13),
    DIRECTORY(16),
    CLOSE_ON_EXEC(19),
    SYNC(20, DSYNC);

    private final int value;
    OptionFlag(int offset, OptionFlag... bits) {
        int v = 1 << + offset;
        for (OptionFlag bit : bits) {
            v = v | bit.value();
        }
        this.value = v;
    }
    public int value() {
        return this.value;
    }
}