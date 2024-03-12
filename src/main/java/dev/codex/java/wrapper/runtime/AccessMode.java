package dev.codex.java.wrapper.runtime;

public final class AccessMode {
    public static final AccessMode READ_ONLY = new AccessMode(0x00000000);
    public static final AccessMode WRITE_ONLY = new AccessMode(0x00000001);
    public static final AccessMode READ_WRITE = new AccessMode(0x00000002);

    private final int value;

    private AccessMode(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}