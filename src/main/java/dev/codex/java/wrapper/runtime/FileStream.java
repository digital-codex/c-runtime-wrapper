package dev.codex.java.wrapper.runtime;

import dev.codex.java.wrapper.pointer.AbstractPointer;

public final class FileStream extends AbstractPointer {
    FileStream(long address) {
        super(address);
    }

    @Override
    public String toString() {
        return Long.toHexString(this.address);
    }
}