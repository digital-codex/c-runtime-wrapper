package dev.codex.java.wrapper.runtime;

public enum NetworkTunnelInterfaceFlag implements InterfaceRequest.RequestFlag {
    NEW_API(0x0010),
    NEW_API_FRAGMENTS(0x0020),
    MULTIPLE_QUEUE(0x0100),
    NO_PACKET_INFORMATION(0x1000),
    ONE_QUEUE(0x2000),
    VIRTUAL_NETWORK_HIGH_DATA_RATE(0x4000),
    EXCLUSIVE_TUNNEL(0x8000);

    public final int value;

    NetworkTunnelInterfaceFlag(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return this.value;
    }
}