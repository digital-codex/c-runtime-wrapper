package dev.codex.java.wrapper.exception;

public class IllegalPointerArgument extends RuntimeException {
    public IllegalPointerArgument(String arg, String message) {
        super(ExceptionMessage.ILLEGAL_POINTER_ARG.format(arg, message));
    }
}