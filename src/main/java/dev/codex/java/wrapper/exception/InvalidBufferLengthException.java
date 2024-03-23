package dev.codex.java.wrapper.exception;

public class InvalidBufferLengthException extends IllegalArgumentException {
    public InvalidBufferLengthException(String arg, int len) {
        super(arg, ExceptionMessage.INVALID_BUFFER_LENGTH.format(len));
    }
}