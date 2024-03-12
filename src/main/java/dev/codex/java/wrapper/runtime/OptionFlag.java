package dev.codex.java.wrapper.runtime;

public final class OptionFlag {
    public static final OptionFlag APPEND = new OptionFlag(0x00002000);
    public static final OptionFlag ASYNC = new OptionFlag(0x00020000);

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
}