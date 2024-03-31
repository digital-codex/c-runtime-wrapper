package dev.codex.java.runtime.exception;

import java.util.Formatter;

public class ExceptionMessage {
    public static final ExceptionMessage ILLEGAL_ARGUMENT = new ExceptionMessage("Illegal argument: `%s` %s");
    public static final ExceptionMessage INVALID_CLASS_TYPE = new ExceptionMessage("invalid class type %s");
    public static final ExceptionMessage INVALID_BUFFER_LENGTH = new ExceptionMessage("cannot be %s");

    private static final Formatter formatter = new Formatter();
    private final String message;

    private ExceptionMessage(String message) {
        this.message = message;
    }

    public String format(Object... args) {
        return ExceptionMessage.formatter.format(this.message, args).toString();
    }
}