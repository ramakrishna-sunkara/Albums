package com.ram.album.di.stock_list;

import androidx.lifecycle.ViewModel;

import com.ram.album.di.app.ViewModelKey;
import com.ram.album.ui.albums.AlbumViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AlbumListViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(AlbumViewModel.class)
    public abstract ViewModel bindUploadImagesViewModel(AlbumViewModel viewModel);


}
