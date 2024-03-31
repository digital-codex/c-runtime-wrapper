package dev.codex.java.unix.runtime.constant;

import org.junit.jupiter.api.Test;

import static dev.codex.java.runtime.unix.constant.NetworkTunnelInterfaceFlag.*;
import static org.junit.jupiter.api.Assertions.*;

class NetworkTunnelInterfaceFlagTest {
    @Test
    void test_value_then_assert_value() {
        assertEquals("10000", Integer.toBinaryString(NEW_API.value()));
        assertEquals("100000", Integer.toBinaryString(NEW_API_FRAGMENTS.value()));
        assertEquals("100000000", Integer.toBinaryString(MULTIPLE_QUEUE.value()));
        assertEquals("1000000000000", Integer.toBinaryString(NO_PACKET_INFORMATION.value()));
        assertEquals("10000000000000", Integer.toBinaryString(ONE_QUEUE.value()));
        assertEquals("100000000000000", Integer.toBinaryString(VIRTUAL_NETWORK_HIGH_DATA_RATE.value()));
        assertEquals("1000000000000000", Integer.toBinaryString(EXCLUSIVE_TUNNEL.value()));
    }
}