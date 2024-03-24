package dev.codex.java.wrapper.runtime;

import org.junit.jupiter.api.Test;

import static dev.codex.java.wrapper.runtime.OptionFlag.*;
import static org.junit.jupiter.api.Assertions.*;

class OptionFlagTest {
    @Test
    void test_value_then_assert_value() {
        assertEquals("1000000", Integer.toBinaryString(CREAT.value()));
        assertEquals("10000000", Integer.toBinaryString(EXCLUSIVE.value()));
        assertEquals("100000000", Integer.toBinaryString(NO_CONTROL_TERMINAL.value()));
        assertEquals("1000000000", Integer.toBinaryString(TRUNCATE.value()));
        assertEquals("10000000000", Integer.toBinaryString(APPEND.value()));
        assertEquals("100000000000", Integer.toBinaryString(NONBLOCKING.value()));
        assertEquals("1000000000000", Integer.toBinaryString(DESYNCHRONOUS.value()));
        assertEquals("10000000000000", Integer.toBinaryString(ASYNCHRONOUS.value()));
        assertEquals("10000000000000000", Integer.toBinaryString(DIRECTORY.value()));
        assertEquals("10000000000000000000", Integer.toBinaryString(CLOSE_ON_EXECUTE.value()));
        assertEquals("100000001000000000000", Integer.toBinaryString(SYNCHRONOUS.value()));
    }
}