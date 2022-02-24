package com.example.stocktracker.data.repositories;

import com.example.stocktracker.data.model.Stock;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends MongoRepository<Stock,String> {
    Optional<Stock> findStockByStockName(String stockName);


    Page<Stock> findAll(Pageable pageable);
}
