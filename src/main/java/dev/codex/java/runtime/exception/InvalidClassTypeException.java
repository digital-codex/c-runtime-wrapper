package dev.codex.java.runtime.exception;

public class InvalidClassTypeException extends IllegalArgumentException {
    public InvalidClassTypeException(String arg, Class<?> clazz) {
        super(arg, ExceptionMessage.INVALID_CLASS_TYPE.format(clazz.getName()));
    }
}