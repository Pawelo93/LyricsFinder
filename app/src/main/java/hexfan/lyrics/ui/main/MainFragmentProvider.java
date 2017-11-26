package hexfan.lyrics.ui.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import hexfan.lyrics.ui.browse.BrowseFragment;
import hexfan.lyrics.ui.lyrics.LyricsFragment;

/**
 * Created by Pawel-PC on 26.11.2017.
 */

@Module
public abstract class MainFragmentProvider {

    @ContributesAndroidInjector
    abstract BrowseFragment provideBrowseFragmentFactory();

    @ContributesAndroidInjector
    abstract LyricsFragment provideLyricsFragmentFactory();
}
