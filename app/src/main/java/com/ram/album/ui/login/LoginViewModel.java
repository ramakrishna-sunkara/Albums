package com.ram.album.ui.login;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ram.album.base.BaseViewModel;
import com.ram.album.data.room.entities.AlbumEntity;
import com.ram.album.repositories.AlbumRepo;
import com.ram.album.ui.albums.AlbumService;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel {

    private static final String TAG = "LoginViewModel";
    public final MutableLiveData<List<AlbumEntity>> albumListLiveData = new MutableLiveData<List<AlbumEntity>>();
    private final MutableLiveData<Boolean> albumListErrorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private AlbumService loginService;
    private CompositeDisposable disposable;

    @Inject
    public LoginViewModel(AlbumRepo albumRepo) {
        disposable = new CompositeDisposable();
        loginService = albumRepo.getStockService();
    }

    public MutableLiveData<List<AlbumEntity>> onAlbumsLoaded() {
        return albumListLiveData;
    }

    public MutableLiveData<Boolean> onAlbumsLoadError() {
        return albumListErrorLiveData;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    @SuppressLint("CheckResult")
    public void insertAlbums() {
        if (albumListLiveData.getValue() == null || albumListLiveData.getValue().isEmpty())
            return;
        /*List<AlbumEntity> albumEntities = new ArrayList<>();
        albumListLiveData.getValue().forEach(album -> {
            albumEntities.add(new AlbumEntity(album.getUserId(), album.getId(), album.getTitle()));
        });*/
        disposable.add(loginService.addAlbums(albumListLiveData.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            Log.e(TAG, "save album success ");
                        }, throwable -> {
                            Log.e(TAG, "save album failed ");
                        }
                ));
    }

    /**
     * for loading wish listed stocks
     *
     * @param networkConnected
     */
    public void loadAlbums(boolean networkConnected) {
        loading.setValue(true);
        disposable.add(loginService.loadAlbums(networkConnected)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(albumEntities -> {
                    Collections.sort(albumEntities);
                    return albumEntities;
                })
                .subscribe(albums -> {
                    loading.setValue(false);
                    if (null == albums || albums.isEmpty()) {
                        albumListErrorLiveData.setValue(true);
                        return;
                    }
                    albumListErrorLiveData.setValue(false);
                    albumListLiveData.setValue(albums);
                    if (networkConnected) {
                        insertAlbums();
                    }
                }, throwable -> {
                    loading.setValue(false);
                    albumListErrorLiveData.setValue(true);
                }));
    }

    /**
     * for clear all disposables
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }

}
