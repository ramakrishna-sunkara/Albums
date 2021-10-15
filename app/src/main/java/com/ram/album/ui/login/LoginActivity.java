package com.ram.album.ui.login;

import android.os.Bundle;

import com.ram.album.R;
import com.ram.album.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        LoginFragment loginFragment = new LoginFragment();
        loadFragment(R.id.actRootLayout, loginFragment, false, true);
    }
}
