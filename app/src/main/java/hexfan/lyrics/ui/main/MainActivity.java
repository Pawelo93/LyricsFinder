package hexfan.lyrics.ui.main;

import android.content.Intent;
import android.os.Bundle;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hexfan.lyrics.R;
import hexfan.lyrics.di.Injector;
import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.model.spotify.SpotifyCheckService;
import hexfan.lyrics.ui.base.BaseActivity;
import hexfan.lyrics.ui.base.BaseFragment;
import hexfan.lyrics.ui.browse.BrowseFragment;
import hexfan.lyrics.ui.components.NowListenView;
import hexfan.lyrics.ui.lyrics.LyricsFragment;
import hexfan.lyrics.utils.Config;

public class MainActivity extends BaseActivity implements MainView{

    private static final String TAG = "MainActivity";

    @BindView(R.id.nowListen)
    NowListenView nowListen;

//    @Inject
//    SharedPreferences sharedPreferences;
//    @Inject
//    DataModel dataModel;

    @Inject
    MainViewModel viewModel;
    @Inject
    BrowseFragment browseFragment;
    @Inject
    Picasso picasso;

//    @Inject
//    BehaviorSubject<TrackInfo> trackInfoBus;

//    public BrowseFragment browseFragment;
    private LyricsFragment lyricsFragment;

    public static MainActivity get(BaseFragment baseFragment){
        return ((MainActivity) baseFragment.getActivity());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Injector.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        System.out.println("ON CREATE "+viewModel);


        addFragment(browseFragment);

//        trackInfoBus.subscribeRawTrackInfo(trackInfo -> {
//            Log.e(TAG, "onCreate: "+trackInfo.getName());
//        });

//        trackInfoBus.subscribeRawTrackInfo(new DisposableObserver<TrackInfo>() {
//            @Override
//            public void onNext(TrackInfo observeTrackInfo) {
//                Log.e(TAG, "onNext: "+observeTrackInfo.getName());
//                presenter.loadTrackInfo(observeTrackInfo.getArtist(), observeTrackInfo.getName());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(TAG, "onError: "+e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
//
//
//        nowListenBus.subscribeRawTrackInfo(new DisposableObserver<String>() {
//            @Override
//            public void onNext(String event) {
//                if (event.equals(NowListenView.HIDE))
//                    nowListen.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
//
//        nowListenBus.onNext(NowListenView.HIDE);

    }

    private void addFragment(BaseFragment baseFragment){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, baseFragment)
                .commit();
    }

    private void addFragmentBackstack(BaseFragment baseFragment){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, baseFragment)
                .addToBackStack("baseFragment")
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(Config.START_SPOTIFY_SERVICE)
            startSpotifyService();
    }

    @Override
    public void bind(){

        System.out.println("ACTIVITY View model activity "+viewModel + "bro fragment "+browseFragment);

        addSubscribe(viewModel.observeTrackInfo().subscribe(trackInfo -> {
//            System.out.println("Geting "+trackInfo);
            System.out.println("NOW thread "+Thread.currentThread().getName());
            System.out.println("IN SUBSCRIBE NOW "+(trackInfo.getLyrics() != null));
            nowListen.setup(picasso, trackInfo);

            if (trackInfo.getLyrics() != null && lyricsFragment == null) {
                lyricsFragment = LyricsFragment.newInstance(trackInfo);
                addFragmentBackstack(lyricsFragment);
            }
        }, throwable -> {
            System.out.println("ERROR "+throwable.getMessage());
        }));




    }

    @Override
    public void showTrackInfo(TrackInfo trackInfo){

    }

    private void startSpotifyService() {
        if(!SpotifyCheckService.isServiceRunning)
            startService(new Intent(this, SpotifyCheckService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        viewModel.onActivityResult(this, requestCode, resultCode, intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
