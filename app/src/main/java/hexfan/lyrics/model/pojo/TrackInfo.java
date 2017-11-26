package hexfan.lyrics.model.pojo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pawel on 16.07.2017.
 */

@Entity
public class TrackInfo implements Parcelable{

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

    public TrackInfo(){

    }

    protected TrackInfo(Parcel in) {
        lyrics = in.readString();
        id = in.readInt();
        name = in.readString();
        artist = in.readString();
        album = in.readString();
        albumCover = in.readString();
        duration = in.readString();
        listeners = in.readString();
        tags = in.createStringArrayList();
        description = in.readString();
    }

    public static final Creator<TrackInfo> CREATOR = new Creator<TrackInfo>() {
        @Override
        public TrackInfo createFromParcel(Parcel in) {
            return new TrackInfo(in);
        }

        @Override
        public TrackInfo[] newArray(int size) {
            return new TrackInfo[size];
        }
    };

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrackInfo trackInfo = (TrackInfo) o;

        if (id != trackInfo.id) return false;
        if (lyrics != null ? !lyrics.equals(trackInfo.lyrics) : trackInfo.lyrics != null)
            return false;
        if (name != null ? !name.equals(trackInfo.name) : trackInfo.name != null) return false;
        if (artist != null ? !artist.equals(trackInfo.artist) : trackInfo.artist != null)
            return false;
        if (album != null ? !album.equals(trackInfo.album) : trackInfo.album != null) return false;
        if (albumCover != null ? !albumCover.equals(trackInfo.albumCover) : trackInfo.albumCover != null)
            return false;
        if (duration != null ? !duration.equals(trackInfo.duration) : trackInfo.duration != null)
            return false;
        if (listeners != null ? !listeners.equals(trackInfo.listeners) : trackInfo.listeners != null)
            return false;
        if (tags != null ? !tags.equals(trackInfo.tags) : trackInfo.tags != null) return false;
        return description != null ? description.equals(trackInfo.description) : trackInfo.description == null;
    }

    @Override
    public int hashCode() {
        int result = lyrics != null ? lyrics.hashCode() : 0;
        result = 31 * result + id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (artist != null ? artist.hashCode() : 0);
        result = 31 * result + (album != null ? album.hashCode() : 0);
        result = 31 * result + (albumCover != null ? albumCover.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (listeners != null ? listeners.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TrackInfo{" +
                "lyrics='" + lyrics + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", albumCover='" + albumCover + '\'' +
                ", duration='" + duration + '\'' +
                ", listeners='" + listeners + '\'' +
                ", tags=" + tags +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lyrics);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(artist);
        dest.writeString(album);
        dest.writeString(albumCover);
        dest.writeString(duration);
        dest.writeString(listeners);
        dest.writeStringList(tags);
        dest.writeString(description);
    }


}
