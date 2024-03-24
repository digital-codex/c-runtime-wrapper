package dev.codex.java.wrapper.type;

import java.util.Objects;

public final class MemoryAddress {
    public static MemoryAddress NULL = new MemoryAddress(null);

    private final Long address;

    private MemoryAddress(Long address) {
        this.address = address;
    }

    public static MemoryAddress of(Long address) {
        return new MemoryAddress(address);
    }

    public Long value() {
        return this.address;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof MemoryAddress that)) return false;
        return Objects.equals(this.address, that.address);
    }

    @Override
    public int hashCode() {
        return (int) (this.address ^ (this.address >>> 32));
    }
}