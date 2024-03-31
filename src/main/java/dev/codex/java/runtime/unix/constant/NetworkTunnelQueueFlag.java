package dev.codex.java.runtime.unix.constant;

import dev.codex.java.runtime.unix.InterfaceRequest;

public enum NetworkTunnelQueueFlag implements InterfaceRequest.RequestFlag {
    ATTACH_QUEUE(0x0200),
    DETACH_QUEUE(0x0400);

    public final int value;

    NetworkTunnelQueueFlag(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return this.value;
    }
}