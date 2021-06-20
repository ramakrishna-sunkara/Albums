package com.stock.watch.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.stock.watch.R;

public abstract class BaseActivity extends AppCompatActivity {

    public boolean isNetworkConnected() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
        inject(getAppComponent());
        setContentView(getLayoutID());
    }

    protected int getLayoutID() {
        return R.layout.layout_activity_base;
    }

    public void loadFragment(int fragmentContainerID, Fragment fragment
            , boolean addTobackstack, boolean add) {

        try {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (!fragment.isAdded()) {
                if (add) {
                    fragmentTransaction.add(fragmentContainerID,
                            fragment, fragment.getClass().getSimpleName());
                } else {
                    fragmentTransaction.replace(fragmentContainerID,
                            fragment, fragment.getClass().getSimpleName());
                }
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                if (addTobackstack) {
                    fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());

                }
                fragmentTransaction.commitAllowingStateLoss();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        } else {
            finish();
        }
    }

    protected abstract void inject(AppComponent appComponent);

    protected AppComponent getAppComponent() {
        AppComponent appComponent
                = ((BaseApplication) getApplication())
                .getAppComponent();
        return appComponent;
    }
}
