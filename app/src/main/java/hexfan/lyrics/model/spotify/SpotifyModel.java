package hexfan.lyrics.model.spotify;

import android.content.Intent;

import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.PlayerNotificationCallback;

import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.ui.base.BaseActivity;
import hexfan.lyrics.ui.main.MainViewModel;
import io.reactivex.Observable;

/**
 * Created by Pawel on 22.10.2017.
 */

public interface SpotifyModel extends PlayerNotificationCallback, ConnectionStateCallback {

//    void sendTrack(TrackInfo trackInfo);

//    Observable<TrackInfo> subscribeRawTrackInfo();

    void onDestroy(BaseActivity baseActivity);

    void onActivityResult(BaseActivity baseActivity, int requestCode, int resultCode, Intent intent);
}
