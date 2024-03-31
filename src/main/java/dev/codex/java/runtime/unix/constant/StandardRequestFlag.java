package dev.codex.java.runtime.unix.constant;

import dev.codex.java.runtime.unix.InterfaceRequest;

public enum StandardRequestFlag implements InterfaceRequest.RequestFlag {
    UP(),
    BROADCAST(),
    DEBUG(),
    LOOP_BACK(),
    POINT_TO_POINT(),
    NO_TRAILERS(),
    RUNNING(),
    NO_ADDRESS_RESOLUTION(),
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

    StandardRequestFlag() {
        this.value = 1 << this.ordinal();
    }

    @Override
    public int value() {
        return this.value;
    }
}