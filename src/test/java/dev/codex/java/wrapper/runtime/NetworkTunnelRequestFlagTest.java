package dev.codex.java.wrapper.runtime;

import org.junit.jupiter.api.Test;

import static dev.codex.java.wrapper.runtime.NetworkTunnelRequestFlag.*;
import static org.junit.jupiter.api.Assertions.*;

class NetworkTunnelRequestFlagTest {
    @Test
    void test_value_then_assert_value() {
        assertEquals("1", Integer.toBinaryString(TUNNEL.value()));
        assertEquals("10", Integer.toBinaryString(ACCESS_POINT.value()));
        assertEquals("10000", Integer.toBinaryString(NEW_API.value()));
        assertEquals("100000", Integer.toBinaryString(NEW_API_FRAGMENTS.value()));
        assertEquals("100000000", Integer.toBinaryString(MULTIPLE_QUEUE.value()));
        assertEquals("1000000000", Integer.toBinaryString(ATTACH_QUEUE.value()));
        assertEquals("10000000000", Integer.toBinaryString(DETACH_QUEUE.value()));
        assertEquals("1000000000000", Integer.toBinaryString(NO_PACKET_INFORMATION.value()));
        assertEquals("10000000000000", Integer.toBinaryString(ONE_QUEUE.value()));
        assertEquals("100000000000000", Integer.toBinaryString(VIRTUAL_NETWORK_HIGH_DATA_RATE.value()));
        assertEquals("1000000000000000", Integer.toBinaryString(EXCLUSIVE_TUNNEL.value()));
    }
}