package hexfan.lyrics.model.pojo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pawel on 11.07.2017.
 */

public class TrackInfoRequest {

    @SerializedName("track")
    Track track = new Track();

    static class Track{
        @SerializedName("album")
        Album album = new Album();

        @SerializedName("name")
        String name;

        @SerializedName("duration")
        String duration;

        @SerializedName("listeners")
        String listeners;

        @SerializedName("toptags")
        TopTags topTags;

        @SerializedName("wiki")
        Wiki wiki = new Wiki();
    }

    static class Album{

        @SerializedName("artist")
        String artist;

        @SerializedName("title")
        String title;

        @SerializedName("image")
        List<Image> images = new ArrayList<>();
    }

    static class Image{
        @SerializedName("#text")
        String url;
        @SerializedName("size")
        String size;
    }

    static class TopTags{
        @SerializedName("tag")
        List<Tag> tags;
    }

    static class Tag{
        @SerializedName("name")
        String name;
        @SerializedName("url")
        String url;
    }

    static class Wiki{
        @SerializedName("published")
        String published;
        @SerializedName("summary")
        String summary;
        @SerializedName("content")
        String content;
    }

    public void setName(String name) {
        this.track.name = name;
    }

    public void setAlbumTitle(String albumTitle) {
        track.album.title = albumTitle;
    }

    public void setAlbumCoverUrl(String albumCoverUrl) {
        Image image = new Image();
        image.size = "extralarge";
        image.url = albumCoverUrl;
        track.album.images.add(image);
    }

    public String getAlbumTitle(){
        return track.album.title;
    }

    public String getAlbumCoverUrl(){
        for (Image image : track.album.images) {
            if(image.size.equals("extralarge"))
                return image.url;
        }

        return track.album.images.get(track.album.images.size() - 1).url;
    }

    public String getName(){
        return track.name;
    }

    public String getArtist(){
        return track.album.artist;
    }

    public String getDuration() {
        return track.duration;
    }

    public String getListeners() {
        return track.listeners;
    }

    public ArrayList<String> getTags() {
        ArrayList<String> tags = new ArrayList<>();
        if(track == null || track.topTags == null || track.topTags.tags != null)
            return tags;

        for (Tag tag : track.topTags.tags) {
            tags.add(tag.name);
        }
        return tags;
    }

    public String getDescription() {
        return track.wiki.content;
    }


}
