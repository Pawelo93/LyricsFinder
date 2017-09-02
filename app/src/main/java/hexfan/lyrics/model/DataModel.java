package hexfan.lyrics.model;


import java.util.List;

import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.model.pojo.TrackInfoRequest;
import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by Pawel on 20.06.2017.
 */

public interface DataModel {

    Observable<TrackInfo> getLyrics(TrackInfo trackInfo);

    Observable<TrackInfoRequest> getTrackInfo(String artist, String track);

    void saveTrackInfo(TrackInfo trackInfo);

    Flowable<List<TrackInfo>> getHistoryCache();

}
