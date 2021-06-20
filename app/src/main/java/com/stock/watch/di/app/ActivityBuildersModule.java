package com.stockapp.di.app;

import com.stockapp.di.stock_list.StockListFragmentBuildersModule;
import com.stockapp.di.stock_list.StockListViewModelsModule;
import com.stockapp.ui.activities.AlbumListActivity;
import com.stockapp.ui.stock_list.StockListFragment;
import com.stockapp.ui.stock_list.StockListViewModel;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = {StockListFragmentBuildersModule.class, StockListViewModelsModule.class})
    abstract AlbumListActivity contributesStockDetailsActivity();
}
