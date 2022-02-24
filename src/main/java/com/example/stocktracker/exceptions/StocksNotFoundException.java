package com.example.stocktracker.exceptions;

public class StocksNotFoundException extends Exception{
    public StocksNotFoundException() {
    }

    public StocksNotFoundException(String message) {
        super(message);
    }

    public StocksNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StocksNotFoundException(Throwable cause) {
        super(cause);
    }
}
