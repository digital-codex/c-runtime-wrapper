package dev.codex.java.runtime.exception;

public class IllegalArgumentException extends RuntimeException {
    public IllegalArgumentException(String arg, String message) {
        super(ExceptionMessage.ILLEGAL_ARGUMENT.format(arg, message));
    }
}