package dev.codex.java.wrapper.runtime;

import dev.codex.java.wrapper.type.Error;
import dev.codex.java.wrapper.type.FlagSet;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;

import static dev.codex.java.wrapper.runtime.NetworkTunnelInterfaceFlag.NO_PACKET_INFORMATION;
import static dev.codex.java.wrapper.runtime.NetworkTunnelDeviceFlag.NETWORK_TUNNEL;
import static org.junit.jupiter.api.Assertions.*;

class InterfaceRequestTest {
    @Test
    void test_interface_request() {
        try (InterfaceRequest ifr = NativeRuntimeWrapper.malloc(InterfaceRequest.class)) {
            ifr.setName("tun0".getBytes(Charset.defaultCharset()));
            assertArrayEquals("tun0\0".getBytes(Charset.defaultCharset()), ifr.getName());

            ifr.addFlags(NETWORK_TUNNEL, NO_PACKET_INFORMATION);
            assertEquals(FlagSet.valueOf(NETWORK_TUNNEL, NO_PACKET_INFORMATION), ifr.getFlags());
        } catch (Error e) {
            fail(e.getMessage());
        }
    }
}