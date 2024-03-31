package dev.codex.java.unix.runtime.constant;

import org.junit.jupiter.api.Test;

import static dev.codex.java.runtime.unix.constant.StatusFlag.*;
import static org.junit.jupiter.api.Assertions.*;

class StatusFlagTest {
    @Test
    void test_value_then_assert_value() {
        assertEquals("10000000000", Integer.toBinaryString(APPEND.value()));
        assertEquals("100000000000", Integer.toBinaryString(NONBLOCKING.value()));
        assertEquals("1000000000000", Integer.toBinaryString(DESYNCHRONOUS.value()));
        assertEquals("10000000000000", Integer.toBinaryString(ASYNCHRONOUS.value()));
        assertEquals("100000001000000000000", Integer.toBinaryString(SYNCHRONOUS.value()));
    }
}