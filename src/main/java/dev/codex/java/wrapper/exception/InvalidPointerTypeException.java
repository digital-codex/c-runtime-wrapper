package dev.codex.java.wrapper.exception;

public class InvalidPointerTypeException extends IllegalArgumentException {
    public InvalidPointerTypeException(String arg, Class<?> clazz) {
        super(arg, ExceptionMessage.INVALID_POINTER_TYPE.format(clazz.getName()));
    }
}