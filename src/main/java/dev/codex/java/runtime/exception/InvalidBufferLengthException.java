package dev.codex.java.runtime.exception;

public class InvalidBufferLengthException extends IllegalArgumentException {
    public InvalidBufferLengthException(String arg, String len) {
        super(arg, ExceptionMessage.INVALID_BUFFER_LENGTH.format(len));
    }
}