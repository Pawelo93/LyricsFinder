package hexfan.lyrics.di.main;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import dagger.Module;
import dagger.Provides;
import hexfan.lyrics.di.AppScope;
import hexfan.lyrics.model.ApiManager;
import hexfan.lyrics.model.DatabaseDataModel;
import hexfan.lyrics.model.db.AppDatabase;
import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.model.spotify.MockSpotifyManager;
import hexfan.lyrics.model.spotify.SpotifyManager;
import hexfan.lyrics.model.spotify.SpotifyModel;
import hexfan.lyrics.model.DataManager;
import hexfan.lyrics.model.DataModel;
import hexfan.lyrics.ui.browse.BrowseFragment;
import hexfan.lyrics.ui.browse.BrowseViewModel;
import hexfan.lyrics.ui.main.MainActivity;
import hexfan.lyrics.ui.main.MainViewModel;
import hexfan.lyrics.utils.Config;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Pawel on 23.10.2017.
 */

@Module
public class MainModule {
    private static final String TAG = "MainModule";

    private MainActivity mainActivity;

    public MainModule(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Provides
    MainViewModel provideMainViewModel(MainViewModel.Factory mainViewModelFactory){
        return ViewModelProviders.of(mainActivity, mainViewModelFactory).get(MainViewModel.class);
    }

    @Provides
    MainViewModel.Factory provideMainViewModelFactory(DataModel dataModel,
                                                      DatabaseDataModel databaseModel, SpotifyModel spotifyModel){
        return new MainViewModel.Factory(mainActivity.getApplication(), dataModel, databaseModel, spotifyModel);
    }

    @Provides
    BrowseFragment provideBrowseFragment(){
        return BrowseFragment.newInstance();
    }

    @Provides
    BrowseViewModel provideBrowseViewModel(BrowseViewModel.Factory browseViewModelFactory){
        return ViewModelProviders.of((mainActivity), browseViewModelFactory).get(BrowseViewModel.class);
    }

    @Provides
    BrowseViewModel.Factory provideBrowseViewModelFactory(Application application, DatabaseDataModel databaseDataModel){
        return new BrowseViewModel.Factory(application, databaseDataModel);
    }

    @Provides
    SpotifyModel provideSpotifyManager() {
        SpotifyModel spotifyModel;
        if(Config.START_SPOTIFY_SERVICE)
            spotifyModel = new SpotifyManager(mainActivity);
        else
            spotifyModel = new MockSpotifyManager(mainActivity);

//        Log.e("AppModule", "provideSpotifyManager: "+Config.START_SPOTIFY_SERVICE+" model "+spotifyModel);

        return spotifyModel;
    }
}
