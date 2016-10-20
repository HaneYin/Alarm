package com.example.yin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yin.alarm.R;
import com.example.yin.constant.MyConstant;
import com.example.yin.entity.Music;

import java.util.List;

/**
 * Created by yin on 2016/10/13.
 */

public class SongAdapter extends BaseAdapter {
    private List<Music> music;
    private Context context;
    private TextView tv;

    public SongAdapter(List<Music> music, Context context){
        this.music=music;
        this.context=context;
    }

    @Override
    public int getCount() {
        return music.size();
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
        convertView= LayoutInflater.from(context).inflate(R.layout.songitem,null);
        tv= (TextView) convertView.findViewById(R.id.songitem);
        tv.setText(music.get(position).getSong_title());
        if(position== MyConstant.listPosition){
            convertView.setBackgroundColor(context.getResources().getColor(R.color.item));
        }
        return convertView;
    }
}
