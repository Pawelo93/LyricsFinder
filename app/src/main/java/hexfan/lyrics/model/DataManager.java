package hexfan.lyrics.model;

import android.text.Html;
import android.util.Log;

import java.io.InterruptedIOException;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hexfan.lyrics.model.db.AppDatabase;
import hexfan.lyrics.model.pojo.TrackInfo;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Pawel on 14.11.2017.
 */

public class DataManager implements DataModel {
    private static final String TAG = "DataManager";

    private ApiManager apiManager;
    private BehaviorSubject<TrackInfo> trackInfoBus;

    public DataManager(ApiManager apiManager, BehaviorSubject<TrackInfo> bus){
        this.apiManager = apiManager;
        this.trackInfoBus = bus;
    }

//    @Override
//    public void cacheTrackInfo(TrackInfo trackInfo) {
//        database.tracksDao().insert(trackInfo);
//    }



    @Override
    public Observable<TrackInfo> subscribeRawTrackInfo() {
        return trackInfoBus;
    }

    @Override
    public Single<TrackInfo> getTrackInfo(String artist, String track) {
        return apiManager.getTrackInfo("http://ws.audioscrobbler.com/2.0/?method=track.getInfo&" +
                "api_key=93bc2797678c0934fcf0d4d4ceabda7a&format=json", artist, track)
                .map(TrackInfo::fromTrackInfoRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<TrackInfo> getLyrics(final TrackInfo trackInfo) {
        String artist = trackInfo.getArtist().replace(" ", "_").replace("'", "_");
        String song = trackInfo.getName().replace(" ", "_").replace("'", "_");
        String query = artist+","+song;
        return apiManager.getLyricsTekstowo(query)
                .subscribeOn(Schedulers.io())
                .map(responseBody -> responseBody.string())
                .map(website -> {
                    Pattern pattern = Pattern.compile("<div class=\"song-text\">((.|\\n)*)<\\/div", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(website);
                    String lyrics = "";
                    while (matcher.find()) {
                        Log.e(TAG, "call: found!");
                        lyrics = matcher.group(1);
                        lyrics =  String.valueOf(Html.fromHtml(lyrics));
                    }
                    trackInfo.setLyrics(lyrics);
                    return trackInfo;
                });


//        return Single.fromCallable(new Callable<TrackInfo>() {
//            @Override
//            public TrackInfo call() throws Exception {
//
////                String artist = trackInfo.getArtist().replace(" ", "_");
////                String song = trackInfo.getName().replace(" ", "_");
////                String query = artist+","+song;
////                String website;
//////                try {
////                    website = apiManager.getLyricsTekstowo(query).execute().body().string();
//////                }catch (InterruptedIOException e){
//////                    System.out.println("ERROR " + e.getMessage());
//////                }
////                Pattern pattern = Pattern.compile("<div class=\"song-text\">((.|\\n)*)<\\/div", Pattern.DOTALL);
////
////                Matcher matcher = pattern.matcher(website);
////
////                String lyrics = "";
////
////                while (matcher.find()) {
////                    Log.e(TAG, "call: found!");
////                    lyrics = matcher.group(1);
//////                    lyrics = lyrics.replace("<!--sse-->", "");
////                    lyrics =  String.valueOf(Html.fromHtml(lyrics));
////
////                }
////                trackInfo.setLyrics(lyrics);
////
////                return trackInfo;
//
//                return trackInfo;
//
//
//
//                /// genius
////                String query = String.valueOf(artistName + " " + songName).replace(" ", "-").concat("-lyrics");
////                String website = apiManager.getLyricsFromGenius(query).execute().body().string();
////
////                Pattern pattern = Pattern.compile("<div class=\"lyrics\">((.|\\n)*?)<\\/div>");
////
////                Matcher matcher = pattern.matcher(website);
////
////                String lyrics = "";
////
////                while (matcher.find()) {
////                    Log.e(TAG, "call: found!");
////                    lyrics = matcher.group(1);
////                    lyrics = lyrics.replace("<!--sse-->", "");
////                    lyrics =  String.valueOf(Html.fromHtml(lyrics));
////
////                }
////                return new Lyrics(artistName, songName, lyrics, Lyrics.GENIUS_SOURCE);
//
//
//            }
//        }).subscribeOn(Schedulers.io());

    }

}
