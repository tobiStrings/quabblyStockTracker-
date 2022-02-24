package com.example.stocktracker.services;

import com.example.stocktracker.data.dtos.CreateStockDto;
import com.example.stocktracker.data.dtos.UpdateStockDto;
import com.example.stocktracker.data.model.Stock;
import com.example.stocktracker.data.repositories.StockRepository;
import com.example.stocktracker.exceptions.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockServiceImpl implements StockService{
    private final StockRepository stockRepositoryImpl;

    @Override
    public Stock createStock(CreateStockDto stockDto) throws StockException {
        validateCreateStockDtoInputs(stockDto);


        Stock stockToSave =  new Stock();
        stockToSave.setStockName(stockDto.getStockName());
        stockToSave.setCurrentPrice(stockDto.getStockPrice());
        stockToSave.setCurrentDate(LocalDateTime.now());
        stockToSave.setUpdatedAt(LocalDateTime.now());
        stockToSave = stockRepositoryImpl.save(stockToSave);
        return stockToSave;
    }

    @Override
    public Stock findStockById(String stockId) throws StockDoesNotExistException {
        Optional<Stock> foundStock = stockRepositoryImpl.findById(stockId);
        if(foundStock.isPresent()){
            return foundStock.get();
        }
        throw new StockDoesNotExistException("Stock with id "+stockId+" does not exist");
    }

    @Override
    public List<Stock> findAllStock(Pageable pageable) throws StocksNotFoundException {
        List<Stock> stocks = stockRepositoryImpl.findAll(pageable).getContent();
        if(!stocks.isEmpty()){
            return stocks;
        }
        throw new StocksNotFoundException("Please make sure that stocks are available in the database");
    }

    @Override
    public Stock findStockByName(String stockName) throws StockDoesNotExistException {
        Optional<Stock> foundStock = stockRepositoryImpl.findStockByStockName(stockName);
        if (foundStock.isPresent()){
            return foundStock.get();
        }
        throw new StockDoesNotExistException("Please make sure the stock name is correct");
    }

    @Override
    public Stock updateStock(UpdateStockDto stockUpdate,String id) throws UpdateStockException, StockDoesNotExistException {
        validateUpdateStockDto(stockUpdate);
        Stock foundStock = findStockById(id);
        if(stockUpdate.getStockName().isEmpty() || stockUpdate.getStockName().isBlank()){
            stockUpdate.setStockName(foundStock.getStockName());
        }
        foundStock.setStockName(stockUpdate.getStockName());
        foundStock.setCurrentPrice(stockUpdate.getStockPrice());
        foundStock.setUpdatedAt(LocalDateTime.now());

        return stockRepositoryImpl.save(foundStock);
    }

    @Override
    public void deleteStockById(String id) throws StockDoesNotExistException {
        Stock stockToDelete = findStockById(id);
        stockRepositoryImpl.deleteById(stockToDelete.getId());
    }



    private void validateCreateStockDtoInputs(CreateStockDto createStockDto) throws StockException {
        if(createStockDto.getStockName().equals("") ||
                createStockDto.getStockName().isBlank() || createStockDto.getStockName().isEmpty()){
            throw new NullStockNameException("Please enter stock name");
        }

        if(createStockDto.getStockPrice() == 0.0){
            throw new EmptyPriceException("Please enter stock price");
        }
    }

    private void validateUpdateStockDto(UpdateStockDto updateStockDto) throws UpdateStockException {
        if ((updateStockDto.getStockName().equals("") || updateStockDto.getStockName().isEmpty()
                || updateStockDto.getStockName().isBlank() ) || updateStockDto.getStockPrice() == 0.0){
            throw new UpdateStockException("Please enter name to update or price to update");
        }

    }

}
