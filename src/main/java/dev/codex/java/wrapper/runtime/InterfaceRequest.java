package dev.codex.java.wrapper.runtime;

import dev.codex.java.wrapper.exception.InvalidBufferLengthException;
import dev.codex.java.wrapper.type.AbstractPointer;
import dev.codex.java.wrapper.type.Flag;
import dev.codex.java.wrapper.type.MemoryAddress;

public class InterfaceRequest extends AbstractPointer {
    public interface RequestFlag extends Flag {}

    public static final int NAME_SIZE = 16;

    private byte[] name;
    private int flags;

    InterfaceRequest(MemoryAddress address, long size) {
        this(address, size, new byte[InterfaceRequest.NAME_SIZE]);
    }

    InterfaceRequest(MemoryAddress address, long size, byte[] name) {
        super(address, size);
        this.name = name;
        this.flags = 0;
    }

    public byte[] getName() {
        return this.name;
    }

    public void setName(byte[] name) {
        if (name.length > InterfaceRequest.NAME_SIZE) {
            throw new InvalidBufferLengthException("name", "greater than " + InterfaceRequest.NAME_SIZE);
        }

        this.name = name;
    }

    public short getFlags() {
        return ((Integer) this.flags).shortValue();
    }

    public void addFlag(RequestFlag flag) {
        this.flags = this.flags | flag.value();
    }

    public void removeFlag(RequestFlag flag) {
        this.flags = this.flags & (~flag.value());
    }
}