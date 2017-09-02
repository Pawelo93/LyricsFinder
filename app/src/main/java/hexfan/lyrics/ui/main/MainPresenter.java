package hexfan.lyrics.ui.main;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hexfan.lyrics.model.DataModel;
import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.model.pojo.TrackInfoRequest;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by Pawel on 20.06.2017.
 */

public class MainPresenter extends MainContract.Presenter {
    private static final String TAG = "MainPresenter";

    public DataModel model;
    public MainContract.View view;

    public MainPresenter(DataModel model) {
        this.model = model;
    }


    public void subscribeToSpotify(Observable<TrackInfo> observable) {

        addSubscriber(observable, new DisposableObserver<TrackInfo>() {
            @Override
            public void onNext(TrackInfo trackInfo) {
                view.displayNewTrack(trackInfo);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void getTrackInfo(String artist, String track){
        Log.e(TAG, "getTrackInfo: ");
        addSubscriber(model.getTrackInfo(artist, track)
                .map(new Function<TrackInfoRequest, TrackInfo>() {
                    @Override
                    public TrackInfo apply(TrackInfoRequest trackInfoRequest) throws Exception {
                        return TrackInfo.fromTrackInfoRequest(trackInfoRequest);
                    }
                }), new DisposableObserver<TrackInfo>() {
                    @Override
                    public void onNext(TrackInfo trackInfo) {
                        Log.e(TAG, "onNext: trackinfo");
                        view.displayFulLTrack(trackInfo);
                        getLyrics(trackInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getLyrics(TrackInfo trackInfo){
        Log.e(TAG, "getLyrics: ");
        addSubscriber(model.getLyrics(trackInfo).map(new Function<TrackInfo, TrackInfo>() {
            @Override
            public TrackInfo apply(TrackInfo trackInfo) throws Exception {
                if(trackInfo != null)
                    model.saveTrackInfo(trackInfo);

                return trackInfo;
            }
        }), new DisposableObserver<TrackInfo>() {
            @Override
            public void onNext(TrackInfo trackInfo) {
                Log.e(TAG, "onNext: lyrics");
                view.displayFulLTrack(trackInfo);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void loadHistoryCache() {
        addSubscriber(model.getHistoryCache().map(new Function<List<TrackInfo>, List<TrackInfo>>() {
            @Override
            public List<TrackInfo> apply(List<TrackInfo> trackInfos) throws Exception {
                List<TrackInfo> newList = new ArrayList<>();
                for (TrackInfo trackInfo : trackInfos) {
                    newList.add(0, trackInfo);
                }
                return newList;
            }
        }), new ResourceSubscriber<List<TrackInfo>>() {
            @Override
            public void onNext(List<TrackInfo> listOfTracks) {
                view.displayHistory(listOfTracks);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: "+e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    protected void attach(MainContract.View view) {
        this.view = view;
    }

    @Override
    protected void detach() {
        this.view = null;
        dispose();
    }


}
