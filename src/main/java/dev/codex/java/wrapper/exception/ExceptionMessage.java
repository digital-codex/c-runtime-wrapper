package dev.codex.java.wrapper.exception;

import java.util.Formatter;

public class ExceptionMessage {
    public static final ExceptionMessage INVALID_POINTER_TYPE = new ExceptionMessage("Invalid pointer type: %s");
    public static final ExceptionMessage ILLEGAL_POINTER_ARGUMENT = new ExceptionMessage("Illegal argument: `%s` %s");

    private static final Formatter formatter = new Formatter();
    private final String message;

    private ExceptionMessage(String message) {
        this.message = message;
    }

    public String format(Object... args) {
        return ExceptionMessage.formatter.format(this.message, args).toString();
    }
}