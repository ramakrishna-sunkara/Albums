package com.stock.watch.api.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.stock.watch.api.daos.AlbumDao;
import com.stock.watch.api.responses.AlbumEntity;


@Database(entities = {AlbumEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AlbumDao albumDao();
}
