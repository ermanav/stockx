package com.manav.stockx.controller;

import com.manav.stockx.model.Stock;
import com.manav.stockx.service.StockxService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class StockController {

    Logger logger = LoggerFactory.getLogger("StockController");

    @Autowired
    StockxService stockxService;

    /*
    This method can produce output list of stocks in json as well as xml depending upon the header passed
     */
    @GetMapping(value = "/stocks/{id}", produces={"application/json","application/xml", "text/xml "})
    public ResponseEntity<Stock> getStocks(@PathVariable int id) {
        MDC.put("sessionId", "Dorothy"+id);
        logger.info("stock id is: {}",id);
        return new ResponseEntity<Stock>(stockxService.getStock(id), HttpStatus.OK);
    }

    /*
    This will use reactive programming webclient for testing performance compared to python and node
     */
    @GetMapping(value = "/http/{limit}", produces={"application/json","application/xml", "text/xml "})
    public ResponseEntity<List<?>> getHttpCodesForStocks(@PathVariable int limit) {
        return new ResponseEntity<List<?>>(stockxService.getHttpCodes(limit), HttpStatus.OK);
    }
}
