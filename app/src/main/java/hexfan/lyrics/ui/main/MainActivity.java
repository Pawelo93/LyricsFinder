package hexfan.lyrics.ui.main;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.FrameLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hexfan.lyrics.MyService;
import hexfan.lyrics.R;
import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.ui.base.BaseActivity;
import hexfan.lyrics.ui.base.BaseFragment;
import hexfan.lyrics.ui.components.NowListenView;
import hexfan.lyrics.ui.lyrics.LyricsFragment;
import hexfan.lyrics.utils.SpotifyManager;

public class MainActivity extends BaseActivity implements MainContract.View, ServiceConnection {

    private static final String TAG = "MainActivity";

    @BindView(R.id.nowListen)
    NowListenView nowListen;

    @Inject
    SpotifyManager spotifyManager;

    public MainPresenter presenter;
    private MainFragment mainFragment;
    private LyricsFragment lyricsFragment;

    public static MainActivity get(BaseFragment baseFragment){
        return ((MainActivity) baseFragment.getActivity());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
//        MainApplication.get(this).getAppComponent().inject(this);
        spotifyManager = new SpotifyManager(this);

        System.out.println("here "+spotifyManager);




        presenter = new MainPresenter(dataModel);

        mainFragment = MainFragment.newInstance();
        addFragment(mainFragment);







//        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
//                AuthenticationResponse.Type.TOKEN,
//                REDIRECT_URI);
//        builder.setScopes(new String[]{"user-read-private", "streaming", "user-read-recently-played"});
//        AuthenticationRequest request = builder.build();
//
//        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);


//        getDataModel().getLyrics("All that remains", "Two weeks")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DisposableObserver<Lyrics>() {
//                    @Override
//                    public void onNext(Lyrics lyrics) {
//                        Log.e(TAG, "onNext: artist "+lyrics.getArtistName());
//                        Log.e(TAG, "onNext: song "+lyrics.getSongName());
//                        Log.e(TAG, "onNext: lyrics "+lyrics.getLyrics().substring(0, 100));
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e(TAG, "onError: "+e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
















//        getDataModel().getLyrics("Scorpions", "Robot man")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(new Function<String, String>() {
//                    @Override
//                    public String apply(String input) throws Exception {
//
//
//                    }
//                })
//                .subscribe(new DisposableObserver<String>() {
//                    @Override
//                    public void onNext(String s) {
//                        Log.e(TAG, "onNext: "+s);
//                        Log.e(TAG, "onNext: ");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e(TAG, "onError: ");
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

//        Call<ResponseBody> call = getDataModel().getLyricsFromGenius("Despised Icon", "MVP");
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    Log.e(TAG, "onResponse: "+response.body().string());
//                    Log.e(TAG, "onResponse: ");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });



    }

    private void addFragment(BaseFragment baseFragment){

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, baseFragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attach(this);
        presenter.subscribeToSpotify(spotifyManager.getSpotify());


        startService(new Intent(this, MyService.class));

    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.detach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        spotifyManager.onDestroy(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(spotifyManager != null)
            spotifyManager.onActivityResult(this, requestCode, resultCode, intent);
    }

    @Override
    public void displayNewTrack(TrackInfo trackInfo) {
        if(!isVisable)
            return;
        Log.e(TAG, "displayNewTrack: new track " + trackInfo.getName());
//        addFragment(LyricsFragment.newInstance(gson.toJson(trackInfo)));
        if(lyricsFragment == null) {
            lyricsFragment = LyricsFragment.newInstance(gson.toJson(trackInfo));
            addFragment(lyricsFragment);
        }else
            lyricsFragment.changeTrack(trackInfo);
    }

    @Override
    public void displayFulLTrack(TrackInfo trackInfo) {
        Log.e(TAG, "displayFulLTrack: ");
        if(lyricsFragment != null)
            lyricsFragment.showFull(trackInfo);
    }

    @Override
    public void displayHistory(List<TrackInfo> cacheList) {
        if(mainFragment != null)
            mainFragment.showHistory(cacheList);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Log.e(TAG, "onServiceConnected: "+componentName.flattenToShortString());
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        Log.e(TAG, "onServiceDisconnected: "+ componentName.flattenToShortString());
    }

    @Override
    public void onBindingDied(ComponentName name) {
        Log.e(TAG, "onBindingDied: "+name);
    }

//    @Override
//    public void displayFulLTrackWithLyrics(TrackInfo trackInfo) {
//        Log.e(TAG, "displayFulLTrackWithLyrics: ");
//        if(lyricsFragment != null)
//            lyricsFragment.changeTrack(trackInfo);
//    }


}
