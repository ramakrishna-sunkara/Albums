package com.ram.album.data.room.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ram.album.data.room.entities.AlbumEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface AlbumDao {
    @Query("SELECT * FROM AlbumEntity")
    Single<List<AlbumEntity>> getAlbums();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<AlbumEntity> albumEntities);
}
