package dev.codex.java.wrapper.exception;

public class IllegalArgumentException extends RuntimeException {
    public IllegalArgumentException(String arg, String message) {
        super(ExceptionMessage.ILLEGAL_POINTER_ARGUMENT.format(arg, message));
    }
}