package com.stock.watch.di.stock_list;

import androidx.lifecycle.ViewModel;

import com.stock.watch.di.app.ViewModelKey;
import com.stock.watch.ui.albums.AlbumViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class StockListViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(AlbumViewModel.class)
    public abstract ViewModel bindUploadImagesViewModel(AlbumViewModel viewModel);


}
