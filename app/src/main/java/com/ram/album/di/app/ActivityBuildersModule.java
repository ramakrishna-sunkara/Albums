package com.ram.album.di.app;

import com.ram.album.di.albums.AlbumListFragmentBuildersModule;
import com.ram.album.di.albums.AlbumListViewModelsModule;
import com.ram.album.di.login.LoginFragmentBuildersModule;
import com.ram.album.di.login.LoginViewModelsModule;
import com.ram.album.ui.albums.AlbumsActivity;
import com.ram.album.ui.login.LoginActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = {AlbumListFragmentBuildersModule.class, AlbumListViewModelsModule.class})
    abstract AlbumsActivity contributesStockDetailsActivity();

    @ContributesAndroidInjector(modules = {LoginFragmentBuildersModule.class, LoginViewModelsModule.class})
    abstract LoginActivity contributesLoginActivity();
}
