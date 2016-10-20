package com.example.yin.entity;

import java.util.Date;

/**
 * Created by yin on 2016/10/10.
 */

public class Alarm {
    private int id;
    private String date;//响铃时间
    private String remark;//备注
    private String songPath;
    private int isDeleted;
    private int state;//状态开启
    private String period;//周期

    public Alarm() {
    }

    public Alarm(String date, String remark, String songPath, int isDeleted, int state, String period) {
        this.date = date;
        this.remark = remark;
        this.songPath = songPath;
        this.isDeleted = isDeleted;
        this.state = state;
        this.period = period;
    }

    public Alarm(int id, String date, String remark, String songPath, int isDeleted, int state, String period) {
        this.id = id;
        this.date = date;
        this.remark = remark;
        this.songPath = songPath;
        this.isDeleted = isDeleted;
        this.state = state;
        this.period = period;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", remark='" + remark + '\'' +
                ", songPath='" + songPath + '\'' +
                ", isDeleted=" + isDeleted +
                ", state=" + state +
                ", period='" + period + '\'' +
                '}';
    }
}
