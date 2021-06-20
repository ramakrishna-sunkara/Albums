package com.stock.watch.di.app;

import android.app.Application;

import com.stockapp.BaseApplication;
import com.stockapp.di.ApiModule;
import com.stockapp.di.ViewModelFactoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
//ApiModule.class,
@Component(modules = {ActivityBuildersModule.class, ApiModule.class, ViewModelFactoryModule.class, AppModule.class, AndroidInjectionModule.class})
public interface AppComponent extends AndroidInjector<BaseApplication> {

//    UserSessionManager userSessionManager();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
