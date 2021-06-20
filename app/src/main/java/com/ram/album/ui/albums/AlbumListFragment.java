package com.ram.album.ui.albums;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ram.album.R;
import com.ram.album.base.BaseFragment;
import com.ram.album.databinding.AlbumListFragmentBinding;
import com.ram.album.di.ViewModelProviderFactory;

import java.util.ArrayList;

import javax.inject.Inject;

public class AlbumListFragment extends BaseFragment<AlbumViewModel> {

    AlbumListFragmentBinding binding;
    AlbumListAdapter albumListAdapter;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Inject
    AlbumService albumService;

    @NonNull
    @Override
    protected Class<AlbumViewModel> viewModel() {
        return AlbumViewModel.class;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.album_list_fragment, container, false);
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
        albumListAdapter = new AlbumListAdapter(new ArrayList<>());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(albumListAdapter);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider));
        binding.recyclerView.addItemDecoration(itemDecorator);
        getViewModel().loadAlbums(isNetworkConnected());

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            binding.swipeRefreshLayout.setRefreshing(false);
            getViewModel().loadAlbums(isNetworkConnected());
        });
        binding.errorView.btnRetry.setOnClickListener(v -> {
            binding.errorView.rlErrorView.setVisibility(View.GONE);
            getViewModel().loadAlbums(isNetworkConnected());
        });
    }

    /**
     * Register for observables
     */
    private void registerObservables() {
        getViewModel().onAlbumsLoaded().observe(getViewLifecycleOwner(), albums -> {
            albumListAdapter.addAlbumsList(albums);
        });

        getViewModel().onAlbumsLoadError().observe(getViewLifecycleOwner(), isError -> {
            if (isError) {
                binding.recyclerView.setVisibility(View.GONE);
                binding.errorView.rlErrorView.setVisibility(View.VISIBLE);
            } else {
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.errorView.rlErrorView.setVisibility(View.GONE);
            }
        });

        getViewModel().getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                binding.loadingView.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
            } else {
                binding.loadingView.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
