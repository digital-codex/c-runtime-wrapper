package dev.codex.java.wrapper.runtime;

import dev.codex.java.wrapper.pointer.AbstractPointer;

public final class File extends AbstractPointer {
    File(long address) {
        super(address);
    }

    @Override
    public String toString() {
        return Long.toHexString(this.address);
    }
}