package dev.codex.java.wrapper.runtime;

final class StdIO {
    private StdIO() {
        super();
    }

    static native int fclose(long stream);
    static native long fopen(String filename, String modes);

    static native long fread(long ptr, long size, long n, long stream);
    static native long fwrite(long ptr, long size, long n, long stream);

    static native int fseek(long stream, long off, int whence);
    static native int ftell(long stream);
    static native void rewind(long stream);
}