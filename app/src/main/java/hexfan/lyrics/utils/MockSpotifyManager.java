package hexfan.lyrics.utils;

import android.content.Intent;
import android.support.v4.util.Pair;
import android.util.Log;

import com.spotify.sdk.android.player.Spotify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.ui.base.BaseActivity;
import hexfan.lyrics.ui.main.MainActivity;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Pawel on 17.10.2017.
 */

public class MockSpotifyManager {

    private BehaviorSubject<TrackInfo> tracksSubject = BehaviorSubject.create();
    private List<Pair<String, String>> songs = new ArrayList<>();
    private int count = 0;

    public MockSpotifyManager(MainActivity mainActivity){
        songs.add(Pair.create("Duality", "Slipknot"));
        songs.add(Pair.create("Metallica", "One"));
        songs.add(Pair.create("Megadeth", "My darkest hour"));
    }

    public void produceEvent(){
        TrackInfo trackInfo = new TrackInfo();
        trackInfo.setName(songs.get(count).first);
        trackInfo.setArtist(songs.get(count).second);
        System.out.println("Event produced! "+songs.get(count).first);
        count++;
        tracksSubject.onNext(trackInfo);
    }

    public Observable<TrackInfo> getSpotify(){
        return tracksSubject;
    }

    public void onDestroy(BaseActivity baseActivity) {

    }

    public void onActivityResult(MainActivity mainActivity, int requestCode, int resultCode, Intent intent) {

    }
}
