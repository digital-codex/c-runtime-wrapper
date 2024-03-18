package dev.codex.java.wrapper.runtime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CRuntimeWrapperTest {

    @Test
    void test_parse_sh_executable_then_print_magic_and_class() {
        try (FileStream fp = CRuntimeWrapper.fopen("/bin/sh", AccessMode.READ)) {
            byte[] buffer = new byte[4];

            long ret = CRuntimeWrapper.fread(buffer, buffer.length, 1, fp);
            assertEquals(1, ret, "fread() failed: " + ret);

            System.out.printf("ELF magic %#04x%02x%02x%02x%n", buffer[0], buffer[1], buffer[2], buffer[3]);

            ret = CRuntimeWrapper.fread(buffer, 1, 1, fp);
            assertEquals(1, ret, "fread() failed: " + ret);

            System.out.printf("Class: %#04x\n", buffer[0]);
        } catch (Error error) {
            fail();
        }
    }
}