package com.ram.album.ui.albums;

import android.os.Bundle;

import com.ram.album.R;
import com.ram.album.base.BaseActivity;

public class AlbumsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        AlbumListFragment albumListFragment = new AlbumListFragment();
        loadFragment(R.id.actRootLayout, albumListFragment, false, true);
    }
}
