package com.example.stocktracker.exceptions;

public class StockDoesNotExistException extends StockException{
    public StockDoesNotExistException() {
    }

    public StockDoesNotExistException(String message) {
        super(message);
    }

    public StockDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public StockDoesNotExistException(Throwable cause) {
        super(cause);
    }
}
