package com.ram.album.ui.albums;

import com.ram.album.base.BaseApplication;
import com.ram.album.data.api.IAlbumAPI;
import com.ram.album.data.room.AppDatabase;
import com.ram.album.data.room.daos.AlbumDao;
import com.ram.album.data.room.entities.AlbumEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AlbumServiceTest {
    @Mock
    public BaseApplication application;

    @Mock
    public IAlbumAPI iAlbumAPI;

    @Mock
    AppDatabase appDatabase;

    AlbumService albumService;

    @Mock
    AlbumDao albumDao;

    @Before
    public void setup() throws Exception {
        albumService = new AlbumService(application, iAlbumAPI);
        albumService.setAppDatabase(appDatabase);
        when(appDatabase.albumDao()).thenReturn(albumDao);
    }

    @Test
    public void testLoadAlbums() {
        when(iAlbumAPI.loadAlbums()).thenReturn(Single.just(getMockAlbums()));
        Single<List<AlbumEntity>> single = albumService.loadAlbums(true);
        assertEquals(1, single.blockingGet().size());
    }

    @Test
    public void testLoadAlbumsDB() {
        when(albumDao.getAlbums()).thenReturn(Single.just(getMockAlbums()));
        Single<List<AlbumEntity>> single = albumService.loadAlbums(false);
        assertEquals(1, single.blockingGet().size());
    }

    private List<AlbumEntity> getMockAlbums() {
        List<AlbumEntity> albumEntities = new ArrayList<>();
        albumEntities.add(new AlbumEntity(1, 11, "Album1"));
        return albumEntities;
    }


}