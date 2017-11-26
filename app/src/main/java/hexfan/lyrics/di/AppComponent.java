package hexfan.lyrics.di;

import dagger.Component;
import hexfan.lyrics.di.main.MainComponent;
import hexfan.lyrics.di.main.MainModule;
import hexfan.lyrics.model.spotify.SpotifyCheckService;
import hexfan.lyrics.ui.main.MainApplication;

/**
 * Created by Pawel on 20.06.2017.
 */

@Component(modules = {AppModule.class, RxBusModule.class})
@AppScope
public interface AppComponent{

    void inject(MainApplication mainApplication);

    MainComponent addComponent(MainModule mainModule);

    void inject(SpotifyCheckService spotifyCheckService);
}

