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

    /**
     * Load track info from artist and track
     * @param artist
     * @param track
     * @return
     */
    Observable<TrackInfoRequest> getTrackInfo(String artist, String track);

    /**
     * Filling trackInfo with lyrics
     * @param trackInfo
     * @return
     */
    Observable<TrackInfo> getLyrics(TrackInfo trackInfo);

    /**
     * Save to database track info with lyrics
     * @param trackInfo
     */
    void cacheTrackInfo(TrackInfo trackInfo);

    /**
     * Connect to database for stream of track info
     * @return
     */
    Flowable<List<TrackInfo>> getAllTrackInfoFromCache();

    Observable<Boolean> test();
}
