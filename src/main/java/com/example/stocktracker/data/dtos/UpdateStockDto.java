package com.example.stocktracker.data.dtos;

import lombok.Data;

@Data
public class UpdateStockDto {
    private String stockName;
    private double stockPrice;
}
