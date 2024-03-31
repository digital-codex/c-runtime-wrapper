package dev.codex.java.wrapper.runtime;

import dev.codex.java.wrapper.type.AbstractPointer;
import dev.codex.java.wrapper.type.MemoryAddress;

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