package com.example.stocktracker.exceptions;

public class EmptyPriceException extends  StockException{
    public EmptyPriceException() {
    }

    public EmptyPriceException(String message) {
        super(message);
    }

    public EmptyPriceException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyPriceException(Throwable cause) {
        super(cause);
    }
}
