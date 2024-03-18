package dev.codex.java.wrapper.exception;

public class InvalidPointerTypeException extends RuntimeException {
    public InvalidPointerTypeException(Class<?> clazz) {
        super(ExceptionMessage.INVALID_POINTER_TYPE.format(clazz.getName()));
    }
}