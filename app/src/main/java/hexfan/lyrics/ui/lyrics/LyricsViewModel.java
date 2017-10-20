package hexfan.lyrics.ui.lyrics;

import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.model.pojo.TrackInfoRequest;
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
//            public void onNext(TrackInfo trackInfo) {
////                        getLyrics(trackInfo);
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
//    public void loadLyrics(TrackInfo trackInfo) {
//        addSubscriber(model.getLyrics(trackInfo).map(new Function<TrackInfo, TrackInfo>() {
//            @Override
//            public TrackInfo apply(TrackInfo trackInfo) throws Exception {
//                if(trackInfo != null)
//                    model.cacheTrackInfo(trackInfo);
//
//                return trackInfo;
//            }
//        }), new DisposableObserver<TrackInfo>() {
//            @Override
//            public void onNext(TrackInfo trackInfo) {
////                view.displayFulLTrack(trackInfo);
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
