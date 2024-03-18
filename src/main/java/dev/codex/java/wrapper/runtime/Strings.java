package dev.codex.java.wrapper.runtime;

final class Strings {
    private Strings() {
        super();
    }

    static native byte[] strerror();
}