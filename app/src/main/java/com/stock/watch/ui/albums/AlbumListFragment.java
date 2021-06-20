package com.stock.watch.ui.stocks;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.stock.watch.R;
import com.stock.watch.api.responses.Result;
import com.stock.watch.base.BaseFragment;
import com.stock.watch.databinding.StocksListFragmentBinding;
import com.stock.watch.di.AppComponent;
import com.stock.watch.utils.AppSharedPreferences;
import com.stock.watch.utils.Constants;
import com.stock.watch.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class StocksListFragment extends BaseFragment<StocksViewModel> {

    StocksListFragmentBinding binding;
    List<Result> stockResults;
    StocksListAdapter stocksListAdapter;
    ArrayList<String> searchedStocks;

    @Inject
    StocksService stocksService;

    AppSharedPreferences appSharedPreferences;

    public static StocksListFragment newInstance(Bundle args) {
        StocksListFragment fragment = new StocksListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewmodel = new ViewModelProvider(requireActivity()).get(StocksViewModel.class);
        stockResults = new ArrayList<>();
        appSharedPreferences = new AppSharedPreferences(getActivity());
        viewmodel.setSettingsService(stocksService);
        viewmodel.setAppSharedPreferences(appSharedPreferences);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.stocks_list_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerObservables();
        initView();
    }

    /**
     * initialising all requires
     */
    private void initView() {
        stocksListAdapter = new StocksListAdapter(stockResults);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.rvStocks.setLayoutManager(linearLayoutManager);
        binding.rvStocks.setAdapter(stocksListAdapter);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider));
        binding.rvStocks.addItemDecoration(itemDecorator);
        viewmodel.fetchAllStockQuotes();

        searchedStocks = appSharedPreferences.getSearchStocks();
        if (null == searchedStocks || searchedStocks.isEmpty()) {
            searchedStocks = new ArrayList<>();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, searchedStocks);
        binding.actSearch.setAdapter(adapter);
        stocksListAdapter.setStockItemClickListener(result -> {
            Intent intent = new Intent(getActivity(), StockDetailsActivity.class);
            intent.putExtra(Constants.EXTRA_STOCK_SYMBOL, result.getSymbol());
            startActivity(intent);
        });
        binding.swAppHeader.changeFilterIcon(ContextCompat.getDrawable(getActivity(), R.drawable.ic_see_watch_list));
        binding.swAppHeader.addFilterListClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StockWatchListActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Register for observables
     */
    private void registerObservables() {
        viewmodel.getStockResults().observe(getViewLifecycleOwner(), results -> {
            if (results != null) {
                stockResults = results;
                stocksListAdapter.updateStockList(results);
            }
        });

        viewmodel.getStockListError().observe(getViewLifecycleOwner(), isError -> {
            if (isError != null) if (isError) {
                binding.rvStocks.setVisibility(View.INVISIBLE);
                binding.tvError.setVisibility(View.VISIBLE);
            } else {
                binding.rvStocks.setVisibility(View.VISIBLE);
                binding.tvError.setVisibility(View.INVISIBLE);
            }
        });

        viewmodel.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                binding.pbLoader.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    binding.tvError.setVisibility(View.GONE);
                }
            }
        });
        binding.actSearch.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (StringUtils.isEmpty(binding.actSearch.getText().toString())) {
                    viewmodel.fetchAllStockQuotes();
                } else {
                    viewmodel.fetchStockQuotes(binding.actSearch.getText().toString().toUpperCase());
                }
                return true;
            }
            return false;
        });
    }

    @Override
    protected Class<StocksViewModel> getViewModel() {
        return StocksViewModel.class;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.stocks_list_fragment;
    }

    @Override
    protected void inject(AppComponent appComponent) {
        appComponent.inject(this);
    }

    @Override
    protected String getToolbarName() {
        return "";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
