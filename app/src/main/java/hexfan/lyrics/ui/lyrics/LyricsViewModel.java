package hexfan.lyrics.ui.lyrics;

import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.model.pojo.TrackInfoRequest;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Pawel on 17.10.2017.
 */

public class LyricsViewModel {



//    @Override
//    public void loadTrackInfo(String artist, String track) {
//        addSubscriber(model.getTrackInfo(artist, track)
//                .map(new Function<TrackInfoRequest, TrackInfo>() {
//                    @Override
//                    public TrackInfo apply(TrackInfoRequest trackInfoRequest) throws Exception {
//                        return TrackInfo.fromTrackInfoRequest(trackInfoRequest);
//                    }
//                }), new DisposableObserver<TrackInfo>() {
//            @Override
//            public void onNext(TrackInfo observeTrackInfo) {
////                        getLyrics(observeTrackInfo);
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
//    public void loadLyrics(TrackInfo observeTrackInfo) {
//        addSubscriber(model.getLyrics(observeTrackInfo).map(new Function<TrackInfo, TrackInfo>() {
//            @Override
//            public TrackInfo apply(TrackInfo observeTrackInfo) throws Exception {
//                if(observeTrackInfo != null)
//                    model.cacheTrackInfo(observeTrackInfo);
//
//                return observeTrackInfo;
//            }
//        }), new DisposableObserver<TrackInfo>() {
//            @Override
//            public void onNext(TrackInfo observeTrackInfo) {
////                view.displayFulLTrack(observeTrackInfo);
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
