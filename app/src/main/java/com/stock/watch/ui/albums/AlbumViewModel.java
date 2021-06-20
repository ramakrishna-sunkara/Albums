package com.stock.watch.ui.stocks;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.stock.watch.api.responses.Album;
import com.stock.watch.api.responses.Result;
import com.stock.watch.api.responses.StockDetailsResponse;
import com.stock.watch.api.responses.StockPrice;
import com.stock.watch.utils.AppSharedPreferences;
import com.stock.watch.utils.ChartInterval;
import com.stock.watch.utils.StringUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class StocksViewModel extends ViewModel {

    private static final String TAG = "SettingsViewModel";
    private final MutableLiveData<List<Result>> stockResults = new MutableLiveData<>();
    private final MutableLiveData<List<Result>> stockWishListResults = new MutableLiveData<>();
    private final MutableLiveData<StockDetailsResponse> stockDetailsResponse = new MutableLiveData<>();
    private final MutableLiveData<List<StockPrice>> stockPriceList = new MutableLiveData<>();
    private final MutableLiveData<Boolean> stockListLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> stockDetailsLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> albumListErrorLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Album>> albumListLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private StocksService stocksService;
    private CompositeDisposable disposable;
    private AppSharedPreferences appSharedPreferences;
    private String searchSymbol;

    @Inject
    public StocksViewModel() {
        disposable = new CompositeDisposable();
    }

    public void setSettingsService(StocksService stocksService) {
        this.stocksService = stocksService;
    }

    public void setAppSharedPreferences(AppSharedPreferences appSharedPreferences) {
        this.appSharedPreferences = appSharedPreferences;
    }

    public MutableLiveData<List<Album>> onAlbumsLoaded() {
        return albumListLiveData;
    }

    public MutableLiveData<Boolean> onAlbumsLoadError() {
        return albumListErrorLiveData;
    }

    LiveData<List<Result>> getStockResults() {
        return stockResults;
    }

    LiveData<List<Result>> getWishListResults() {
        return stockWishListResults;
    }

    LiveData<StockDetailsResponse> getStockDetailsResponse() {
        return stockDetailsResponse;
    }

    LiveData<List<StockPrice>> getStockPriceList() {
        return stockPriceList;
    }

    LiveData<Boolean> getStockListError() {
        return stockListLoadError;
    }

    LiveData<Boolean> getStockDetailsError() {
        return stockDetailsLoadError;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    /**
     * For specific symbol searched by user
     *
     * @param symbol
     */
    public void fetchStockQuotes(String symbol) {
        searchSymbol = symbol;
        ArrayList<String> list = new ArrayList<>();
        list.add(symbol);
        fetchStockQuotes(list);
    }

    /**
     * Currently we are using fixed symbols to display in list when page load
     */
    public void fetchAllStockQuotes() {
        fetchStockQuotes(appSharedPreferences.getSearchStocks());
    }

    /**
     * for loading wish listed stocks
     */
    public void fetchAllWishListStocks() {
        ArrayList<String> symbols = appSharedPreferences.getWishListStocks();
        if (null == symbols || symbols.isEmpty())
            return;
        loading.setValue(true);
        disposable.add(stocksService.getStockQuotes(symbols)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    stockListLoadError.setValue(false);
                    loading.setValue(false);
                    stockWishListResults.setValue(response.getQuoteResponse().getResult());
                }, throwable -> {
                    stockListLoadError.setValue(true);
                    loading.setValue(false);
                }));
    }

    /**
     * for loading wish listed stocks
     */
    public void loadAlbums() {
        ArrayList<String> symbols = appSharedPreferences.getWishListStocks();
        if (null == symbols || symbols.isEmpty())
            return;
        loading.setValue(true);
        disposable.add(stocksService.loadAlbums()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(albums -> {
                    loading.setValue(false);
                    if (null == albums || albums.isEmpty()) {
                        albumListErrorLiveData.setValue(true);
                        return;
                    }
                    albumListErrorLiveData.setValue(false);
                    albumListLiveData.setValue(albums);
                }, throwable -> {
                    loading.setValue(false);
                    stockListLoadError.setValue(true);
                }));
    }

    /**
     * Method call when page load and when search
     * In fresh installation symbols will be empty
     *
     * @param symbols
     */
    private void fetchStockQuotes(ArrayList<String> symbols) {
        if (null == symbols || symbols.isEmpty())
            return;
        loading.setValue(true);
        disposable.add(stocksService.getStockQuotes(symbols)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (!StringUtils.isEmpty(searchSymbol)) {
                        appSharedPreferences.saveSearchStocks(searchSymbol);
                        searchSymbol = "";
                    }
                    stockListLoadError.setValue(false);
                    loading.setValue(false);
                    stockResults.setValue(response.getQuoteResponse().getResult());
                }, throwable -> {
                    stockListLoadError.setValue(true);
                    loading.setValue(false);
                    searchSymbol = "";
                }));
    }

    /**
     * for fetching the stock details for specific story by using symbol
     *
     * @param symbol
     */
    public void fetchStockDetails(String symbol) {
        loading.setValue(true);
        disposable.add(stocksService.getStockDetails(symbol)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    stockDetailsLoadError.setValue(false);
                    stockDetailsResponse.setValue(response);
                    loading.setValue(false);
                }, throwable -> {
                    throwable.printStackTrace();
                    Log.e(TAG, "fetchStockDetails Error : " + throwable.getMessage());
                    stockDetailsLoadError.setValue(true);
                    loading.setValue(false);
                }));
    }

    /**
     * for clear all disposables
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }

    /**
     * for filter the list based on interval
     * need to support below O devices
     * @param interval
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void filterStockPrices(ChartInterval interval) {
        loading.setValue(true);
        List<StockPrice> filteredList = new ArrayList<>();
        try {
            LocalDate minLocalDate = LocalDate.now();
            LocalDate maxLocalDate = null;
            switch (interval) {
                case ONE_DAY:
                    maxLocalDate = LocalDate.now();
                    break;
                case FIVE_DAYS:
                    maxLocalDate = LocalDate.now().plusDays(5);
                    break;
                case SIX_MONTHS:
                    maxLocalDate = LocalDate.now().plusMonths(6);
                    break;
                case ONE_YEAR:
                    maxLocalDate = LocalDate.now().plusYears(1);
                    break;
                case FIVE_YEARS:
                    maxLocalDate = LocalDate.now().plusYears(5);
                    break;
            }

            if (maxLocalDate == null)
                stockPriceList.setValue(stockDetailsResponse.getValue().getStockPrices());

            Date minDate = Date.from(minLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date maxDate = Date.from(maxLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            for (StockPrice stockPrice :
                    stockDetailsResponse.getValue().getStockPrices()) {
                if (stockPrice.getDate().after(minDate) && stockPrice.getDate().before(maxDate)) {
                    filteredList.add(stockPrice);
                }
                //between map range function to try
            }
            stockPriceList.setValue(filteredList);
        } catch (Exception e) {
            stockPriceList.setValue(new ArrayList<>());
        }
        loading.setValue(false);
    }
}
