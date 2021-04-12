package com.example.musicapp.Model;

public class UploadAlbum {
    String url;
    String name;
    String song;

    public UploadAlbum(String url, String name, String songsCategory) {
        this.url = url;
        this.name = name;
        this.song = songsCategory;
    }

    public UploadAlbum() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSongsCategory() {
        return song;
    }

    public void setSongsCategory(String songsCategory) {
        this.song = songsCategory;
    }
}
