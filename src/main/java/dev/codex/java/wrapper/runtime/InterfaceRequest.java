package dev.codex.java.wrapper.runtime;

import dev.codex.java.wrapper.exception.InvalidBufferLengthException;
import dev.codex.java.wrapper.type.AbstractPointer;
import dev.codex.java.wrapper.type.MemoryAddress;

public class InterfaceRequest extends AbstractPointer {
    public enum InterfaceFlag {
        UP(),
        BROADCAST(),
        DEBUG(),
        LOOP_BACK(),
        POINT_TO_POINT(),
        NO_TRAILERS(),
        RUNNING(),
        NO_ARP(),
        PROMISCUOUS(),
        ALL_MULTICAST(),
        MASTER(),
        SLAVE(),
        MULTICAST(),
        PORT_SELECT(),
        AUTO_MEDIA(),
        DYNAMIC(),

        LOWER_UP(),
        DORMANT(),
        ECHO();

        private final int value;
        InterfaceFlag() {
            this.value = 1 << this.ordinal();
        }
        public int value() {
            return this.value;
        }
    }

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
        if (name.length >= InterfaceRequest.NAME_SIZE) {
            throw new InvalidBufferLengthException("name", "greater than or equal to " + InterfaceRequest.NAME_SIZE);
        }

        this.name = name;
    }

    public short getFlags() {
        return ((Integer) this.flags).shortValue();
    }

    public void addFlag(InterfaceFlag flag) {
        this.flags = this.flags | flag.value();
    }

    public void removeFlag(InterfaceFlag flag) {
        this.flags = this.flags & (~flag.value());
    }
}