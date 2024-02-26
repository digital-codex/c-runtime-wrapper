package dev.codex.java.wrapper.runtime;

import dev.codex.java.wrapper.pointer.AbstractPointer;

public class InterfaceRequest extends AbstractPointer {
    public static final int NAME_SIZE = 16;

    private byte[] name;
    private short flags;

    InterfaceRequest(long address) {
        this(address, new byte[InterfaceRequest.NAME_SIZE]);
    }

    InterfaceRequest(long address, byte[] name) {
        super(address);
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

    @Override
    public String toString() {
        return Long.toHexString(this.address);
    }
}