package dev.codex.java.wrapper.exception;

public class IllegalPointerType extends RuntimeException {
    public IllegalPointerType(Class<?> clazz) {
        super(ExceptionMessage.INVALID_POINTER_TYPE.format(clazz.getName()));
    }

    public IllegalPointerType(Class<?> clazz, Throwable cause) {
        super(ExceptionMessage.INVALID_POINTER_TYPE.format(clazz.getName()), cause);
    }
}