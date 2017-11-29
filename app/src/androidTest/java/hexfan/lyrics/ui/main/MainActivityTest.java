package hexfan.lyrics.ui.main;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import hexfan.lyrics.base.AsyncTaskSchedulerRule;
import hexfan.lyrics.R;
import hexfan.lyrics.base.TestApplication;
import hexfan.lyrics.di.Injector;
import hexfan.lyrics.model.DataModel;
import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.ui.browse.BrowseFragment;
import hexfan.lyrics.ui.browse.BrowseFragmentTest;
import hexfan.lyrics.utils.Config;
import io.reactivex.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Pawel on 13.11.2017.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> rule
            = new ActivityTestRule<MainActivity>(MainActivity.class, false, false);

    @Rule
    public AsyncTaskSchedulerRule asyncTaskSchedulerRule = new AsyncTaskSchedulerRule();

    @Inject
    MainViewModel viewModel;
    @Inject
    DataModel model;
    @Inject
    BrowseFragment browseFragment;

    private String name = "Best Song ever";
    private String artist = "Best Artist";

    @Before
    public void setUp(){
        Config.test();
        MockitoAnnotations.initMocks(this);

//        TestApplication.INSTANCE.component.inject();

//        TestMainActivityModule module = new TestMainActivityModule(rule.getActivity());
//        TestMainActivityComponent testMainActivityComponent = ((TestApplication.TestComponent) TestApplication.INSTANCE.component)
//                .addComponent(module);
//
//        testMainActivityComponent.inject(this);
//
//        Injector.get().mainComponent = testMainActivityComponent;
    }

    @Test
    public void nowListen_properValues(){
        TrackInfo testTrackInfo = new TrackInfo();
        testTrackInfo.setName(name);
        testTrackInfo.setArtist(artist);
        when(viewModel.observeTrackInfo()).thenReturn(Observable.just(testTrackInfo));

        rule.launchActivity(null);

        onView(withId(R.id.tvNowListenTitle)).check(matches(withText(name)));
        onView(withId(R.id.tvNowListenArtist)).check(matches(withText(artist)));
    }

    public static void takeScreenshot(String name, Activity activity)
    {

        // In Testdroid Cloud, taken screenshots are always stored
        // under /test-screenshots/ folder and this ensures those screenshots
        // be shown under Test Results
        String path =
                Environment.getExternalStorageDirectory().getAbsolutePath() + "/test-screenshots/" + name + ".png";

        View scrView = activity.getWindow().getDecorView().getRootView();
        scrView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(scrView.getDrawingCache());
        scrView.setDrawingCacheEnabled(false);

        OutputStream out = null;
        File imageFile = new File(path);

        try {
            out = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
        } catch (FileNotFoundException e) {
            // exception
            System.out.println("EXCEPTION "+e.getMessage());

        } catch (IOException e) {
            // exception
            System.out.println("EXCEPTION "+e.getMessage());

        } finally {

            try {
                if (out != null) {
                    out.close();
                }

            } catch (Exception exc) {
                System.out.println("EXCEPTION "+exc.getMessage());
            }

        }
    }
}