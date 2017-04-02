package com.yin.alarm.entity;

/**
 * Created by yin on 2016/10/11.
 */

public class Music {
    private int song_id;
    private String song_title;
    private String song_artist;
    private long song_time;
    private String song_url;
    private int song_isdeleted;

    public Music() {
        super();
    }

    public Music(String song_title, String song_artist, long song_time,
                 String song_url) {
        super();
        this.song_title = song_title;
        this.song_artist = song_artist;
        this.song_time = song_time;
        this.song_url = song_url;
    }

    public int getSong_id() {
        return song_id;
    }

    public void setSong_id(int song_id) {
        this.song_id = song_id;
    }

    public String getSong_title() {
        return song_title;
    }

    public void setSong_title(String song_title) {
        this.song_title = song_title;
    }

    public String getSong_artist() {
        return song_artist;
    }

    public void setSong_artist(String song_artist) {
        this.song_artist = song_artist;
    }

    public long getSong_time() {
        return song_time;
    }

    public void setSong_time(long song_time) {
        this.song_time = song_time;
    }

    public String getSong_url() {
        return song_url;
    }

    public void setSong_url(String song_url) {
        this.song_url = song_url;
    }

    public int getSong_isdeleted() {
        return song_isdeleted;
    }

    public void setSong_isdeleted(int song_isdeleted) {
        this.song_isdeleted = song_isdeleted;
    }

    @Override
    public boolean equals(Object o) {
        Music music = (Music) o;
        if (this.song_title.equals(music.getSong_title())
                && (this.song_time==music.getSong_time())
                && this.getSong_url().equals(music.getSong_url())) {
            return true;
        }
        return false;
    }
}
