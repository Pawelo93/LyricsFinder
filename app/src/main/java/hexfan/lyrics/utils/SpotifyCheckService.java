package hexfan.lyrics.utils;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;

import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.ui.main.MainApplication;
import io.reactivex.subjects.BehaviorSubject;


/**
 * Created by Pawel on 29.08.2017.
 */

public class SpotifyCheckService extends Service {
    private static final String TAG = "SpotifyCheckService";
    public static boolean isServiceRunning;
    private Looper mServiceLooper;

    private ServiceHandler mServiceHandler;

    @Inject
    BehaviorSubject<TrackInfo> trackInfoBehaviorSubject;

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate: ");
        // To avoid cpu-blocking, we create a background handler to run our service
        HandlerThread thread = new HandlerThread("TutorialService", Thread.NORM_PRIORITY);
        // start the new handler thread
        thread.start();

        ((MainApplication) getApplication()).getMyComponents().inject(this);

//        mServiceLooper = thread.getLooper();
        // start the service using the background handler
//        mServiceHandler = new ServiceHandler(mServiceLooper);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.spotify.music.metadatachanged");
        intentFilter.addAction("com.spotify.music.playbackstatechanged");
        registerReceiver(receiver, intentFilter);

        isServiceRunning = true;
    }

    static final class BroadcastTypes {
        static final String SPOTIFY_PACKAGE = "com.spotify.music";
        static final String PLAYBACK_STATE_CHANGED = SPOTIFY_PACKAGE + ".playbackstatechanged";
        static final String QUEUE_CHANGED = SPOTIFY_PACKAGE + ".queuechanged";
        static final String METADATA_CHANGED = SPOTIFY_PACKAGE + ".metadatachanged";
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if (action.equals(BroadcastTypes.METADATA_CHANGED)) {
                String trackId = intent.getStringExtra("id");
                String artistName = intent.getStringExtra("artist");
                String albumName = intent.getStringExtra("album");
                String trackName = intent.getStringExtra("track");
                int trackLengthInSec = intent.getIntExtra("length", 0);

//                Intent trackIntent = new Intent();
//                trackIntent.putExtra(SpotifyManager.TRACK_NAME, trackName);
//                trackIntent.putExtra(SpotifyManager.ARTIST_NAME, artistName);
//                trackIntent.putExtra(SpotifyManager.ALBUM_NAME, albumName);
//                trackIntent.setAction(SpotifyManager.BROADCAST_ACTION);
//                context.sendBroadcast(trackIntent);

                TrackInfo trackInfo = new TrackInfo();
                trackInfo.setArtist(artistName);
                trackInfo.setName(trackName);
                trackInfo.setAlbum(albumName);

                trackInfoBehaviorSubject.onNext(trackInfo);

                Log.e("MyBroadcastReceiver", "metadata id "+trackId+" artist " + artistName + " track "+trackName);


                if(trackName != null) {
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("last_song", trackName).apply();
                    Log.e(TAG, "onReceive: saved " + trackName);
                }

                // Do something with extracted information...
            } else if (action.equals(BroadcastTypes.PLAYBACK_STATE_CHANGED)) {
                boolean playing = intent.getBooleanExtra("playing", false);
                int positionInMs = intent.getIntExtra("playbackPosition", 0);
                Log.e("MyBroadcastReceiver", "onReceive: state changed "+playing + " pos in ms "+positionInMs);
                // Do something with extracted information
            } else if (action.equals(BroadcastTypes.QUEUE_CHANGED)) {
                // Sent only as a notification, your app may want to respond accordingly.
            }

        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "onStartCommand", Toast.LENGTH_SHORT).show();

        // call a new service handler. The service ID can be used to identify the service

//        Message message = mServiceHandler.obtainMessage();
//        message.arg1 = startId;
//        mServiceHandler.sendMessage(message);

        return START_STICKY;
    }

    protected void showToast(final String msg){
        //gets the main thread
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                // run this code in the main thread
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind: ");
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        isServiceRunning = false;
    }

    // Object responsible for
    private final class ServiceHandler extends Handler {

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            // Well calling mServiceHandler.sendMessage(message); from onStartCommand,
            // this method will be called.

            // Add your cpu-blocking activity here


        }
    }

}
