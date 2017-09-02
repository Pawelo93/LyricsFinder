package hexfan.lyrics.model.pojo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pawel on 16.07.2017.
 */

@Entity
public class TrackInfo {

    private String lyrics;

    @PrimaryKey(autoGenerate = true)
    int id;

    private String name;
    private String artist;
    private String album;
    private String albumCover;
    private String duration;
    private String listeners;
    private ArrayList<String> tags;
    private String description;

    public static TrackInfo fromTrackInfoRequest(TrackInfoRequest trackInfoRequest) {
        TrackInfo trackInfo = new TrackInfo();
        trackInfo.setName(trackInfoRequest.getName());
        trackInfo.setArtist(trackInfoRequest.getArtist());
        trackInfo.setAlbum(trackInfoRequest.getAlbumTitle());
        trackInfo.setAlbumCover(trackInfoRequest.getAlbumCoverUrl());
        trackInfo.setDuration(trackInfoRequest.getDuration());
        trackInfo.setListeners(trackInfoRequest.getListeners());
        trackInfo.setTags(trackInfoRequest.getTags());
        trackInfo.setDescription(trackInfoRequest.getDescription());
        return trackInfo;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbumCover() {
        return albumCover;
    }

    public void setAlbumCover(String albumCover) {
        this.albumCover = albumCover;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getListeners() {
        return listeners;
    }

    public void setListeners(String listeners) {
        this.listeners = listeners;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
