package hexfan.lyrics.ui.main;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;

import dagger.Module;
import dagger.Provides;
import hexfan.lyrics.model.DataModel;
import hexfan.lyrics.model.DatabaseDataModel;
import hexfan.lyrics.model.spotify.MockSpotifyManager;
import hexfan.lyrics.model.spotify.SpotifyManager;
import hexfan.lyrics.model.spotify.SpotifyModel;
import hexfan.lyrics.ui.browse.BrowseFragment;
import hexfan.lyrics.ui.browse.BrowseViewModel;
import hexfan.lyrics.utils.Config;

/**
 * Created by Pawel-PC on 26.11.2017.
 */

@Module
public class MainActivityModule {

    @Provides
    MainViewModel provideMainViewModel(MainActivity mainActivity, MainViewModel.Factory mainViewModelFactory){
        return ViewModelProviders.of(mainActivity, mainViewModelFactory).get(MainViewModel.class);
    }

    @Provides
    MainViewModel.Factory provideMainViewModelFactory(MainActivity mainActivity, DataModel dataModel,
                                                      DatabaseDataModel databaseModel, SpotifyModel spotifyModel){
        return new MainViewModel.Factory(mainActivity.getApplication(), dataModel, databaseModel, spotifyModel);
    }

    @Provides
    BrowseFragment provideBrowseFragment(){
        return BrowseFragment.newInstance();
    }

    @Provides
    BrowseViewModel provideBrowseViewModel(MainActivity mainActivity, BrowseViewModel.Factory browseViewModelFactory){
        return ViewModelProviders.of((mainActivity), browseViewModelFactory).get(BrowseViewModel.class);
    }

    @Provides
    BrowseViewModel.Factory provideBrowseViewModelFactory(Application application, DatabaseDataModel databaseDataModel){
        return new BrowseViewModel.Factory(application, databaseDataModel);
    }

    @Provides
    SpotifyModel provideSpotifyManager(MainActivity mainActivity) {
        SpotifyModel spotifyModel;
        if(Config.START_SPOTIFY_SERVICE)
            spotifyModel = new SpotifyManager(mainActivity);
        else
            spotifyModel = new MockSpotifyManager(mainActivity);

//        Log.e("AppModule", "provideSpotifyManager: "+Config.START_SPOTIFY_SERVICE+" model "+spotifyModel);

        return spotifyModel;
    }
}
