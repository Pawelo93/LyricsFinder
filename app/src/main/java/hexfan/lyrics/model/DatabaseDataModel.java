package hexfan.lyrics.model;

import java.util.List;

import hexfan.lyrics.model.pojo.TrackInfo;
import io.reactivex.Flowable;

/**
 * Created by Pawel on 15.11.2017.
 */

public interface DatabaseDataModel {

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
}
