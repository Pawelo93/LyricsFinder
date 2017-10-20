package hexfan.lyrics.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Html;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hexfan.lyrics.model.db.AppDatabase;
import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.model.pojo.TrackInfoRequest;
import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by Pawel on 20.06.2017.
 */

public class AppDataManager implements DataModel {
    private static final String TAG = "AppDataManager";

    private Context context;
    private SharedPreferences sharedPreferences;
    private AppDatabase database;
    private Gson gson;
    private ApiManager apiManager;

    public AppDataManager(Context context, SharedPreferences sharedPreferences, AppDatabase database, Gson gson, ApiManager apiManager) {
        this.context = context;
        this.sharedPreferences = sharedPreferences;
        this.database = database;
        this.gson = gson;
        this.apiManager = apiManager;


    }

    @Override
    public Observable<TrackInfo> getLyrics(final TrackInfo trackInfo) {

        return Observable.fromCallable(new Callable<TrackInfo>() {
            @Override
            public TrackInfo call() throws Exception {

                String artist = trackInfo.getArtist().replace(" ", "_");
                String song = trackInfo.getName().replace(" ", "_");
                String query = artist+","+song;
                String website = apiManager.getLyricsTekstowo(query).execute().body().string();

                Pattern pattern = Pattern.compile("<div class=\"song-text\">((.|\\n)*)<\\/div", Pattern.DOTALL);

                Matcher matcher = pattern.matcher(website);

                String lyrics = "";

                while (matcher.find()) {
                    Log.e(TAG, "call: found!");
                    lyrics = matcher.group(1);
//                    lyrics = lyrics.replace("<!--sse-->", "");
                    lyrics =  String.valueOf(Html.fromHtml(lyrics));

                }
                trackInfo.setLyrics(lyrics);

                return trackInfo;

                /// genius
//                String query = String.valueOf(artistName + " " + songName).replace(" ", "-").concat("-lyrics");
//                String website = apiManager.getLyricsFromGenius(query).execute().body().string();
//
//                Pattern pattern = Pattern.compile("<div class=\"lyrics\">((.|\\n)*?)<\\/div>");
//
//                Matcher matcher = pattern.matcher(website);
//
//                String lyrics = "";
//
//                while (matcher.find()) {
//                    Log.e(TAG, "call: found!");
//                    lyrics = matcher.group(1);
//                    lyrics = lyrics.replace("<!--sse-->", "");
//                    lyrics =  String.valueOf(Html.fromHtml(lyrics));
//
//                }
//                return new Lyrics(artistName, songName, lyrics, Lyrics.GENIUS_SOURCE);


            }
        });

    }

    @Override
    public Observable<TrackInfoRequest> getTrackInfo(final String artist, final String track) {
        return apiManager.getTrackInfo("http://ws.audioscrobbler.com/2.0/?method=track.getInfo&" +
                        "api_key=93bc2797678c0934fcf0d4d4ceabda7a&format=json", artist, track);
    }

    @Override
    public void cacheTrackInfo(TrackInfo trackInfo) {
        database.tracksDao().insert(trackInfo);
    }

    @Override
    public Flowable<List<TrackInfo>> getAllTrackInfoFromCache() {
        return database.tracksDao().getAllTrackInfos();
    }

    @Override
    public Observable<Boolean> test() {
        return null;
    }
}
