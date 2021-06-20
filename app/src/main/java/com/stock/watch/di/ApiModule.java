package com.stockapp.di;

import com.stockapp.network.apis.StockApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public abstract class ApiModule {

    @Provides
    @Singleton
    static StockApi providesMerchantApi(Retrofit retrofit) {
        return retrofit.create(StockApi.class);
    }

}