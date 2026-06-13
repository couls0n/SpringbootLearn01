package com.example.moslearn.exception;

public class OrderNotFoundException  extends RuntimeException{
    public OrderNotFoundException(long orderId) {
        super("Order with ID " + orderId + " not found.");
    }
}
