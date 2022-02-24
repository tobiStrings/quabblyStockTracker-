package com.example.stocktracker.exceptions;

public class NullStockNameException extends StockException{
    public NullStockNameException() {
    }

    public NullStockNameException(String message) {
        super(message);
    }

    public NullStockNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullStockNameException(Throwable cause) {
        super(cause);
    }
}
