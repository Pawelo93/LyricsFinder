package hexfan.lyrics.model.spotify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.Spotify;

import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.ui.base.BaseActivity;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Pawel on 16.07.2017.
 */

public class SpotifyManager implements SpotifyModel {
    private static final String TAG = "SpotifyManager";


    public static final String BROADCAST_ACTION = "action_track_info";

    private static final String CLIENT_ID = "c61e21fe712e450290070bdc7cd9ca7a";
    private static final String REDIRECT_URI = "hexfan.lyrics://callback";

    public static final String TRACK_NAME = "song_name";
    public static final String ARTIST_NAME = "artist_name";
    public static final String ALBUM_NAME = "album_name";

    private static final int REQUEST_CODE = 1337;
//    private BehaviorSubject<TrackInfo> tracksSubject = BehaviorSubject.create();
    private Player player;

    public SpotifyManager(BaseActivity activity){
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming", "user-read-recently-played"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(activity, REQUEST_CODE, request);

//        if(broadcastReceiver == null)
//            activity.registerReceiver(broadcastReceiver, new IntentFilter(BROADCAST_ACTION));
    }

//    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            TrackInfo trackInfo = new TrackInfo();
//            trackInfo.setName(intent.getStringExtra(TRACK_NAME));
//            trackInfo.setArtist(intent.getStringExtra(ARTIST_NAME));
//            Log.e(TAG, "onReceive: name "+trackInfo.getName()+" artist "+trackInfo.getArtist());
////            sendTrack(trackInfo);
//        }
//    };

//    @Override
//    public Observable<TrackInfo> subscribeRawTrackInfo() {
//        return tracksSubject;
//    }

    @Override
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

//    @Override
//    public void sendTrack(TrackInfo trackInfo) {
//        tracksSubject.onNext(trackInfo);
//    }

    @Override
    public void onDestroy() {
        Spotify.destroyPlayer(this);
        player = null;
//        if (broadcastReceiver != null) {
//            try {
//                baseActivity.unregisterReceiver(broadcastReceiver);
//            } catch (IllegalArgumentException e) {
//                Log.e(TAG, "onDestroy: not registered");
//            }
//        }
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
