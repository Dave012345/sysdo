package com.sysdo.service;

public class GlobalThrowableExcaption extends RuntimeException {

    public GlobalThrowableExcaption(String message) {
        super(message);
    }

    public GlobalThrowableExcaption(String message, Throwable cause) {
        super(message, cause);
    }
}
