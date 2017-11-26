package hexfan.lyrics.di.main;

import dagger.Subcomponent;
import hexfan.lyrics.model.spotify.SpotifyCheckService;
import hexfan.lyrics.ui.browse.BrowseFragment;
import hexfan.lyrics.ui.lyrics.LyricsFragment;
import hexfan.lyrics.ui.main.MainActivity;

/**
 * Created by Pawel on 18.11.2017.
 */

@Subcomponent(modules = {MainModule.class})
public interface MainComponent {

    void inject(MainActivity mainActivity);

    void inject(BrowseFragment browseFragment);

    void inject(LyricsFragment lyricsFragment);
}
