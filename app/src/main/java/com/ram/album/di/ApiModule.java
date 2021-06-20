package com.ram.album.di;

import com.ram.album.data.api.IAlbumAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public abstract class ApiModule {

    @Provides
    @Singleton
    static IAlbumAPI providesMerchantApi(Retrofit retrofit) {
        return retrofit.create(IAlbumAPI.class);
    }

}