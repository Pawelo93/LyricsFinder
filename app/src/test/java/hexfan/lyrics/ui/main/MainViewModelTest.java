package hexfan.lyrics.ui.main;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Objects;
import java.util.concurrent.Callable;

import hexfan.lyrics.model.DataModel;
import hexfan.lyrics.model.DatabaseDataModel;
import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.model.pojo.TrackInfoRequest;
import hexfan.lyrics.model.spotify.SpotifyModel;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.when;

/**
 * Created by Pawel on 18.10.2017.
 */
public class MainViewModelTest {

    @Mock
    DataModel model;
    @Mock
    SpotifyModel spotifyManager;
    @Mock
    DatabaseDataModel databaseDataModel;

    @InjectMocks
    MainViewModel viewModel;

    private String testName = "testName";
    private String testName2 = "testName2";
    private String testArtist = "testArtist";
    private String testArtist2 = "testArtist2";
    private String testCover = "http://cover.com";
    private String testLyrics = "Some happy lyrics";
    private String error = "error";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
    }

    @After
    public void tearDown(){
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }

    @Test
    public void trackInfoFlow() {
        TrackInfo rawTrackInfo = new TrackInfo();
        rawTrackInfo.setName(testName);
        rawTrackInfo.setArtist(testArtist);

        TrackInfo trackInfoFull = new TrackInfo();
        trackInfoFull.setName(testName);
        trackInfoFull.setAlbumCover(testCover);

        TrackInfo trackInfoWithLyrics = new TrackInfo();
        trackInfoWithLyrics.setName(testName);
        trackInfoWithLyrics.setArtist(testArtist);
        trackInfoWithLyrics.setLyrics(testLyrics);

//        TrackInfoRequest trackInfoRequest = new TrackInfoRequest();
//        trackInfoRequest.setName(testName);
//        trackInfoRequest.setAlbumCoverUrl(testCover);

        when(model.subscribeRawTrackInfo()).thenReturn(Observable.just(rawTrackInfo));
        when(model.getTrackInfo(testArtist, testName)).thenReturn(Single.just(trackInfoFull));
        when(model.getLyrics(rawTrackInfo)).thenReturn(Single.just(trackInfoWithLyrics));

        TestObserver<TrackInfo> testObserver = new TestObserver<>();
        viewModel.observeTrackInfo().subscribe(testObserver);

        testObserver.assertValueCount(2);

        testObserver.assertValueAt(0, trackInfo ->
                trackInfo.getName().equals(testName) && trackInfo.getAlbumCover() == null);

        testObserver.assertValueAt(1, trackInfo ->
                trackInfo.getName().equals(testName) &&
                        trackInfo.getAlbumCover().equals(testCover) &&
                        trackInfo.getLyrics().equals(testLyrics));
    }


    @Test
    public void trackInfoBlockEmpty() {
        TrackInfo rawTrackInfo = new TrackInfo();
        rawTrackInfo.setName("");
        rawTrackInfo.setArtist("");

        TrackInfo rawTrackInfo2 = new TrackInfo();
        rawTrackInfo2.setName(testName);
        rawTrackInfo2.setArtist(testArtist);

        TrackInfo trackInfoFull = new TrackInfo();
        trackInfoFull.setName(testName);
        trackInfoFull.setAlbumCover(testCover);

        TrackInfo trackInfoWithLyrics = new TrackInfo();
        trackInfoWithLyrics.setName(testName);
        trackInfoWithLyrics.setArtist(testArtist);
        trackInfoWithLyrics.setLyrics(testLyrics);

        when(model.subscribeRawTrackInfo()).thenReturn(Observable.just(rawTrackInfo, rawTrackInfo2));
        when(model.getTrackInfo(testArtist, testName)).thenReturn(Single.just(trackInfoFull));
        when(model.getLyrics(rawTrackInfo2)).thenReturn(Single.just(trackInfoWithLyrics));

        TestObserver<TrackInfo> testObserver = new TestObserver<>();
        viewModel.observeTrackInfo().subscribe(testObserver);

        testObserver.assertValueCount(2);

        testObserver.assertValueAt(0, trackInfo -> trackInfo.getName().equals(testName));
        testObserver.assertValueAt(1, trackInfo ->
                trackInfo.getName().equals(testName) && trackInfo.getAlbumCover().equals(testCover));
    }

    @Test
    public void trackInfoBlockError() {
        TrackInfo rawTrackInfo = new TrackInfo();
        rawTrackInfo.setName(testName);
        rawTrackInfo.setArtist(testArtist);

        TrackInfo rawTrackInfo2 = new TrackInfo();
        rawTrackInfo2.setName(testName2);
        rawTrackInfo2.setArtist(testArtist2);

        TrackInfo trackInfoFull = new TrackInfo();
        trackInfoFull.setName(testName);
        trackInfoFull.setAlbumCover(testCover);

        TrackInfo trackInfoFull2 = new TrackInfo();
        trackInfoFull2.setName(testName2);
        trackInfoFull2.setAlbumCover(testCover);

        TrackInfo trackInfoWithLyrics = new TrackInfo();
        trackInfoWithLyrics.setName(testName2);
        trackInfoWithLyrics.setArtist(testArtist2);
        trackInfoWithLyrics.setLyrics(testLyrics);

        when(model.subscribeRawTrackInfo()).thenReturn(Observable.just(rawTrackInfo, rawTrackInfo2));

        when(model.getTrackInfo(testArtist, testName)).thenReturn(Single.just(trackInfoFull));
        when(model.getLyrics(rawTrackInfo)).thenReturn(Single.error(new Throwable(error)));

        when(model.getTrackInfo(testArtist2, testName2)).thenReturn(Single.just(trackInfoFull2));
        when(model.getLyrics(rawTrackInfo2)).thenReturn(Single.just(trackInfoWithLyrics));

        TestObserver<TrackInfo> testObserver = new TestObserver<>();
        viewModel.observeTrackInfo().subscribe(testObserver);

        testObserver.assertValueCount(4);

        testObserver.assertValueAt(0, trackInfo -> trackInfo.getName().equals(testName));

        testObserver.assertValueAt(1, trackInfo ->
                        trackInfo.getName().equals(testName) &&
                        trackInfo.getAlbumCover().equals(testCover) &&
                        trackInfo.getLyrics() == null);

        testObserver.assertValueAt(2, trackInfo -> trackInfo.getName().equals(testName2));
        testObserver.assertValueAt(3, trackInfo ->
                trackInfo.getName().equals(testName2) &&
                        trackInfo.getAlbumCover().equals(testCover) &&
                        trackInfo.getLyrics().equals(testLyrics));
    }
//    @Test
//    public void first(){
//        TestObserver<Integer> testSubscriber = new TestObserver<>();
//        Observable.just(1).subscribeRawTrackInfo(testSubscriber);
//        testSubscriber.assertNoErrors();
//        testSubscriber.assertValue(1);
//    }

//    @Test
//    public void showLastTrack(){
////        when()
//    }

//    @Test
//    public void showTrackInfo(){
//        TrackInfo trackInfo = new TrackInfo();
//        trackInfo.setName("Test track");
//
//        when(spotifyManager.subscribe()).thenReturn(Observable.just(trackInfo));
//        TestObserver<TrackInfo> testObserver = new TestObserver<>();
//
//        viewModel.observeTrackInfo().subscribe(testObserver);
//        testObserver.assertValue(trackInfo);
//    }

}