package dev.codex.java.runtime.type;

public abstract class AbstractPointer implements Pointer {
    protected final MemoryAddress address;
    protected long size;

    protected AbstractPointer(MemoryAddress address, long size) {
        this.address = address;
        this.size = size;
    }

    @Override
    public MemoryAddress address() {
        return this.address;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return Long.toHexString(this.address.value());
    }
}