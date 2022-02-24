package com.example.stocktracker.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class Stock {
    @Id
    private String id;
    private String stockName;
    private double currentPrice;
    private LocalDateTime currentDate;
    private LocalDateTime updatedAt;
}
