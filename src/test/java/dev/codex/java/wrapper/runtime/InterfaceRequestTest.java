package dev.codex.java.wrapper.runtime;

import dev.codex.java.wrapper.type.Error;
import dev.codex.java.wrapper.type.FlagSet;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;

import static dev.codex.java.wrapper.runtime.NetworkTunnelRequestFlag.NO_PACKET_INFORMATION;
import static dev.codex.java.wrapper.runtime.NetworkTunnelRequestFlag.TUNNEL;
import static org.junit.jupiter.api.Assertions.*;

class InterfaceRequestTest {
    @Test
    void test_interface_request() {
        try (InterfaceRequest ifr = CRuntimeWrapper.malloc(InterfaceRequest.class)) {
            ifr.setName("tun0".getBytes(Charset.defaultCharset()));
            assertArrayEquals("tun0\0".getBytes(Charset.defaultCharset()), ifr.getName());

            ifr.addFlags(TUNNEL, NO_PACKET_INFORMATION);
            assertEquals(FlagSet.valueOf(TUNNEL, NO_PACKET_INFORMATION), ifr.getFlags());
        } catch (Error e) {
            fail(e.getMessage());
        }
    }
}