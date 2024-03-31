package dev.codex.java.unix.runtime.constant;

import org.junit.jupiter.api.Test;

import static dev.codex.java.runtime.unix.constant.StandardRequestFlag.*;
import static org.junit.jupiter.api.Assertions.*;

class StandardRequestFlagTest {
    @Test
    void test_value_then_assert_value() {
        assertEquals("1", Integer.toBinaryString(UP.value()));
        assertEquals("10", Integer.toBinaryString(BROADCAST.value()));
        assertEquals("100", Integer.toBinaryString(DEBUG.value()));
        assertEquals("1000", Integer.toBinaryString(LOOP_BACK.value()));
        assertEquals("10000", Integer.toBinaryString(POINT_TO_POINT.value()));
        assertEquals("100000", Integer.toBinaryString(NO_TRAILERS.value()));
        assertEquals("1000000", Integer.toBinaryString(RUNNING.value()));
        assertEquals("10000000", Integer.toBinaryString(NO_ADDRESS_RESOLUTION.value()));
        assertEquals("100000000", Integer.toBinaryString(PROMISCUOUS.value()));
        assertEquals("1000000000", Integer.toBinaryString(ALL_MULTICAST.value()));
        assertEquals("10000000000", Integer.toBinaryString(MASTER.value()));
        assertEquals("100000000000", Integer.toBinaryString(SLAVE.value()));
        assertEquals("1000000000000", Integer.toBinaryString(MULTICAST.value()));
        assertEquals("10000000000000", Integer.toBinaryString(PORT_SELECT.value()));
        assertEquals("100000000000000", Integer.toBinaryString(AUTO_MEDIA.value()));
        assertEquals("1000000000000000", Integer.toBinaryString(DYNAMIC.value()));
        assertEquals("10000000000000000", Integer.toBinaryString(LOWER_UP.value()));
        assertEquals("100000000000000000", Integer.toBinaryString(DORMANT.value()));
        assertEquals("1000000000000000000", Integer.toBinaryString(ECHO.value()));
    }
}