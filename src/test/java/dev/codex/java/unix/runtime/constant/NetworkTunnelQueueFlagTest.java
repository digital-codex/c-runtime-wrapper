package dev.codex.java.unix.runtime.constant;

import org.junit.jupiter.api.Test;

import static dev.codex.java.runtime.unix.constant.NetworkTunnelQueueFlag.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NetworkTunnelQueueFlagTest {
    @Test
    void test_value_then_assert_value() {
        assertEquals("1000000000", Integer.toBinaryString(ATTACH_QUEUE.value()));
        assertEquals("10000000000", Integer.toBinaryString(DETACH_QUEUE.value()));
    }
}