package hexfan.lyrics.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.Spotify;

import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.ui.base.BaseActivity;
import hexfan.lyrics.ui.main.MainActivity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Pawel on 16.07.2017.
 */

public class SpotifyManager implements PlayerNotificationCallback, ConnectionStateCallback {
    private static final String TAG = "SpotifyManager";

    Player player;

    public static final String BROADCAST_ACTION = "action_track_info";

    private static final String CLIENT_ID = "7b4a1bb7b9794116bef5502c1a854617";
    private static final String REDIRECT_URI = "hexfan.lyrics://callback";

    public static final String TRACK_NAME = "song_name";
    public static final String ARTIST_NAME = "artist_name";
    public static final String ALBUM_NAME = "album_name";

    private static final int REQUEST_CODE = 1337;

    BehaviorSubject<TrackInfo> tracksSubject = BehaviorSubject.create();


    public SpotifyManager(BaseActivity activity){

        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming", "user-read-recently-played"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(activity, REQUEST_CODE, request);

        activity.registerReceiver(broadcastReceiver, new IntentFilter(BROADCAST_ACTION));
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            TrackInfo trackInfo = new TrackInfo();
            trackInfo.setName(intent.getStringExtra(TRACK_NAME));
            trackInfo.setArtist(intent.getStringExtra(ARTIST_NAME));
            Log.e(TAG, "onReceive: ");
            tracksSubject.onNext(trackInfo);
        }
    };


    public void onActivityResult(BaseActivity activity, int requestCode, int resultCode, Intent intent) {

//         Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Config playerConfig = new Config(activity, response.getAccessToken(), CLIENT_ID);

                Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {
                    @Override
                    public void onInitialized(Player newPlayer) {
                        player = newPlayer;
                        player.addConnectionStateCallback(SpotifyManager.this);
                        player.addPlayerNotificationCallback(SpotifyManager.this);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e(TAG, "onError: "+throwable.getMessage());
                    }
                });
            }
        }
    }

    public Observable<TrackInfo> getSpotify(){
        return tracksSubject;
    }


    public void onDestroy(BaseActivity baseActivity) {
        Spotify.destroyPlayer(this);
        player = null;
        if (broadcastReceiver != null) {
            baseActivity.unregisterReceiver(broadcastReceiver);
        }
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
