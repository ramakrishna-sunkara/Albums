package com.ram.album.data.api.room.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ram.album.data.api.responses.AlbumEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface AlbumDao {
    @Query("SELECT * FROM AlbumEntity")
    Flowable<List<AlbumEntity>> getAlbums();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<AlbumEntity> albumEntities);
}
