package dev.codex.java.wrapper.runtime;

import dev.codex.java.wrapper.type.Error;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CRuntimeWrapperTest {
    public static final int MAGIC_NUMBER = 0x7f454c46;
    public static final byte CLASS = 0x02;

    @Test
    void test_parse_sh_executable_with_open_then_assert_magic_and_class() {
        FileDescriptor fd = CRuntimeWrapper.open("/bin/sh", AccessFlag.READ_ONLY);
        byte[] buffer = new byte[4];

        long ret = CRuntimeWrapper.read(fd, buffer, buffer.length);
        assertEquals(4, ret, "fread() failed: " + ret);

        assertEquals(CRuntimeWrapperTest.MAGIC_NUMBER, buffer[3] | (buffer[2] << 8) | (buffer[1] << 16) | (buffer[0] << 24));

        ret = CRuntimeWrapper.read(fd, buffer, 1);
        assertEquals(1, ret, "fread() failed: " + ret);

        assertEquals(CRuntimeWrapperTest.CLASS, buffer[0]);
    }

    @Test
    void test_parse_sh_executable_with_fopen_then_assert_magic_and_class() {
        try (FileStream fp = CRuntimeWrapper.fopen("/bin/sh", AccessMode.READ)) {
            byte[] buffer = new byte[4];

            long ret = CRuntimeWrapper.fread(buffer, buffer.length, 1, fp);
            assertEquals(1, ret, "fread() failed: " + ret);

            assertEquals(CRuntimeWrapperTest.MAGIC_NUMBER, buffer[3] | (buffer[2] << 8) | (buffer[1] << 16) | (buffer[0] << 24));

            ret = CRuntimeWrapper.fread(buffer, 1, 1, fp);
            assertEquals(1, ret, "fread() failed: " + ret);

            assertEquals(CRuntimeWrapperTest.CLASS, buffer[0]);
        } catch (Error error) {
            fail(error.getMessage());
        }
    }
}