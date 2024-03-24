package dev.codex.java.wrapper.runtime;

import org.junit.jupiter.api.Test;

import static dev.codex.java.wrapper.runtime.InterfaceRequest.InterfaceFlag.*;
import static org.junit.jupiter.api.Assertions.*;

class InterfaceRequestTest {
    @Test
    void test_value_then_assert_value_and_mask() {
        assertEquals(1, UP.value());
        assertEquals("1", Integer.toBinaryString(UP.value()));

        assertEquals(2, BROADCAST.value());
        assertEquals("10", Integer.toBinaryString(BROADCAST.value()));

        assertEquals(4, DEBUG.value());
        assertEquals("100", Integer.toBinaryString(DEBUG.value()));

        assertEquals(8, LOOP_BACK.value());
        assertEquals("1000", Integer.toBinaryString(LOOP_BACK.value()));

        assertEquals(16, POINT_TO_POINT.value());
        assertEquals("10000", Integer.toBinaryString(POINT_TO_POINT.value()));

        assertEquals(32, NO_TRAILERS.value());
        assertEquals("100000", Integer.toBinaryString(NO_TRAILERS.value()));

        assertEquals(64, RUNNING.value());
        assertEquals("1000000", Integer.toBinaryString(RUNNING.value()));

        assertEquals(128, NO_ARP.value());
        assertEquals("10000000", Integer.toBinaryString(NO_ARP.value()));

        assertEquals(256, PROMISCUOUS.value());
        assertEquals("100000000", Integer.toBinaryString(PROMISCUOUS.value()));

        assertEquals(512, ALL_MULTICAST.value());
        assertEquals("1000000000", Integer.toBinaryString(ALL_MULTICAST.value()));

        assertEquals(1024, MASTER.value());
        assertEquals("10000000000", Integer.toBinaryString(MASTER.value()));

        assertEquals(2048, SLAVE.value());
        assertEquals("100000000000", Integer.toBinaryString(SLAVE.value()));

        assertEquals(4096, MULTICAST.value());
        assertEquals("1000000000000", Integer.toBinaryString(MULTICAST.value()));

        assertEquals(8192, PORT_SELECT.value());
        assertEquals("10000000000000", Integer.toBinaryString(PORT_SELECT.value()));

        assertEquals(16384, AUTO_MEDIA.value());
        assertEquals("100000000000000", Integer.toBinaryString(AUTO_MEDIA.value()));

        assertEquals(32768, DYNAMIC.value());
        assertEquals("1000000000000000", Integer.toBinaryString(DYNAMIC.value()));

        assertEquals(65536, LOWER_UP.value());
        assertEquals("10000000000000000", Integer.toBinaryString(LOWER_UP.value()));

        assertEquals(131072, DORMANT.value());
        assertEquals("100000000000000000", Integer.toBinaryString(DORMANT.value()));

        assertEquals(262144, ECHO.value());
        assertEquals("1000000000000000000", Integer.toBinaryString(ECHO.value()));
    }
}