package dev.codex.java.wrapper.runtime;

public final class OptionFlagMask {
    private int value;

    public OptionFlagMask() {
        this.value = 0;
    }

    public OptionFlagMask(OptionFlag... flags) {
        for (OptionFlag flag : flags) {
            this.value = this.value | flag.value();
        }
    }

    public static int valueOf(OptionFlag ...flags) {
        int value = 0;
        for (OptionFlag flag : flags) {
            value = value | flag.value();
        }
        return value;
    }

    public void add(OptionFlag flag) {
        this.value = this.value | flag.value();
    }

    public void remove(OptionFlag flag) {
        this.value = (this.value & (~flag.value()));
    }

    public boolean contains(OptionFlag flag) {
        return (this.value & flag.value()) != 0;
    }

    public boolean containsAll(OptionFlag... flags) {
        for (OptionFlag flag : flags) {
            if (!this.contains(flag)) return false;
        }
        return true;
    }

    public int value() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof OptionFlagMask that)) return false;
        return this.value == that.value;
    }

    @Override
    public int hashCode() {
        return this.value;
    }
}