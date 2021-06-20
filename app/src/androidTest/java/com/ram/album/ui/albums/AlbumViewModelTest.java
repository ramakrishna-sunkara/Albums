package com.ram.album;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.ram.album.base.BaseApplication;
import com.ram.album.data.api.IAlbumAPI;
import com.ram.album.data.room.AppDatabase;
import com.ram.album.data.room.daos.AlbumDao;
import com.ram.album.data.room.entities.AlbumEntity;
import com.ram.album.repositories.AlbumRepo;
import com.ram.album.ui.albums.AlbumService;
import com.ram.album.ui.albums.AlbumViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AlbumViewModelTest {
    private final String TAG = this.getClass().getSimpleName();

    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

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

    //@BeforeClass
    public static void setUpRxSchedulers() {
        Scheduler immediate = new Scheduler() {
            @Override
            public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
                // this prevents StackOverflowErrors when scheduling with a delay
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run, true);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }

    @Test
    public void getTestStringForEMH() {
        List<AlbumEntity> albumEntities = getMockAlbums();
        when(albumService.loadAlbums(true)).thenReturn(Single.just(albumEntities));
        albumViewModel.loadAlbums(true);
        verify(listObserver).onChanged(albumEntities);
        //assertEquals(1, viewModel.onAlbumsLoaded().getValue().size());
        //assertEquals("", "");
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