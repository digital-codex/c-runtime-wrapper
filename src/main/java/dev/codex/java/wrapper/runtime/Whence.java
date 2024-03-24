package dev.codex.java.wrapper.runtime;

public final class Whence {
    public static final Whence SEEK_SET = new Whence(0x00000000);
    public static final Whence SEEK_CURRENT = new Whence(0x00000001);
    public static final Whence SEEK_END = new Whence(0x00000002);

    private final int value;

    private Whence(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Whence that)) return false;
        return this.value == that.value;
    }

    @Override
    public int hashCode() {
        return this.value;
    }
}