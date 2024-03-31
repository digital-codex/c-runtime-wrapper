package dev.codex.java.runtime.unix;

import dev.codex.java.runtime.type.AbstractPointer;
import dev.codex.java.runtime.type.MemoryAddress;

public final class FileStream extends AbstractPointer {
    FileStream(MemoryAddress address, long size) {
        super(address, size);
    }

    public static class Position extends AbstractPointer {
        Position(MemoryAddress address, long size) {
            super(address, size);
        }
    }

    @Override
    public void close() {
        NativeRuntimeWrapper.fclose(this);
    }
}