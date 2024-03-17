package dev.codex.java.wrapper.type;

public class Error extends RuntimeException {
    public static final Error ILLEGAL_NUMBER_OF_ELEMENTS = new Error("Value of `nmemb` cannot be less than or equal to 0");
    public static final Error INCOMPATIBLE_ACCESS_ASSOCIATION = new Error("The flags on `fd` are not compatible with `mode`");

    public Error(String message) {
        super(message);
    }
}