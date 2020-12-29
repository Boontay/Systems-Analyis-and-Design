package com.online.store.exceptions;

public class UnsuccessfulDeliveryException extends RuntimeException {
    public UnsuccessfulDeliveryException() {
        super("Delivery unsuccessful.", new Exception());
    }
}
