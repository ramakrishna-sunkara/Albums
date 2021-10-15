package com.ram.album.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.ram.album.R;
import com.ram.album.base.BaseFragment;
import com.ram.album.databinding.FragmentLoginBinding;
import com.ram.album.ui.albums.AlbumService;

import javax.inject.Inject;

public class LoginFragment extends BaseFragment<LoginViewModel> {

    FragmentLoginBinding binding;

    @Inject
    AlbumService loginService;

    @NonNull
    @Override
    protected Class<LoginViewModel> viewModel() {
        return LoginViewModel.class;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
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
    }

    /**
     * Register for observables
     */
    private void registerObservables() {

    }
}
