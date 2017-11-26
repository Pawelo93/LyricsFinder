package hexfan.lyrics.di;

/**
 * Created by Pawel on 18.11.2017.
 */

public class Injector {

//    public MainComponent mainComponent;
//    public BrowseFragmentComponent browseFragmentComponent;

    private static Injector INSTANCE;

    public static Injector get(){
        if(INSTANCE == null)
            INSTANCE = new Injector();
        return INSTANCE;
    }
    private Injector(){}

//    public static void setupMain(MainActivity mainActivity) {
//        Injector.get().mainComponent = MainApplication.INSTANCE.component.addComponent(new MainModule(mainActivity));
//    }
//
//    public static void inject(MainActivity mainActivity){
//        System.out.println("BEFORE injecto main "+Injector.get().mainComponent);
//
//        if(Injector.get().mainComponent == null)
//            setupMain(mainActivity);
//        Injector.get().mainComponent.inject(mainActivity);
//    }
//
//    public static void inject(SpotifyCheckService spotifyCheckService) {
//        MainApplication.INSTANCE.component.inject(spotifyCheckService);
//    }
//
//    public static void inject(BrowseFragment browseFragment) {
//        System.out.println("BEFORE injecto browse");
//        Injector.get().mainComponent.inject(browseFragment);
//    }
//
//    public static void inject(LyricsFragment lyricsFragment) {
//        Injector.get().mainComponent.inject(lyricsFragment);
//    }


}