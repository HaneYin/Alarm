package com.example.yin.service;

import com.example.yin.entity.Alarm;

import java.util.List;

/**
 * Created by Mr.Yin on 2016/10/20.
 */

public interface AlarmService {
    public void save(Alarm alarm);
    public void delete(int id);
    public void update(Alarm alram);
    public Alarm findOneById(int id);
    public List<Alarm> getAll();
}
