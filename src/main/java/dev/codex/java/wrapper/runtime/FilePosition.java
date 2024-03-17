package dev.codex.java.wrapper.runtime;

import dev.codex.java.wrapper.type.AbstractPointer;

public class FilePosition extends AbstractPointer {
    FilePosition(Long address, long size) {
        super(address, size);
    }
}