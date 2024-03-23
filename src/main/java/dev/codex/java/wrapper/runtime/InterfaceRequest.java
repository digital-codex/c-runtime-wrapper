package dev.codex.java.wrapper.runtime;

import dev.codex.java.wrapper.type.AbstractPointer;
import dev.codex.java.wrapper.type.MemoryAddress;

public class InterfaceRequest extends AbstractPointer {
    public static final int NAME_SIZE = 16;

    private byte[] name;
    private short flags;

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

    //TODO(treyvon): param validation name.length < 16
    public void setName(byte[] name) {
        this.name = name;
    }

    public short getFlags() {
        return this.flags;
    }

    //TODO(treyvon): add type safety on flags
    public void setFlags(short flags) {
        this.flags = flags;
    }
}