package com.online.store.exceptions;

public class ItemOutOfStockException extends RuntimeException {
    public ItemOutOfStockException() {
        super("Item not added to cart. It is out of stock.", new Exception());
    }
}
