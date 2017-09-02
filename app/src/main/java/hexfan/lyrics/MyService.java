package hexfan.lyrics;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.logging.Handler;

/**
 * Created by Pawel on 29.08.2017.
 */

public class MyService extends Service {
    private static final String TAG = "MyService";

    private Looper mServiceLooper;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
//    private ServiceHandler mServiceHandler;

//    @Override
//    public void onCreate() {
//        // To avoid cpu-blocking, we create a background handler to run our service
//        HandlerThread thread = new HandlerThread("TutorialService", Process.THREAD_PRIORITY_BACKGROUND);
//        // start the new handler thread
//        thread.start();
//
//        mServiceLooper = thread.getLooper();
//        // start the service using the background handler
//        mServiceHandler = new ServiceHandler(mServiceLooper);
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Toast.makeText(this, "onStartCommand", Toast.LENGTH_SHORT).show();
//
//        // call a new service handler. The service ID can be used to identify the service
//        Message message = mServiceHandler.obtainMessage();
//        message.arg1 = startId;
//        mServiceHandler.sendMessage(message);
//
//        return START_STICKY;
//    }
//
//    protected void showToast(final String msg){
//        //gets the main thread
//        Handler handler = new Handler(Looper.getMainLooper());
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                // run this code in the main thread
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    // Object responsible for
//    private final class ServiceHandler extends Handler {
//
//        public ServiceHandler(Looper looper) {
//            super(looper);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            // Well calling mServiceHandler.sendMessage(message); from onStartCommand,
//            // this method will be called.
//
//            // Add your cpu-blocking activity here
//        }
//    }

}
