package hexfan.lyrics.di;

import hexfan.lyrics.di.main.MainComponent;
import hexfan.lyrics.di.main.MainModule;
import hexfan.lyrics.model.spotify.SpotifyCheckService;
import hexfan.lyrics.ui.browse.BrowseFragment;
import hexfan.lyrics.ui.lyrics.LyricsFragment;
import hexfan.lyrics.ui.main.MainActivity;
import hexfan.lyrics.ui.main.MainApplication;

/**
 * Created by Pawel on 18.11.2017.
 */

public class Injector {

    public MainComponent mainComponent;
//    public BrowseFragmentComponent browseFragmentComponent;

    private static Injector INSTANCE;

    public static Injector get(){
        if(INSTANCE == null)
            INSTANCE = new Injector();
        return INSTANCE;
    }
    private Injector(){}

    public static void setupMain(MainActivity mainActivity) {
        Injector.get().mainComponent = MainApplication.INSTANCE.component.addComponent(new MainModule(mainActivity));
    }

    public static void inject(MainActivity mainActivity){
        if(Injector.get().mainComponent == null)
            setupMain(mainActivity);
        Injector.get().mainComponent.inject(mainActivity);
    }

    public static void inject(SpotifyCheckService spotifyCheckService) {
        MainApplication.INSTANCE.component.inject(spotifyCheckService);
    }

    public static void inject(BrowseFragment browseFragment) {
        Injector.get().mainComponent.inject(browseFragment);
    }

    public static void inject(LyricsFragment lyricsFragment) {
        Injector.get().mainComponent.inject(lyricsFragment);
    }


//    public static void inject(BrowseFragment browseFragment) {
//        if(Injector.get().browseFragmentComponent != null)
//            Injector.get().browseFragmentComponent.inject(browseFragment);
//        else
//            MainApplication.INSTANCE.component.addComponent(new BrowseFragmentModule(browseFragment)).inject(browseFragment);
//    }
}