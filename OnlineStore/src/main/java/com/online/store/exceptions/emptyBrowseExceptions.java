package com.online.store.exceptions;

public class emptyBrowseExceptions extends RuntimeException {
    public emptyBrowseExceptions() {
        super("No items to browse.", new Exception());
    }
}