package dev.codex.java.wrapper.exception;

public class InvalidPointerType extends RuntimeException {
    public InvalidPointerType(Class<?> clazz) {
        super(ExceptionMessage.INVALID_POINTER_TYPE.format(clazz.getName()));
    }

    public InvalidPointerType(Class<?> clazz, Throwable cause) {
        super(ExceptionMessage.INVALID_POINTER_TYPE.format(clazz.getName()), cause);
    }
}