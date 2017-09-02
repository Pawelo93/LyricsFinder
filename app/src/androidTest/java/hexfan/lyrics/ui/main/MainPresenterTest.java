package hexfan.lyrics.ui.main;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import hexfan.lyrics.base.AbstractTest;
import hexfan.lyrics.model.DataModel;
import hexfan.lyrics.model.pojo.TrackInfoRequest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Pawel on 11.07.2017.
 */
@RunWith(AndroidJUnit4.class)
public class MainPresenterTest extends AbstractTest{

    //    @Mock
//    DataModel model;
//
//    View view = Mockito.mock(View.class);

    DataModel model;

    MainContract.View view;

    @Rule
    public final ActivityRule<MainActivity> main = new ActivityRule<>(MainActivity.class);
    private MainPresenter presenter;


//    private MainPresenter presenter;

    @Before
    public void setUp(){
        model = main.get().getDataModel();

        presenter = new MainPresenter(model);

        view = mock(MainContract.View.class);
//        MockitoAnnotations.initMocks(this);
//        presenter = new MainPresenter(model, view);
    }

//    @Test
//    public void shouldBeAbleToLaunchMainScreen(){
//        onView(withText("Hello World!")).check(ViewAssertions.matches(isDisplayed()));
//    }

//    @Test
//    public void useAppContext() throws Exception {
//        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getTargetContext();
//
//        assertEquals("hexfan.lyrics", appContext.getPackageName());
//    }


    @Test
    public void testAttach() {
        assertNull(presenter.view);

        presenter.attach(view);
        assertNotNull(presenter.view);
    }

    @Test
    public void testDetach() {
        presenter.attach(view);
        assertNotNull(presenter.view);

        presenter.detach();
        assertNull(presenter.view);
    }

    @Test
    public void loadAlbumCover(){


//        presenter.getTrackInfo("Scorpions", "Robot Man");

        TrackInfoRequest trackInfoRequest = new TrackInfoRequest();

//        when(model.getTrackInfo("Scorpions", "Robot Man")).thenReturn(Observable.just(trackInfoRequest));
//
//        waitFor(1000);
        verify(view).displayNewTrack(trackInfoRequest);

//        main.get().presenter.getTrackInfo("Scorpions", "Robot Man");


//        Mockito.verify(main.get()).displayNewTrack(new TrackInfoRequest());
    }
}