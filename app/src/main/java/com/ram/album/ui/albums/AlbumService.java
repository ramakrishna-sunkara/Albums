package com.ram.album.ui.albums;

import android.app.Application;

import com.ram.album.data.api.IAlbumAPI;
import com.ram.album.data.room.AppDatabase;
import com.ram.album.data.room.entities.AlbumEntity;

import org.jetbrains.annotations.TestOnly;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class AlbumService {
    private static final String TAG = "StocksService";
    AppDatabase appDatabase;
    private IAlbumAPI iAlbumAPI;

    @Inject
    public AlbumService(Application application, IAlbumAPI iAlbumAPI) {
        this.iAlbumAPI = iAlbumAPI;
        appDatabase = AppDatabase.getInstance(application);
    }

    public Single<List<AlbumEntity>> loadAlbums(boolean networkConnected) {
        if (networkConnected) {
            return iAlbumAPI.loadAlbums();
        } else {
            return appDatabase.albumDao().getAlbums();
        }
    }

    public Completable addAlbums(List<AlbumEntity> albumEntities) {
        return appDatabase.albumDao().insertAll(albumEntities);
    }

    @TestOnly
    public void setAlbumAPI(IAlbumAPI iAlbumAPI) {
        this.iAlbumAPI = iAlbumAPI;
    }

    @TestOnly
    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @TestOnly
    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
