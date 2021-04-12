package com.example.musicapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SongModel {

    @Expose
    @SerializedName("song")
    String song;

    @Expose
    @SerializedName("url")
    String url;

    @Expose
    @SerializedName("cover_image")
    String cover_image;
    @Expose
    @SerializedName("artists")
    String artists;

    public SongModel(String song, String url, String cover_image, String artists) {
        this.song = song;
        this.url = url;
        this.cover_image = cover_image;
        this.artists = artists;
    }

    public SongModel() {
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }
}
