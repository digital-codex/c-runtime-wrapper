package dev.codex.java.runtime.unix;

import dev.codex.java.runtime.exception.InvalidBufferLengthException;
import dev.codex.java.runtime.type.AbstractPointer;
import dev.codex.java.runtime.type.Flag;
import dev.codex.java.runtime.type.MemoryAddress;

import java.util.Arrays;

public class InterfaceRequest extends AbstractPointer {
    public interface RequestFlag extends Flag {}

    public static final int NAME_SIZE = 16;

    InterfaceRequest(MemoryAddress address, long size) {
        this(address, size, new byte[InterfaceRequest.NAME_SIZE]);
    }

    InterfaceRequest(MemoryAddress address, long size, byte[] name) {
        super(address, size);
        this.updateName(name);
        this.updateFlags((short) 0);
    }

    public native byte[] getName();

    public void setName(byte[] name) {
        if (name.length > InterfaceRequest.NAME_SIZE || (name.length == InterfaceRequest.NAME_SIZE && name[name.length - 1] != '\0')) {
            throw new InvalidBufferLengthException("name", "greater than " + InterfaceRequest.NAME_SIZE);
        }

        if (name[name.length - 1] != '\0') {
            name = Arrays.copyOf(name, name.length + 1);
            name[name.length - 1] = '\0';
        }

        this.updateName(name);
    }
    public native void updateName(byte[] name);

    public native short getFlags();

    public void addFlag(RequestFlag flag) {
        this.updateFlags((short) (this.getFlags() | flag.value()));
    }

    public void addFlags(RequestFlag... flags) {
        int current = this.getFlags();
        for (RequestFlag flag : flags) {
            current = current | flag.value();
        }
        this.updateFlags((short) current);
    }

    public void removeFlag(RequestFlag flag) {
        this.updateFlags((short) (this.getFlags() & (~flag.value())));
    }

    public void removeFlags(RequestFlag... flags) {
        int current = this.getFlags();
        for (RequestFlag flag : flags) {
            current = current & (~flag.value());
        }
        this.updateFlags((short) current);
    }
    public native void updateFlags(short flags);

    private static native void initialize();
    static {
        InterfaceRequest.initialize();
    }
}