package com.online.store.exceptions;

public class UnsuccessfulPaymentException extends RuntimeException {
    public UnsuccessfulPaymentException() {
        super("Payment unsuccessful.", new Exception());
    }
}
