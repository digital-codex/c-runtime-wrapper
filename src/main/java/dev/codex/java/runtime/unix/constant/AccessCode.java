package dev.codex.java.runtime.unix.constant;

import dev.codex.java.runtime.type.Flag;

public final class AccessCode {
    public interface OptionFlag extends Flag {}

    public static final AccessCode READ_ONLY = new AccessCode(0x00000000);
    public static final AccessCode WRITE_ONLY = new AccessCode(0x00000001);
    public static final AccessCode READ_WRITE = new AccessCode(0x00000002);

    private final int value;

    private AccessCode(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof AccessCode that)) return  false;
        return this.value == that.value;
    }

    @Override
    public int hashCode() {
        return this.value;
    }
}