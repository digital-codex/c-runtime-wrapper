package dev.codex.java.wrapper.runtime;

public enum NetworkTunnelDeviceFlag implements InterfaceRequest.RequestFlag {
    NETWORK_TUNNEL(0x0001),
    ACCESS_POINT(0x0002);

    public final int value;

    NetworkTunnelDeviceFlag(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return this.value;
    }
}