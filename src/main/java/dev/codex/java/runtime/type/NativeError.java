package dev.codex.java.runtime.type;

public class NativeError extends RuntimeException {
    public static final String MESSAGE_FORMAT = "%s: %s";

    // TODO: relate these to errno value?
    public static final NativeError ILLEGAL_NUMBER_OF_ELEMENTS = new NativeError("Value of `nmemb` cannot be less than or equal to 0");
    public static final NativeError INCOMPATIBLE_ACCESS_ASSOCIATION = new NativeError("The flags on `fd` are not compatible with `mode`");

    public NativeError(String message) {
        super(message);
    }
}