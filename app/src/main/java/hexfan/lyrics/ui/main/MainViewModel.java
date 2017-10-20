package hexfan.lyrics.ui.main;



import hexfan.lyrics.model.DataModel;
import hexfan.lyrics.model.pojo.TrackInfo;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Pawel on 17.10.2017.
 */
public class MainViewModel {

    private DataModel model;

    private BehaviorSubject<TrackInfo> trackInfoBehaviorSubject;

    public MainViewModel(DataModel model) {
        this.model = model;
        trackInfoBehaviorSubject = BehaviorSubject.create();
    }

    public Observable<TrackInfo> trackInfo() {
        return trackInfoBehaviorSubject;
    }

//    @Override
//    public void subscribeToSpotify(Observable<TrackInfo> observable) {
//        addSubscriber(observable, new DisposableObserver<TrackInfo>() {
//            @Override
//            public void onNext(TrackInfo trackInfo) {
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
//            public void onNext(TrackInfo trackInfo) {
//                view.displayNewTrack(trackInfo);
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
}
