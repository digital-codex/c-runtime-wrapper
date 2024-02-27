package dev.codex.java.wrapper.pointer;

public abstract class AbstractPointer implements Pointer {
    protected final long address;

    protected AbstractPointer(long address) {
        this.address = address;
    }

    @Override
    public long address() {
        return this.address;
    }
}