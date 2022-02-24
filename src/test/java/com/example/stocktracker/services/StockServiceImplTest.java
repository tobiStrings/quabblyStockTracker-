package com.example.stocktracker.services;

import com.example.stocktracker.data.dtos.CreateStockDto;
import com.example.stocktracker.data.dtos.UpdateStockDto;
import com.example.stocktracker.data.model.Stock;
import com.example.stocktracker.data.repositories.StockRepository;
import com.example.stocktracker.exceptions.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class StockServiceImplTest {
    @Mock
    private StockRepository stockRepository;
    @InjectMocks
    private StockServiceImpl stockServiceImpl;

    private Stock stock;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        stock = new Stock();
        stock.setStockName("Pepsi");
        stock.setCurrentPrice(20.0);
        stock.setId("553635");
    }

    @Test
    @DisplayName("Test that stock can be saved when all the conditions are met.")
    void createStockWhenAllConditionsAreMet() throws StockException {
        CreateStockDto stockDto =  new CreateStockDto();
        stockDto.setStockName("Pepsi");
        stockDto.setStockPrice(20.0);

        when(stockRepository.save(any())).thenReturn(stock);

        Stock returnedStock = stockServiceImpl.createStock(stockDto);
        assertThat(returnedStock).isNotNull();
    }

    @Test
    @DisplayName("Test that stock cannot be saved if the stock name is not given")
    void saveStockWhenNameIsNotProvided(){
        CreateStockDto stockDto =  new CreateStockDto();
        stockDto.setStockPrice(20.0);
        stockDto.setStockName("");
        assertThrows(NullStockNameException.class,()->stockServiceImpl.createStock(stockDto));
    }

    @Test
    @DisplayName("Test that stock cannot be saved if the stock price is not given")
    void saveStockWhenPriceIsNotProvided(){
        CreateStockDto stockDto =  new CreateStockDto();
        stockDto.setStockName("Pepsi");
        assertThrows(EmptyPriceException.class,()->stockServiceImpl.createStock(stockDto));
    }

    @Test
    @DisplayName("Test that stock can be gotten by it's id after it has been saved")
    void findStockById() throws StockDoesNotExistException {
       when(stockRepository.findById(stock.getId())).thenReturn(Optional.of(stock));
       Stock newStock  = stockServiceImpl.findStockById(stock.getId());
       assertThat(newStock).isNotNull();
       assertThat(newStock).isEqualTo(stock);
    }

    @Test
    @DisplayName("Test that stock cannot be found if the id provided is wrong")
    void findStockWithWrongId(){
        when(stockRepository.findById(stock.getId())).thenReturn(Optional.empty());
        assertThrows(StockDoesNotExistException.class, ()-> stockServiceImpl.findStockById(stock.getId()));
    }

    @Test
    @DisplayName("Test that  all stocks can be retrieved when the database bis not empty")
    void findAllStockWhenDatabaseIsNotEmpty() throws StocksNotFoundException {
        List<Stock> list =  new ArrayList<>();
        list.add(stock);
        Page<Stock> page = new PageImpl<>(list);

        int start = 0;
        int end = 10;
        Pageable pageable = PageRequest.of(start, end);
        when(stockRepository.findAll(pageable)).thenReturn(page);

        List<Stock> foundStocks = stockServiceImpl.findAllStock(pageable);

        assertThat(foundStocks).contains(stock);
        assertThat(foundStocks).hasSize(1);
    }

    @Test
    @DisplayName("Test that stocks cannot be retrieved when the database is empty")
    void findAllStocksWhenDatabaseIsEmpty(){
        Page<Stock> page = new PageImpl<>(new ArrayList<>());
        int start = 0;
        int end = 10;
        Pageable pageable = PageRequest.of(start, end);
        when(stockRepository.findAll(pageable)).thenReturn(page);
        assertThrows(StocksNotFoundException.class, ()->stockServiceImpl.findAllStock(pageable));
    }

    @Test
    @DisplayName("Test that stock can be updated when the dto inputs are correctly validated")
    void updateStock() throws StockDoesNotExistException, UpdateStockException {
        UpdateStockDto updateStockDto = new UpdateStockDto();
        updateStockDto.setStockName("Coke");
        updateStockDto.setStockPrice(30.0);

        when(stockRepository.findById(stock.getId())).thenReturn(Optional.of(stock));
        when(stockRepository.save(any())).thenReturn(stock);

        Stock returnedStock = stockServiceImpl.updateStock(updateStockDto,stock.getId());

        assertThat(returnedStock).isNotNull();
        assertThat(returnedStock).isEqualTo(stock);

    }

    @Test
    @DisplayName("Test that stock cannot be updated if the stock name is not in the database")
    void updateUnresistingStock(){
        UpdateStockDto updateStockDto = new UpdateStockDto();
        updateStockDto.setStockName("Coke");
        updateStockDto.setStockPrice(30.0);

        when(stockRepository.findById(stock.getId())).thenReturn(Optional.empty());

        assertThrows(StockDoesNotExistException.class, ()-> stockServiceImpl.updateStock(updateStockDto,stock.getId()));
    }

    @Test
    @DisplayName("Test that existing stock can be deleted by id")
    void deleteExistingStockById() throws StockDoesNotExistException {
        when(stockRepository.findById(stock.getId())).thenReturn(Optional.of(stock));
        Stock foundStock = stockServiceImpl.findStockById(stock.getId());

        assertThat(foundStock).isEqualTo(stock);

        stockServiceImpl.deleteStockById(stock.getId());

        when(stockRepository.findById(stock.getId())).thenReturn(Optional.empty());

        Optional<Stock>foundStock2 = stockRepository.findById(stock.getId());
        assertThat(foundStock2).isNotPresent();
    }

    @Test
    @DisplayName("Test that un-existing stock cannot be deleted")
    void deleteUnExistingStockById(){
        when(stockRepository.findById(stock.getId())).thenReturn(Optional.empty());
        assertThrows(StockDoesNotExistException.class, ()-> stockServiceImpl.deleteStockById(stock.getId()));
    }

}