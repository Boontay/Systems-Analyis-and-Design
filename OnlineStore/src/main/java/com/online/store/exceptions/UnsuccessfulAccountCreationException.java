package com.online.store.exceptions;


public class UnsuccessfulAccountCreationException extends RuntimeException {
    public UnsuccessfulAccountCreationException() {
        super("Unsuccessful account Creation.", new Exception());
    }
}
