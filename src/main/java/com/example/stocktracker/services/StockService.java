package com.example.stocktracker.services;

import com.example.stocktracker.data.dtos.CreateStockDto;
import com.example.stocktracker.data.dtos.UpdateStockDto;
import com.example.stocktracker.data.model.Stock;
import com.example.stocktracker.exceptions.StockDoesNotExistException;
import com.example.stocktracker.exceptions.StockException;
import com.example.stocktracker.exceptions.StocksNotFoundException;
import com.example.stocktracker.exceptions.UpdateStockException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface StockService {
    Stock createStock(CreateStockDto stockDto) throws StockException;
    Stock findStockById(String stockId) throws StockDoesNotExistException;
    Stock updateStock(UpdateStockDto stockUpdate,String id) throws UpdateStockException, StockDoesNotExistException;
    void deleteStockById(String id) throws StockDoesNotExistException;

    List<Stock> findAllStock(Pageable pageable) throws StocksNotFoundException;

    Stock findStockByName(String stockName) throws StockDoesNotExistException;
}
