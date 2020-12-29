package com.online.store.exceptions;

public class ItemNotInCartException extends RuntimeException {
    public ItemNotInCartException() {
        super("This cart does not have this item.", new Exception());
    }
}
