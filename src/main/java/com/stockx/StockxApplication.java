package com.manav.stockx;

import com.manav.stockx.service.StockxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class StockxApplication {

	@Autowired
	StockxService stockxService;

	public static void main(String[] args) {
		SpringApplication.run(StockxApplication.class, args);
	}

	@PostConstruct
	public void addStocksOnStartup() {
		System.out.println("Adding stocks on startup");
		stockxService.addStocks();
	}

}
