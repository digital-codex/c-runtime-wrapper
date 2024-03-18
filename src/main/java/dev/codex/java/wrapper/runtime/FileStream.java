package dev.codex.java.wrapper.runtime;

import dev.codex.java.wrapper.type.AbstractPointer;

public final class FileStream extends AbstractPointer {
    FileStream(Long address, long size) {
        super(address, size);
    }

    public static class Position extends AbstractPointer {
        Position(Long address, long size) {
            super(address, size);
        }
    }

    @Override
    public void close() {
        CRuntimeWrapper.fclose(this);
    }
}