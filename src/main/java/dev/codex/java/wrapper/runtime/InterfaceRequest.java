package dev.codex.java.wrapper.runtime;

import dev.codex.java.wrapper.exception.InvalidBufferLengthException;
import dev.codex.java.wrapper.type.AbstractPointer;
import dev.codex.java.wrapper.type.MemoryAddress;

public class InterfaceRequest extends AbstractPointer {
    public static final int NAME_SIZE = 16;

    private char[] name;
    private InterfaceFlag flags;

    InterfaceRequest(MemoryAddress address, long size) {
        this(address, size, new char[InterfaceRequest.NAME_SIZE]);
    }

    InterfaceRequest(MemoryAddress address, long size, char[] name) {
        super(address, size);
        this.name = name;
        this.flags = null;
    }

    public static class InterfaceFlag {

        private final short value;
        InterfaceFlag() {
            this.value = 0;
        }
        public short value() {
            return this.value;
        }
    }

    public char[] getName() {
        return this.name;
    }

    public void setName(char[] name) {
        if (name.length >= InterfaceRequest.NAME_SIZE) {
            throw new InvalidBufferLengthException("name", InterfaceRequest.NAME_SIZE);
        }

        this.name = name;
    }

    public InterfaceFlag getFlags() {
        return this.flags;
    }

    public void setFlags(InterfaceFlag flags) {
        this.flags = flags;
    }
}