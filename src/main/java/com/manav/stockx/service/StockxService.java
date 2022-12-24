package com.manav.stockx.service;

import com.manav.stockx.model.Stock;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class StockxService {

    List<Stock> stocks = new ArrayList<>();
    Logger logger = LoggerFactory.getLogger("StockxService");

    public void addStocks() {
        Stock stock1 = Stock.builder().id(1).name("tata").price(14).build();
        Stock stock2 = Stock.builder().id(2).name("hdfc").price(15).build();
        Stock stock3 = Stock.builder().id(3).name("sbi").price(19).build();
        Stock stock4 = Stock.builder().id(4).name("pnb").price(52).build();
        stocks.addAll(Arrays.asList(stock1, stock2, stock3, stock4));
    }

    public Stock getStock(int id) {
        logger.info("received stock id is: {}",id);
        return stocks.stream().filter(f -> f.getId()==id).distinct().findAny().get();
    }

    public List<Integer> getHttpCodes(int n) {
        List<Integer> list = new ArrayList<>();
        Instant start = Instant.now();
        WebClient client = WebClient.builder()
                .baseUrl("https://pokeapi.co/api/v2")
                .build();
        Flux flux = sendRequest(IntStream.rangeClosed(1, n)
                .boxed()
                .collect(Collectors.toList()), client);
        flux.subscribe();
        Instant end = Instant.now();
        Duration d = Duration.between( start , end );

        System.out.println("Process finished in : "+d.getSeconds()+ " seconds");
        return list;
    }

    public Mono<String> getResponses(int id, WebClient client) {
        return client.get()
                .uri("/pokemon/{id}", id)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Flux sendRequest(List<Integer> userIds, WebClient client) {
        return Flux.fromIterable(userIds)
                .flatMap(id -> getResponses(id, client));
    }
}
