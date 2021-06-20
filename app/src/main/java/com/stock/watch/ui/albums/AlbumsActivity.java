package com.stock.watch.ui.stocks;

import android.os.Bundle;

import com.stock.watch.R;
import com.stock.watch.base.BaseActivity;
import com.stock.watch.di.AppComponent;

public class StockDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragment(R.id.actRootLayout, StockDetailsFragment.newInstance(getIntent().getExtras()), false, true);
    }

    @Override
    protected void inject(AppComponent appComponent) {
        appComponent.inject(this);
    }
}
