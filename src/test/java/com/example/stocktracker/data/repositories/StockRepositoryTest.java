package com.example.stocktracker.data.repositories;

import com.example.stocktracker.data.model.Stock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class StockRepositoryTest {
    @Autowired
    private StockRepository stockRepositoryImpl;
    private Stock stock;
    @BeforeEach
    void setUp() {
        stock =  new Stock();
        stock.setStockName("Pepsi");
        stock.setCurrentPrice(30.0);
        stock.setCurrentDate(LocalDateTime.now());
        stock.setUpdatedAt(LocalDateTime.now());
    }

    @AfterEach
    void tearDown() {
        stock = null;
    }

    @Test
    @DisplayName("Test that stock can be saved and stock id is generated")
    void saveStock(){
        assertThat(stock.getId()).isNull();
        stockRepositoryImpl.save(stock);
        assertThat(stock.getId()).isNotNull();
        assertThat(stock.getId()).isNotEmpty();
    }

    @Test
    @DisplayName("Test that stock can be found by the id generated")
    void findStockById(){
        assertThat(stock.getId()).isNull();
        stockRepositoryImpl.save(stock);
        assertThat(stock.getId()).isNotNull();
        assertThat(stock.getId()).isNotEmpty();

        Optional<Stock> returnedStock = stockRepositoryImpl.findById(stock.getId());

        assertThat(returnedStock).isPresent();
        assertThat(returnedStock.get().getStockName()).isEqualTo(stock.getStockName());
    }

    @Test
    @DisplayName("Test that all stocks in the database can be found")
    void findAllStock(){
        assertThat(stock.getId()).isNull();
        stockRepositoryImpl.save(stock);
        assertThat(stock.getId()).isNotNull();
        assertThat(stock.getId()).isNotEmpty();

        List<Stock> stocks = stockRepositoryImpl.findAll();
        assertThat(stocks).hasSizeBetween(1,Integer.MAX_VALUE);
    }

    @Test
    @DisplayName("Test that stock van be deleted by it's id")
    void deleteStockById(){
        assertThat(stock.getId()).isNull();
        stockRepositoryImpl.save(stock);
        assertThat(stock.getId()).isNotNull();
        assertThat(stock.getId()).isNotEmpty();

        Optional<Stock> returnedStock = stockRepositoryImpl.findById(stock.getId());

        assertThat(returnedStock).isPresent();
        assertThat(returnedStock.get().getStockName()).isEqualTo(stock.getStockName());

        stockRepositoryImpl.deleteById(stock.getId());

        returnedStock = stockRepositoryImpl.findById(stock.getId());

        assertThat(returnedStock).isNotPresent();
    }

   @Test
   @DisplayName("Test that stock can be found by name")
   void findByName(){
       assertThat(stock.getId()).isNull();
       stockRepositoryImpl.save(stock);
       assertThat(stock.getId()).isNotNull();
       assertThat(stock.getId()).isNotEmpty();

       Optional<Stock> foundStock = stockRepositoryImpl.findStockByStockName(stock.getStockName());
       assertThat(foundStock).get().isNotNull();
   }
}