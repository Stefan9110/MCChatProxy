package com.github.stefan9110.MCChatProxy.util.configuration.exceptions;

public class IncorrectInstanceException extends RuntimeException {
    public IncorrectInstanceException() {
        super("Object is not the correct instance");
    }
}
