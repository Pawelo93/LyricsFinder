package hexfan.lyrics.ui.browse;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import hexfan.lyrics.R;
import hexfan.lyrics.base.AsyncTaskSchedulerRule;
import hexfan.lyrics.base.TestApplication;
import hexfan.lyrics.model.DataModel;
import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.ui.main.MainActivity;
import hexfan.lyrics.ui.main.MainActivityTest;
import hexfan.lyrics.utils.Config;
import io.reactivex.Flowable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.when;

/**
 * Created by Pawel on 19.11.2017.
 */
public class BrowseFragmentTest {
    @Rule
    public ActivityTestRule<MainActivity> rule
            = new ActivityTestRule<MainActivity>(MainActivity.class, false, false);

    @Rule
    public AsyncTaskSchedulerRule asyncTaskSchedulerRule = new AsyncTaskSchedulerRule();


    @Inject
    BrowseViewModel viewModel;

    @Inject
    DataModel model;



    private String name = "Nice Song";
    private String artist = "Fine Artist";

    @Before
    public void setUp(){
        Config.test();
        MockitoAnnotations.initMocks(this);



//        MainActivityTest.TestMainActivityModule module = new MainActivityTest.TestMainActivityModule(rule.getActivity());
//        MainActivityTest.TestMainActivityComponent testMainActivityComponent = ((TestApplication.TestComponent) TestApplication.INSTANCE.component)
//                .addComponent(module);
//
//        testMainActivityComponent.inject(this);
    }

    @Test
    public void history_properValues(){
        List<TrackInfo> list = new ArrayList<>();
        list.add(new TrackInfo());
        list.add(new TrackInfo());

        TrackInfo testTrackInfo = new TrackInfo();
        testTrackInfo.setName(name);
        testTrackInfo.setName(artist);
        list.add(testTrackInfo);

        when(viewModel.observeLastTrackInfos()).thenReturn(Flowable.just(list));

        rule.launchActivity(new Intent());

        onView(withId(R.id.tvTrackTitle)).check(matches(withText(name)));
        onView(withId(R.id.tvArtist)).check(matches(withText(artist)));
    }
}