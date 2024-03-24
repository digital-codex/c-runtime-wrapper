package dev.codex.java.wrapper.runtime;

public enum NetworkTunnelRequestFlag implements InterfaceRequest.RequestFlag {
    TUNNEL(0x0001),
    ACCESS_POINT(0x0002),
    NEW_API(0x0010),
    NEW_API_FRAGMENTS(0x0020),
    MULTIPLE_QUEUE(0x0100),
    ATTACH_QUEUE(0x0200),
    DETACH_QUEUE(0x0400),
    NO_PACKET_INFORMATION(0x1000),
    ONE_QUEUE(0x2000),
    VIRTUAL_NETWORK_HIGH_DATA_RATE(0x4000),
    EXCLUSIVE_TUNNEL(0x8000);

    public final int value;

    NetworkTunnelRequestFlag(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return this.value;
    }
}