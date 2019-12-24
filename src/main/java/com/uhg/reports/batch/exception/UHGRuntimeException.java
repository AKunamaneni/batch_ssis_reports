package com.uhg.reports.batch.exception;

public class UHGRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -8279291371403138936L;

    public UHGRuntimeException() {
    }

    public UHGRuntimeException(String message) {
        super(message);
    }

    public UHGRuntimeException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public UHGRuntimeException(Throwable throwable) {
        super(throwable);
    }
}