package com.stock.watch.ui.stocks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.stock.watch.R;
import com.stock.watch.api.responses.Result;
import com.stock.watch.databinding.StockListItemBinding;
import com.stock.watch.interfaces.IStockItemClickListener;
import com.stock.watch.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class StocksListAdapter extends RecyclerView.Adapter<StocksListAdapter.StockItemHolder> {

    private List<Result> stockResults;
    private LayoutInflater layoutInflater;
    private StockListItemBinding binding;
    private IStockItemClickListener iStockItemClickListener;
    private Context context;

    public StocksListAdapter(List<Result> stockResults) {
        this.stockResults = stockResults;
    }

    @NonNull
    @Override
    public StockItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.stock_list_item, parent, false);
        return new StocksListAdapter.StockItemHolder(binding);
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onBindViewHolder(@NonNull StockItemHolder holder, int position) {
        final Result result = stockResults.get(position);
        holder.stockListItemBinding.tvStockName.setText(result.getShortName());
        double regularMarketPrice = StringUtils.roundRegularMarketPrice(result.getRegularMarketPrice());
        double regularMarketChangePercent = 0;
        if (null != result.getRegularMarketChangePercent()) {
            regularMarketChangePercent = result.getRegularMarketChangePercent();
        }
        if (regularMarketChangePercent >= 0) {
            holder.stockListItemBinding.tvStockPercentage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_stock_up, 0, 0, 0);
            holder.stockListItemBinding.tvStockPercentage.setTextColor(ContextCompat.getColor(context, R.color.green1));
        } else {
            holder.stockListItemBinding.tvStockPercentage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_stock_down, 0, 0, 0);
            holder.stockListItemBinding.tvStockPercentage.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        }
        holder.stockListItemBinding.tvStockValue.setText(String.format("%s $", regularMarketPrice));
        holder.stockListItemBinding.tvStockType.setText(result.getExchange());
        holder.stockListItemBinding.tvStockPercentage.setText(
                String.format(context.getString(R.string.regular_market_change_percentage),
                        StringUtils.roundValue(result.getRegularMarketChange()),
                        StringUtils.roundValue(regularMarketChangePercent)));
        holder.stockListItemBinding.clStockItemContainer.setOnClickListener(view -> {
            if (null != iStockItemClickListener) {
                iStockItemClickListener.onStockItemClick(result);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stockResults.size();
    }

    public void updateStockList(List<Result> updatedList) {
        stockResults.clear();
        stockResults.addAll(updatedList);
        notifyDataSetChanged();
    }

    public void setStockItemClickListener(IStockItemClickListener iStockItemClickListener) {
        this.iStockItemClickListener = iStockItemClickListener;
    }

    public void filter(ArrayList<Result> baseResults, String searchString) {
        if (StringUtils.isEmpty(searchString)) {
            updateStockList(baseResults);
        }
        List<Result> filterList = new ArrayList<>();
        for (Result result : baseResults) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (result.getShortName().contains(searchString)) {
                filterList.add(result);
            }
        }
        //update recyclerview
        updateStockList(filterList);
    }

    class StockItemHolder extends RecyclerView.ViewHolder {

        StockListItemBinding stockListItemBinding;

        public StockItemHolder(@NonNull StockListItemBinding binding) {
            super(binding.getRoot());
            stockListItemBinding = binding;
        }
    }
}
