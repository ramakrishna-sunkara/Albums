package com.ram.album.ui.albums;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ram.album.R;
import com.ram.album.data.room.entities.AlbumEntity;
import com.ram.album.databinding.StockListItemBinding;

import java.util.List;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.AlbumItemHolder> {

    private List<AlbumEntity> albumList;
    private LayoutInflater layoutInflater;
    private StockListItemBinding binding;

    public AlbumListAdapter(List<AlbumEntity> albumList) {
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public AlbumItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.stock_list_item, parent, false);
        return new AlbumItemHolder(binding);
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onBindViewHolder(@NonNull AlbumItemHolder holder, int position) {
        final AlbumEntity result = albumList.get(position);
        holder.stockListItemBinding.setStock(result);
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public void addAlbumsList(List<AlbumEntity> updatedList) {
        albumList.clear();
        albumList.addAll(updatedList);
        notifyDataSetChanged();
    }

    class AlbumItemHolder extends RecyclerView.ViewHolder {

        StockListItemBinding stockListItemBinding;

        public AlbumItemHolder(@NonNull StockListItemBinding binding) {
            super(binding.getRoot());
            stockListItemBinding = binding;
        }
    }
}
