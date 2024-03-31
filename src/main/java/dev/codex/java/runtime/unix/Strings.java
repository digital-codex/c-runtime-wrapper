package dev.codex.java.runtime.unix;

final class Strings {
    private Strings() {
        super();
    }

    static native byte[] strerror();
}