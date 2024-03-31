package dev.codex.java.unix.runtime.constant;

import org.junit.jupiter.api.Test;

import static dev.codex.java.runtime.unix.constant.CreationFlag.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CreationFlagTest {
    @Test
    void test_value_then_assert_value() {
        assertEquals("1000000", Integer.toBinaryString(CREAT.value()));
        assertEquals("10000000", Integer.toBinaryString(EXCLUSIVE.value()));
        assertEquals("100000000", Integer.toBinaryString(NO_CONTROL_TERMINAL.value()));
        assertEquals("1000000000", Integer.toBinaryString(TRUNCATE.value()));
        assertEquals("10000000000000000", Integer.toBinaryString(DIRECTORY.value()));
        assertEquals("10000000000000000000", Integer.toBinaryString(CLOSE_ON_EXECUTE.value()));
    }
}