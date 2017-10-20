package hexfan.lyrics.di;

import dagger.Component;
import hexfan.lyrics.utils.SpotifyCheckService;
import hexfan.lyrics.ui.browse.BrowseFragment;
import hexfan.lyrics.ui.lyrics.LyricsFragment;
import hexfan.lyrics.ui.main.MainActivity;

/**
 * Created by Pawel on 20.06.2017.
 */

@AppScope
@Component(modules = {AppModule.class, RxBusModule.class})
public interface MyComponents {

//    void inject(BaseActivity baseActivity);

    void inject(SpotifyCheckService spotifyCheckService);

    void inject(MainActivity mainActivity);

    void inject(BrowseFragment browseFragment);

    void inject(LyricsFragment lyricsFragment);
}
