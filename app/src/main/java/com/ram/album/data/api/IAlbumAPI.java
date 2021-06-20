package com.ram.album.data.api;

import com.ram.album.data.room.entities.AlbumEntity;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface IAlbumAPI {
    @GET("albums")
    Single<List<AlbumEntity>> loadAlbums();
}
