package hexfan.lyrics.model.spotify;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.util.Pair;
import android.util.Log;

import com.spotify.sdk.android.player.PlayerState;

import java.util.ArrayList;
import java.util.List;

import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.ui.base.BaseActivity;
import hexfan.lyrics.ui.main.MainActivity;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Pawel on 17.10.2017.
 */

public class MockSpotifyManager implements SpotifyModel {
    private static final String TAG = "MockSpotifyManager";

    private BehaviorSubject<TrackInfo> tracksSubject = BehaviorSubject.create();
    private List<Pair<String, String>> songs = new ArrayList<>();
    private int count = 0;

    public MockSpotifyManager(MainActivity mainActivity){
//        songs.add(Pair.create("Duality", "Slipknot"));
//        songs.add(Pair.create("Metallica", "One"));
//        songs.add(Pair.create("Megadeth", "My darkest hour"));


        new Handler().postDelayed(() -> {
            TrackInfo trackInfo = new TrackInfo();
            trackInfo.setArtist("Metallica");
            trackInfo.setName("Enter sandman");
//            sendTrack(trackInfo);
        }, 3000);
    }

//    @Override
//    public void sendTrack(TrackInfo trackInfo) {
//        tracksSubject.onNext(trackInfo);
//    }
//
//    @Override
//    public Observable<TrackInfo> subscribeRawTrackInfo() {
//        return tracksSubject;
//    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onActivityResult(BaseActivity baseActivity, int requestCode, int resultCode, Intent intent) {

    }

    @Override
    public void onLoggedIn() {

    }

    @Override
    public void onLoggedOut() {

    }

    @Override
    public void onLoginFailed(Throwable throwable) {

    }

    @Override
    public void onTemporaryError() {

    }

    @Override
    public void onConnectionMessage(String s) {

    }

    @Override
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {

    }

    @Override
    public void onPlaybackError(ErrorType errorType, String s) {

    }
}
