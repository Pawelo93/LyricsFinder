package hexfan.lyrics.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import hexfan.lyrics.model.spotify.SpotifyCheckService;
import hexfan.lyrics.ui.main.MainFragmentProvider;
import hexfan.lyrics.ui.main.MainActivity;
import hexfan.lyrics.ui.main.MainActivityModule;

/**
 * Created by Pawel-PC on 26.11.2017.
 */

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {MainActivityModule.class, MainFragmentProvider.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract SpotifyCheckService bindSpotifyCheckService();
}
