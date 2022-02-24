package com.example.stocktracker.web.controller;

import com.example.stocktracker.data.dtos.CreateStockDto;
import com.example.stocktracker.data.dtos.UpdateStockDto;
import com.example.stocktracker.exceptions.StockDoesNotExistException;
import com.example.stocktracker.exceptions.StockException;
import com.example.stocktracker.exceptions.StocksNotFoundException;
import com.example.stocktracker.services.StockServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
@Slf4j
public class StockController {
    private final StockServiceImpl stockServiceImpl;
    @PostMapping()
    public ResponseEntity<?>createStock(@RequestBody CreateStockDto stockDto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(stockServiceImpl.createStock(stockDto));
        } catch (StockException e) {
            log.info(e.getLocalizedMessage());
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>getStockById(@PathVariable String id){
        try {
            return ResponseEntity.status(HttpStatus.FOUND).body(stockServiceImpl.findStockById(id));
        }catch (StockDoesNotExistException ex){
            log.info(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getLocalizedMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllStock(@RequestParam(required = true, defaultValue = "0", value = "start") int start,
                                         @RequestParam(required = true, defaultValue = "10", value = "end") int end){
        try{
            Pageable pageable = PageRequest.of(start, end);
            return ResponseEntity.status(HttpStatus.FOUND).body(stockServiceImpl.findAllStock(pageable));
        }catch (StocksNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getLocalizedMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStock(@PathVariable String id, @RequestBody UpdateStockDto updateStockDto) {
        try {
            return ResponseEntity.ok().body(stockServiceImpl.updateStock(updateStockDto, id));
        } catch (StockException e) {
            log.info(e.getLocalizedMessage());
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStock(@PathVariable String id){
        try {
            stockServiceImpl.deleteStockById(id);
            return ResponseEntity.ok().body("Stock successfully deleted");
        } catch (StockDoesNotExistException e) {
            log.info(e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getLocalizedMessage());
        }
    }
}
