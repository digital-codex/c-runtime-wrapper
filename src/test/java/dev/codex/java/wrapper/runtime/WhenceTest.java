package dev.codex.java.wrapper.runtime;

import org.junit.jupiter.api.Test;

import static dev.codex.java.wrapper.runtime.Whence.*;
import static org.junit.jupiter.api.Assertions.*;

class WhenceTest {
    @Test
    void test_value_then_assert_value() {
        assertEquals("0", Integer.toBinaryString(SEEK_SET.value()));
        assertEquals("1", Integer.toBinaryString(SEEK_CURRENT.value()));
        assertEquals("10", Integer.toBinaryString(SEEK_END.value()));
    }
}