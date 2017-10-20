package hexfan.lyrics.ui.main;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import hexfan.lyrics.model.DataModel;
import hexfan.lyrics.model.pojo.TrackInfo;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

/**
 * Created by Pawel on 18.10.2017.
 */
public class MainViewModelTest {

    @Mock
    DataModel model;

    MainViewModel viewModel;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        viewModel = new MainViewModel(model);
    }

    @Test
    public void first(){
        TestObserver<Integer> testSubscriber = new TestObserver<>();
        Observable.just(1).subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertValue(1);
    }

    @Test
    public void showTrackInfo(){
//        when(model.test()).thenReturn(Observable.just(true));
        TrackInfo trackInfo = new TrackInfo();
        trackInfo.setName("Test track");

        TestObserver<TrackInfo> testObserver = new TestObserver<>();

        viewModel.trackInfo().subscribe(testObserver);
        testObserver.assertValue(trackInfo);
    }
}