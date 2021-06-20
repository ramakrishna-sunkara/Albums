package com.stock.watch.repositories;

import com.stock.watch.ui.albums.StocksService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StockRepo {
    private final StocksService stockService;

    @Inject
    public StockRepo(StocksService stockService) {
        this.stockService = stockService;
    }

    public StocksService getStockService() {
        return stockService;
    }


}
