package dev.codex.java.glibc;

final class StdIO {
    private StdIO() {
        super();
    }

    public static native long fopen(String filename, String modes);
}