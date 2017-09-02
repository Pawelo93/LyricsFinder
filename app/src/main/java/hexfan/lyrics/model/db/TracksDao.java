package hexfan.lyrics.model.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import hexfan.lyrics.model.pojo.TrackInfo;
import io.reactivex.Flowable;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Pawel on 16.07.2017.
 */

@Dao
public interface TracksDao {
    @Query("SELECT * FROM TrackInfo")
    Flowable<List<TrackInfo>> getAllTrackInfos();

    @Insert(onConflict = REPLACE)
    void insert(TrackInfo trackInfo);

    @Insert(onConflict = REPLACE)
    void insertAll(List<TrackInfo> trackInfos);
}
