package com.ram.album.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    public boolean isNetworkConnected() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
}
