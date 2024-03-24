package dev.codex.java.wrapper.runtime;

import org.junit.jupiter.api.Test;

import static dev.codex.java.wrapper.runtime.OptionFlag.*;
import static org.junit.jupiter.api.Assertions.*;

class OptionFlagTest {
    @Test
    void test_value_then_assert_value_and_mask() {
        assertEquals(64, CREAT.value());
        assertEquals("1000000", Integer.toBinaryString(CREAT.value()));

        assertEquals(128, EXCLUSIVE.value());
        assertEquals("10000000", Integer.toBinaryString(EXCLUSIVE.value()));

        assertEquals(256, NO_CONTROL_TERMINAL.value());
        assertEquals("100000000", Integer.toBinaryString(NO_CONTROL_TERMINAL.value()));

        assertEquals(512, TRUNCATE.value());
        assertEquals("1000000000", Integer.toBinaryString(TRUNCATE.value()));

        assertEquals(1024, APPEND.value());
        assertEquals("10000000000", Integer.toBinaryString(APPEND.value()));

        assertEquals(2048, NONBLOCKING.value());
        assertEquals("100000000000", Integer.toBinaryString(NONBLOCKING.value()));

        assertEquals(4096, DSYNC.value());
        assertEquals("1000000000000", Integer.toBinaryString(DSYNC.value()));

        assertEquals(8192, ASYNC.value());
        assertEquals("10000000000000", Integer.toBinaryString(ASYNC.value()));

        assertEquals(65536, DIRECTORY.value());
        assertEquals("10000000000000000", Integer.toBinaryString(DIRECTORY.value()));

        assertEquals(524288, CLOSE_ON_EXEC.value());
        assertEquals("10000000000000000000", Integer.toBinaryString(CLOSE_ON_EXEC.value()));

        assertEquals(1052672, SYNC.value());
        assertEquals("100000001000000000000", Integer.toBinaryString(SYNC.value()));
    }
}