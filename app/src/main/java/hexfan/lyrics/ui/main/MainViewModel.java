package hexfan.lyrics.ui.main;


import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;


import hexfan.lyrics.model.DataModel;
import hexfan.lyrics.model.DatabaseDataModel;
import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.ui.base.BaseActivity;
import hexfan.lyrics.model.spotify.SpotifyModel;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Pawel on 17.10.2017.
 */
public class MainViewModel extends ViewModel {

    private DataModel model;
    private DatabaseDataModel databaseModel;
    private SpotifyModel spotifyModel;

    public MainViewModel(DataModel model, DatabaseDataModel databaseModel, SpotifyModel spotifyModel) {
        this.model = model;
        this.databaseModel = databaseModel;
        this.spotifyModel = spotifyModel;
    }

    public Observable<TrackInfo> observeTrackInfo() {
        return model.subscribeRawTrackInfo()
                .skipWhile(trackInfo -> trackInfo.getName().equals(""))
                .flatMap(trackInfo -> Single.zip(model.getTrackInfo(trackInfo.getArtist(), trackInfo.getName()),
                        model.getLyrics(trackInfo).onErrorReturn(throwable -> trackInfo), (trackWithData, trackWithLyrics) -> {
                            trackWithData.setLyrics(trackWithLyrics.getLyrics());

                            if(trackWithData.getLyrics() != null && !trackWithData.getLyrics().equals("")) {
                                databaseModel.cacheTrackInfo(trackWithData);
                            }
                            return trackWithData;
                        })
                        .onErrorReturn(throwable -> trackInfo)
                        .toObservable()
                        .startWith(trackInfo))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (spotifyModel != null)
            spotifyModel.onDestroy();
    }

    public void onDestroy(BaseActivity baseActivity) {

    }

    public void onActivityResult(BaseActivity baseActivity, int requestCode, int resultCode, Intent intent) {
        if (spotifyModel != null)
            spotifyModel.onActivityResult(baseActivity, requestCode, resultCode, intent);
    }

//    public Flowable<List<TrackInfo>> observeHistory() {
//        return model.getAllTrackInfoFromCache();
//    }


//    @Override
//    public void subscribeToSpotify(Observable<TrackInfo> observable) {
//        addSubscriber(observable, new DisposableObserver<TrackInfo>() {
//            @Override
//            public void onNext(TrackInfo observeTrackInfo) {
//
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
//    }
//
//    @Override
//    public void loadTrackInfo(String artist, final String track) {
//        addSubscriber(model.getTrackInfo(artist, track).map(new Function<TrackInfoRequest, TrackInfo>() {
//            @Override
//            public TrackInfo apply(TrackInfoRequest trackInfoRequest) throws Exception {
//                return TrackInfo.fromTrackInfoRequest(trackInfoRequest);
//            }
//        }), new DisposableObserver<TrackInfo>() {
//            @Override
//            public void onNext(TrackInfo observeTrackInfo) {
//                view.displayNewTrack(observeTrackInfo);
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
//    }

    /**
     * Helping create view model with custom constructor
     */
    public static class Factory extends ViewModelProviders.DefaultFactory {

        DataModel dataModel;
        DatabaseDataModel databaseModel;
        SpotifyModel spotifyModel;

        /**
         * Creates a {@code DefaultFactory}
         *
         * @param application an application to pass in {@link MainViewModel}
         */
        public Factory(@NonNull Application application, DataModel dataModel, DatabaseDataModel databaseModel,
                       SpotifyModel spotifyModel) {
            super(application);
            this.dataModel = dataModel;
            this.databaseModel = databaseModel;
            this.spotifyModel = spotifyModel;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new MainViewModel(dataModel, databaseModel, spotifyModel);
        }
    }
}
