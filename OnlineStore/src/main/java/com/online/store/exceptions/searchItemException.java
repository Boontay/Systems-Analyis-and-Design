package com.online.store.exceptions;

public class searchItemException extends RuntimeException {
    public searchItemException() {
        super("No items to browse.", new Exception());
    }
}