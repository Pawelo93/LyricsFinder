package hexfan.lyrics.model;

import hexfan.lyrics.model.pojo.TrackInfoRequest;
import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Pawel on 20.06.2017.
 */

public interface ApiManager {

//    @GET("piosenka,{path}")
//    Call<ResponseBody> getLyricsTekstowo(@Path("path") String query);

    @GET("piosenka,{path}")
    Single<ResponseBody> getLyricsTekstowo(@Path("path") String query);

    @GET
    Call<ResponseBody> getLyricsFromGenius(@Url String url);

    @GET
    Single<TrackInfoRequest> getTrackInfo(@Url String url, @Query("artist") String artist, @Query("track") String track);
}
