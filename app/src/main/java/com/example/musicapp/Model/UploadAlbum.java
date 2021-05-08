package com.example.musicapp.Model;

public class UploadAlbum {

    String name;
    String url;
    String songsCategory;

    public UploadAlbum(String name,String songsCategory, String url) {

        this.url = url;
        this.name = name;
        this.songsCategory = songsCategory;
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
        return songsCategory;
    }

    public void setSongsCategory(String songsCategory) {
        this.songsCategory = songsCategory;
    }
}
