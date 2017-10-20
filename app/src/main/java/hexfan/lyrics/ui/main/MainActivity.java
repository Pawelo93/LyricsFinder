package hexfan.lyrics.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hexfan.lyrics.utils.Config;
import hexfan.lyrics.utils.MockSpotifyManager;
import hexfan.lyrics.utils.SpotifyCheckService;
import hexfan.lyrics.R;
import hexfan.lyrics.model.DataModel;
import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.ui.base.BaseActivity;
import hexfan.lyrics.ui.base.BaseFragment;
import hexfan.lyrics.ui.browse.BrowseFragment;
import hexfan.lyrics.ui.components.NowListenView;
import hexfan.lyrics.ui.lyrics.LyricsFragment;
import hexfan.lyrics.utils.SpotifyManager;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends BaseActivity implements MainView {

    private static final String TAG = "MainActivity";

    @BindView(R.id.nowListen)
    NowListenView nowListen;

    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    DataModel dataModel;

    private MockSpotifyManager spotifyManager;

//    @Inject
//    BehaviorSubject<TrackInfo> trackInfoBehaviorSubject;
//    @Inject
//    PublishSubject<String> nowListenBus;


    private BrowseFragment browseFragment;
    private LyricsFragment lyricsFragment;

    public static MainActivity get(BaseFragment baseFragment){
        return ((MainActivity) baseFragment.getActivity());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getMyComponents().inject(this);
        spotifyManager = new MockSpotifyManager(this);

        browseFragment = BrowseFragment.newInstance();
        addFragment(browseFragment);

        Log.e(TAG, "onCreate: last " +sharedPreferences.getString("last_song", "empty"));

//        trackInfoBehaviorSubject.subscribe(new DisposableObserver<TrackInfo>() {
//            @Override
//            public void onNext(TrackInfo trackInfo) {
//                Log.e(TAG, "onNext: "+trackInfo.getName());
//                presenter.loadTrackInfo(trackInfo.getArtist(), trackInfo.getName());
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


//        nowListenBus.subscribe(new DisposableObserver<String>() {
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

//        nowListenBus.onNext(NowListenView.HIDE);

    }

    private void addFragment(BaseFragment baseFragment){

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, baseFragment)
                .commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
//        presenter.attachView(this);
//        presenter.subscribeToSpotify(spotifyManager.getSpotify());

        if(Config.START_SPOTIFY_SERVICE)
            startSpotifyService();
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
//        presenter.detachView();
        spotifyManager.onDestroy(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(spotifyManager != null)
            spotifyManager.onActivityResult(this, requestCode, resultCode, intent);
    }


}
