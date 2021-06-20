package com.ram.album.data.api;

import com.ram.album.data.api.responses.AlbumEntity;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface IStockAPI {
    @GET("albums")
    Flowable<List<AlbumEntity>> loadAlbums();
}
