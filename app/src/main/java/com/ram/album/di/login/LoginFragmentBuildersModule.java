package com.ram.album.di.login;

import com.ram.album.ui.login.LoginFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class LoginFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract LoginFragment contributeLoginFragment();

}
