package dev.codex.java.wrapper.runtime;

public final class OptionFlag {
    public static final OptionFlag CREAT = new OptionFlag(0x00000100);
    public static final OptionFlag EXCLUSIVE = new OptionFlag(0x00000200);
    public static final OptionFlag NO_CONTROL_TERMINAL = new OptionFlag(0x00000400);
    public static final OptionFlag TRUNCATE = new OptionFlag(0x00001000);
    public static final OptionFlag APPEND = new OptionFlag(0x00002000);
    public static final OptionFlag NONBLOCKING = new OptionFlag(0x00004000);
    public static final OptionFlag DSYNC = new OptionFlag(0x00010000);
    public static final OptionFlag ASYNC = new OptionFlag(0x00020000);


    public static final OptionFlag DIRECTORY = new OptionFlag(0x00200000);


    public static final OptionFlag CLOSE_ON_EXEC = new OptionFlag(0x02000000);
    public static final OptionFlag SYNC = new OptionFlag(0x04010000);

    private final int value;

    private OptionFlag(int value) {
        this.value = value;
    }

    public static OptionFlag of(OptionFlag ...flags) {
        int value = 0;
        for (OptionFlag flag : flags)
            value = value | flag.value();

        return new OptionFlag(value);
    }

    public int value() {
        return this.value;
    }

    @Override
    public int hashCode() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof OptionFlag that)) return false;
        return this.value == that.value;
    }
}