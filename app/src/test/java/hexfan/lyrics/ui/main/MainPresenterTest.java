package hexfan.lyrics.ui.main;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.Callable;

import hexfan.lyrics.model.DataModel;
import hexfan.lyrics.model.pojo.TrackInfoRequest;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Pawel on 20.06.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    MainPresenter presenter;

    @Mock
    DataModel model;

    @Mock
    MainContract.View view;

    @Before
    public void setUp() throws Exception {
        RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(Callable<Scheduler> schedulerCallable) throws Exception {
                return Schedulers.trampoline();
            }
        });

        presenter = new MainPresenter(model);
        presenter.attach(view);
    }

    @After
    public void tearDown() throws Exception {
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
        presenter.detach();
    }

    @Test
    public void getTrackInfo(){
        String artist = "Scorpions";
        String track = "Robot Man";
        String albumTitle = "In Trance";
        String albumCoverUrl = "https://lastfm-img2.akamaized.net/i/u/300x300/2a427f78a6dfdfcb3fedbe9586c1c56e.png";
        TrackInfoRequest trackInfoRequest = new TrackInfoRequest();
        trackInfoRequest.setAlbumTitle(albumTitle);
        trackInfoRequest.setAlbumCoverUrl(albumCoverUrl);

        when(model.getTrackInfo(artist, track)).thenReturn(Observable.just(trackInfoRequest));

        presenter.getTrackInfo(artist, track);

        verify(view).displayNewTrack(trackInfoRequest);
    }
}