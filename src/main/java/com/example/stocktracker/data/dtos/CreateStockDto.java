package com.example.stocktracker.data.dtos;

import lombok.Data;

@Data
public class CreateStockDto {
    private String stockName;
    private double stockPrice;
}
