package com.example.yin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yin.alarm.R;
import com.example.yin.entity.Music;

import java.util.List;

/**
 * Created by yin on 2016/10/13.
 */

public class SongAdapter extends BaseAdapter {
    private List<Music> music;
    private Context context;

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
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.songitem,null);
            holder.tv= (TextView) convertView.findViewById(R.id.songitem);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(music.get(position).getSong_title());
        return convertView;
    }
    //持有者优化
    class ViewHolder{
        public TextView tv;
    }
}
