package hexfan.lyrics.model;

import java.util.List;

import hexfan.lyrics.model.db.AppDatabase;
import hexfan.lyrics.model.pojo.TrackInfo;
import io.reactivex.Flowable;

/**
 * Created by Pawel on 15.11.2017.
 */

public class DatabaseDataManager implements DatabaseDataModel{

    private AppDatabase database;

    public DatabaseDataManager(AppDatabase database) {
        this.database = database;
    }

    @Override
    public void cacheTrackInfo(TrackInfo trackInfo) {
        database.tracksDao().insert(trackInfo);
    }

    @Override
    public Flowable<List<TrackInfo>> getAllTrackInfoFromCache() {
        return database.tracksDao().getAllTrackInfos();
    }
}
