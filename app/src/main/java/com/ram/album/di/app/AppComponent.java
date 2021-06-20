package com.ram.album.di.app;

import android.app.Application;

import com.ram.album.base.BaseApplication;
import com.ram.album.di.ApiModule;
import com.ram.album.di.ViewModelFactoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {ActivityBuildersModule.class, ViewModelFactoryModule.class, AppModule.class, ApiModule.class, AndroidInjectionModule.class})
public interface AppComponent extends AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
