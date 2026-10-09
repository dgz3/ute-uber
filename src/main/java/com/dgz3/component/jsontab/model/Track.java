package com.dgz3.component.jsontab.model;

public class Track 
{ 
    private String name;
    private String artist;
    private String album;
    private String duration;
    private String added_at;

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
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public String getAdded_at() {
        return added_at;
    }
    public void setAdded_at(String added_at) {
        this.added_at = added_at;
    }
}

//public record Track 
//(
//    String name,
//    String artist,
//    String album,
//    String duration,
//    String added_at
//)
//{ }

