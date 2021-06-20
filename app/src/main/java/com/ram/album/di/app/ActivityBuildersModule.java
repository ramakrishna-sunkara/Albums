package com.ram.album.di.app;

import com.ram.album.di.stock_list.AlbumListFragmentBuildersModule;
import com.ram.album.di.stock_list.AlbumListViewModelsModule;
import com.ram.album.ui.albums.AlbumsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = {AlbumListFragmentBuildersModule.class, AlbumListViewModelsModule.class})
    abstract AlbumsActivity contributesStockDetailsActivity();
}
