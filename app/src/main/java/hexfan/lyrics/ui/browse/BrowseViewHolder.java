package hexfan.lyrics.ui.browse;

import java.util.ArrayList;
import java.util.List;

import hexfan.lyrics.model.pojo.TrackInfo;
import io.reactivex.functions.Function;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by Pawel on 17.10.2017.
 */

public class BrowseViewHolder {


//    addSubscriber(model.getAllTrackInfoFromCache().map(new Function<List<TrackInfo>, List<TrackInfo>>() {
//        @Override
//        public List<TrackInfo> apply(List<TrackInfo> trackInfos) throws Exception {
//            // reversing order
//            List<TrackInfo> newList = new ArrayList<>();
//            for (TrackInfo trackInfo : trackInfos) {
//                newList.add(0, trackInfo);
//            }
//            return newList;
//        }
//    }), new ResourceSubscriber<List<TrackInfo>>() {
//        @Override
//        public void onNext(List<TrackInfo> listOfTracks) {
////                view.displayHistory(listOfTracks);
//        }
//
//        @Override
//        public void onError(Throwable e) {
////                Log.e(TAG, "onError: "+e.getMessage());
//        }
//
//        @Override
//        public void onComplete() {
//
//        }
//    });
}
