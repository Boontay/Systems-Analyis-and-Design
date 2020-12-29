package com.online.store.exceptions;

public class CartIsEmptyException extends RuntimeException {
    public CartIsEmptyException() {
        super("This cart is empty.", new Exception());
    }
}
