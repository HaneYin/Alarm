package com.example.yin.service.serviceImpl;

import android.content.Context;

import com.example.yin.dao.impl.AlarmDaoImpl;
import com.example.yin.entity.Alarm;
import com.example.yin.service.AlarmService;

import java.util.List;

/**
 * Created by Mr.Yin on 2016/10/20.
 */

public class AlarmServiceImpl implements AlarmService {
    private AlarmDaoImpl daoImpl;

    public AlarmServiceImpl(Context context) {
        this.daoImpl = new AlarmDaoImpl(context);
    }

    @Override
    public void save(Alarm alarm) {
        daoImpl.save(alarm);
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
        return daoImpl.getAll();
    }
}
