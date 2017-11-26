package hexfan.lyrics.ui.main;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;
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

public class MainActivity extends BaseActivity implements MainView, HasFragmentInjector{

    private static final String TAG = "MainActivity";

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @BindView(R.id.nowListen)
    NowListenView nowListen;

    @Inject
    MainViewModel viewModel;
    @Inject
    BrowseFragment browseFragment;
    @Inject
    Picasso picasso;

    private LyricsFragment lyricsFragment;

    public static MainActivity get(BaseFragment baseFragment){
        return ((MainActivity) baseFragment.getActivity());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        System.out.println("ONCREATE "+picasso+"   view "+viewModel);

        addFragment(browseFragment, R.id.fragmentContainer);

        nowListen.setup(this, picasso);



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
//            System.out.println("NOW thread "+Thread.currentThread().getName());
//            System.out.println("IN SUBSCRIBE NOW "+(trackInfo.getLyrics() != null));
            nowListen.update(trackInfo);
            if(!isLyricsFragmentVisible())
                showLyricsFragment(trackInfo);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        viewModel.onActivityResult(this, requestCode, resultCode, intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        nowListen.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLyricsFragment(TrackInfo trackInfo) {
//        Log.e(TAG, "showLyricsFragment: "+trackInfo);
        if(trackInfo.getLyrics() == null)
            return;

        if (lyricsFragment == null) {
            lyricsFragment = LyricsFragment.newInstance(trackInfo);
            addFragment(lyricsFragment, R.id.fragmentContainer, "lyricsFragment");
        }
        else {
            if(!lyricsFragment.isVisible()) {
                addFragment(lyricsFragment, R.id.fragmentContainer, "lyricsFragment");
                lyricsFragment.showLyrics(trackInfo);
            }else
                lyricsFragment.showLyrics(trackInfo);
        }

        if (nowListen.getTrackInfo().getArtist().equals(trackInfo.getArtist()) && nowListen.getTrackInfo().getName().equals(trackInfo.getName()))
            nowListen.setVisibility(View.GONE);
        else
            nowListen.setVisibility(View.VISIBLE);
    }

    private boolean isLyricsFragmentVisible(){
        return lyricsFragment != null && lyricsFragment.isVisible();
    }

    @Override
    public AndroidInjector<Fragment> fragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
