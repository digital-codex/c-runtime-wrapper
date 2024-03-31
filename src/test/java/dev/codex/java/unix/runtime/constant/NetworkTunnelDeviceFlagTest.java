package dev.codex.java.unix.runtime.constant;

import org.junit.jupiter.api.Test;

import static dev.codex.java.runtime.unix.constant.NetworkTunnelDeviceFlag.ACCESS_POINT;
import static dev.codex.java.runtime.unix.constant.NetworkTunnelDeviceFlag.NETWORK_TUNNEL;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NetworkTunnelDeviceFlagTest {
    @Test
    void test_value_then_assert_value() {
        assertEquals("1", Integer.toBinaryString(NETWORK_TUNNEL.value()));
        assertEquals("10", Integer.toBinaryString(ACCESS_POINT.value()));
    }
}