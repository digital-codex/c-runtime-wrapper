package dev.codex.java.wrapper.runtime;

import dev.codex.java.wrapper.exception.InvalidBufferLengthException;
import dev.codex.java.wrapper.type.AbstractPointer;
import dev.codex.java.wrapper.type.Flag;
import dev.codex.java.wrapper.type.MemoryAddress;

import java.util.Arrays;

public class InterfaceRequest extends AbstractPointer {
    public interface RequestFlag extends Flag {}

    public static final int NAME_SIZE = 16;

    // TODO: remove duplicate storage
    private byte[] name;
    private Integer flags;

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
        if (name.length > InterfaceRequest.NAME_SIZE || (name.length == InterfaceRequest.NAME_SIZE && name[name.length - 1] != '\0')) {
            throw new InvalidBufferLengthException("name", "greater than " + InterfaceRequest.NAME_SIZE);
        }

        if (name[name.length - 1] != '\0') {
            name = Arrays.copyOf(name, name.length + 1);
            name[name.length - 1] = '\0';
        }

        this.name = name;
        this.updateName(this.name);
    }
    public native void updateName(byte[] name);

    public short getFlags() {
        return this.flags.shortValue();
    }

    public void addFlag(RequestFlag flag) {
        this.flags = this.flags | flag.value();
        this.updateFlags(this.flags.shortValue());
    }

    public void addFlags(RequestFlag... flags) {
        for (RequestFlag flag : flags) {
            this.flags = this.flags | flag.value();
        }
        this.updateFlags(this.flags.shortValue());
    }

    public void removeFlag(RequestFlag flag) {
        this.flags = this.flags & (~flag.value());
        this.updateFlags(this.flags.shortValue());
    }

    public void removeFlags(RequestFlag... flags) {
        for (RequestFlag flag : flags) {
            this.flags = this.flags & (~flag.value());
        }
        this.updateFlags(this.flags.shortValue());
    }
    public native void updateFlags(short flags);

    private static native void initialize();
    static {
        InterfaceRequest.initialize();
    }
}