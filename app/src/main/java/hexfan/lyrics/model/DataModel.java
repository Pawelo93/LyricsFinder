package hexfan.lyrics.model;

import java.util.List;


import hexfan.lyrics.R;
import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.model.pojo.TrackInfoRequest;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;

/**
 * Created by Pawel on 14.11.2017.
 */

public interface DataModel {

    /**
     * Subscribe to raw trackInfo, only band and name, no image
     * @return Raw trackInfoBus
     */
    Observable<TrackInfo> subscribeRawTrackInfo();

    /**
     * Load more info about track from Last.fm
     * @param artist song artist
     * @param track song name
     * @return TrackInfoRequest which you convert to TrackInfo
     */
    Single<TrackInfo> getTrackInfo(String artist, String track);

    /**
     * Load lyrics
     * @param trackInfo
     * @return
     */
    Single<TrackInfo> getLyrics(final TrackInfo trackInfo);
}
