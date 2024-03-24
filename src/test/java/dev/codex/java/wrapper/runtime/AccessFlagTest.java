package dev.codex.java.wrapper.runtime;

import org.junit.jupiter.api.Test;

import static dev.codex.java.wrapper.runtime.AccessFlag.*;
import static org.junit.jupiter.api.Assertions.*;

class AccessFlagTest {
    @Test
    void test_value_then_assert_value_and_mask() {
        assertEquals(0, READ_ONLY.value());
        assertEquals("0", Integer.toBinaryString(READ_ONLY.value()));

        assertEquals(1, WRITE_ONLY.value());
        assertEquals("1", Integer.toBinaryString(WRITE_ONLY.value()));

        assertEquals(2, READ_WRITE.value());
        assertEquals("10", Integer.toBinaryString(READ_WRITE.value()));
    }
}