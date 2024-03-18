package dev.codex.java.wrapper.runtime;

public final class AccessFlag {
    public static final AccessFlag READ_ONLY = new AccessFlag(0x00000000);
    public static final AccessFlag WRITE_ONLY = new AccessFlag(0x00000001);
    public static final AccessFlag READ_WRITE = new AccessFlag(0x00000002);

    private final int value;

    private AccessFlag(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}