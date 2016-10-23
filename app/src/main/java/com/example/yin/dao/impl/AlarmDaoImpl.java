package com.example.yin.dao.impl;

import android.content.Context;

import com.example.yin.dao.AlarmDao;
import com.example.yin.entity.Alarm;
import com.example.yin.sqlite.MySqlite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Yin on 2016/10/20.
 */

public class AlarmDaoImpl implements AlarmDao {
    private MySqlite mySqlite;

    public AlarmDaoImpl() {
    }

    public AlarmDaoImpl(Context context) {
        this.mySqlite = new MySqlite(context);
    }

    @Override
    public void save(Alarm alarm) {
        List<Object> key=new ArrayList<>();
        List<Object> values=new ArrayList<>();
        key.add("date");
        values.add(alarm.getDate());
        if(alarm.getRemark()!=null){
            key.add("remark");
            values.add(alarm.getRemark());
        }
        if(alarm.getSongPath()!=null){
            key.add("songPath");
            values.add(alarm.getSongPath());
        }
        this.mySqlite.addAlarm(key,values);
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(Alarm alram) {

    }

    @Override
    public Alarm findOneById(int id) {
        return null;
    }

    @Override
    public List<Alarm> getAll() {
        return mySqlite.getAllAlarm();
    }
}
