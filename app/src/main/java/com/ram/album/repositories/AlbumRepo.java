package com.ram.album.repositories;

import com.ram.album.ui.albums.AlbumService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AlbumRepo {
    private final AlbumService stockService;

    @Inject
    public AlbumRepo(AlbumService stockService) {
        this.stockService = stockService;
    }

    public AlbumService getStockService() {
        return stockService;
    }


}
