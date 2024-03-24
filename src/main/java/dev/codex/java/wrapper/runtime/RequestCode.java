package dev.codex.java.wrapper.runtime;

public final class RequestCode {
    public static final RequestCode TUNSETIFF = new RequestCode(0L);

    private final long value;

    private RequestCode(long value) {
        this.value = value;
    }

    public long value() {
        return this.value;
    }
}