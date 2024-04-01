package dev.codex.java.unix.runtime;

import dev.codex.java.runtime.library.NativeLibraryLoader;
import dev.codex.java.runtime.type.NativeError;
import dev.codex.java.runtime.type.Flag;
import dev.codex.java.runtime.unix.InterfaceRequest;
import dev.codex.java.runtime.unix.NativeRuntimeWrapper;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;

import static dev.codex.java.runtime.unix.constant.NetworkTunnelInterfaceFlag.NO_PACKET_INFORMATION;
import static dev.codex.java.runtime.unix.constant.NetworkTunnelDeviceFlag.NETWORK_TUNNEL;
import static org.junit.jupiter.api.Assertions.*;

class InterfaceRequestTest {
    static {
        NativeLibraryLoader.load(NativeLibraryLoader.WORKSPACE, "libNativeRuntimeWrapper.so");
    }

    @Test
    void test_interface_request() {
        try (InterfaceRequest ifr = NativeRuntimeWrapper.malloc(InterfaceRequest.class)) {
            ifr.setName("tun0".getBytes(Charset.defaultCharset()));
            assertArrayEquals("tun0\0".getBytes(Charset.defaultCharset()), ifr.getName());

            ifr.addFlags(NETWORK_TUNNEL, NO_PACKET_INFORMATION);
            assertEquals(Flag.valueOf(NETWORK_TUNNEL, NO_PACKET_INFORMATION), ifr.getFlags());
            ifr.removeFlag(NO_PACKET_INFORMATION);
            assertEquals(Flag.valueOf(NETWORK_TUNNEL), ifr.getFlags());
        } catch (NativeError error) {
            fail(error.getMessage());
        }
    }
}