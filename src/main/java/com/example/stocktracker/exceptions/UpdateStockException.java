package com.example.stocktracker.exceptions;

public class UpdateStockException extends StockException{
    public UpdateStockException() {
    }

    public UpdateStockException(String message) {
        super(message);
    }

    public UpdateStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateStockException(Throwable cause) {
        super(cause);
    }
}
