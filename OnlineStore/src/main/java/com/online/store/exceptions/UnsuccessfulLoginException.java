package com.online.store.exceptions;

public class UnsuccessfulLoginException extends RuntimeException {
    public UnsuccessfulLoginException() {
        super("Login unsuccessul.", new Exception());
    }
}
