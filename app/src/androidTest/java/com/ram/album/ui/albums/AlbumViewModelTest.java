package com.ram.album.ui.albums;

import androidx.lifecycle.Observer;

import com.ram.album.base.BaseApplication;
import com.ram.album.data.api.IAlbumAPI;
import com.ram.album.data.room.AppDatabase;
import com.ram.album.data.room.daos.AlbumDao;
import com.ram.album.data.room.entities.AlbumEntity;
import com.ram.album.repositories.AlbumRepo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AlbumViewModelTest {

   /* @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
*/
    @Mock
    public BaseApplication application;

    @Mock
    public IAlbumAPI iAlbumAPI;

    @Mock
    AppDatabase appDatabase;

    AlbumService albumService;

    @Mock
    AlbumDao albumDao;

    AlbumViewModel albumViewModel;

    AlbumRepo albumRepo;

    @Mock
    Observer<List<AlbumEntity>> listObserver;

    @Mock
    Observer<Boolean> loadingObserver;

    @Before
    public void setup() throws Exception {
        albumService = new AlbumService(application, iAlbumAPI);
        albumService.setAppDatabase(appDatabase);
        when(appDatabase.albumDao()).thenReturn(albumDao);
        albumRepo = new AlbumRepo(albumService);
        albumViewModel = new AlbumViewModel(albumRepo);
        albumViewModel.getLoading().observeForever(loadingObserver);
        albumViewModel.onAlbumsLoaded().observeForever(listObserver);
    }

    @Test
    public void test_loadAlbums() {
        List<AlbumEntity> albumEntities = getMockAlbums();
        when(albumService.loadAlbums(true)).thenReturn(Single.just(albumEntities));
        when(albumDao.insertAll(albumEntities)).thenReturn(Completable.fromSingle(Single.just(true)));
        albumViewModel.loadAlbums(true);
        assertNotNull(listObserver);
    }

    @After
    public void tearDown() throws Exception {
        albumViewModel = null;
    }

    private List<AlbumEntity> getMockAlbums() {
        List<AlbumEntity> albumEntities = new ArrayList<>();
        albumEntities.add(new AlbumEntity(1, 11, "Album1"));
        return albumEntities;
    }

}