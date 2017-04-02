package com.yin.alarm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.yin.alarm.entity.Music;
import com.yin.alarm.main.R;

/**
 * Created by yin on 2016/10/13.
 */

public class SongAdapter extends BaseAdapter {
    private List<Music> music;
    private Context context;
    private TextView tv;
    private boolean existMusic;

    public SongAdapter(List<Music> music, Context context){
        this.music=music;
        this.context=context;
        this.existMusic=music!=null && music.size()>0;
    }

    @Override
    public int getCount() {
        return existMusic ? music.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	if(!existMusic)
    		return null;
        convertView= LayoutInflater.from(context).inflate(R.layout.songitem,null);
        tv= (TextView) convertView.findViewById(R.id.songitem);
        tv.setText(music.get(position).getSong_title());
        return convertView;
    }
}
