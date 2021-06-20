package com.stock.watch.di.stock_list;

import com.stock.watch.ui.albums.AlbumListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class StockListFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract AlbumListFragment contributeAlbumListFragment();

}
