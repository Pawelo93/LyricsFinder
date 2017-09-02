package hexfan.lyrics.ui.main;

import java.util.List;

import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.model.pojo.TrackInfoRequest;
import hexfan.lyrics.ui.base.BasePresenter;

/**
 * Created by Pawel on 12.07.2017.
 */

public interface MainContract {

    interface View {
        void displayNewTrack(TrackInfo trackInfo);

        void displayFulLTrack(TrackInfo trackInfo);

        void displayHistory(List<TrackInfo> cacheList);

//        void displayFulLTrackWithLyrics(TrackInfo trackInfo);
    }

    abstract class Presenter extends BasePresenter<View> {

    }
}
