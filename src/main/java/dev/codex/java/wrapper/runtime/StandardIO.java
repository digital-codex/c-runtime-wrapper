package dev.codex.java.wrapper.runtime;

import java.util.Map;

final class StandardIO {
    private StandardIO() {
        super();
    }

    static native Long fopen(String pathname, String mode);
    static native Long fdopen(int fd, String mode);
    static native Long freopen(String pathname, String mode, Long stream);

    static native int fclose(Long stream);

    static native long fread(byte[] ptr, long size, long nmemb, Long stream);
    static native long fwrite(byte[] ptr, long size, long nmemb, Long stream);

    static native int fseek(Long stream, long offset, int whence);
    static native long ftell(Long stream);
    static native void rewind(Long stream);
    static native int fgetpos(Long stream, Long pos);
    static native int fsetpos(Long stream, Long pos);
}