package dev.codex.java.glibc;

final class StdIO {
    private StdIO() {
        super();
    }

    public static native long fopen(String filename, String modes);

    public  static native long fread(long ptr, long size, long n, long stream);
    public  static native long fwrite(long ptr, long size, long n, long stream);

    public static native int fseek(long stream, long off, int whence);
    public static native int ftell(long stream);
    public static native void rewind(long stream);
}