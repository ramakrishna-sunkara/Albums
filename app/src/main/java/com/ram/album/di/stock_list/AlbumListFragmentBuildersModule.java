package com.ram.album.di.stock_list;

import com.ram.album.ui.albums.AlbumListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AlbumListFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract AlbumListFragment contributeAlbumListFragment();

}
