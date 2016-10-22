package com.example.yin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yin.alarm.R;
import com.example.yin.entity.Alarm;

import java.util.List;

/**
 * Created by yin on 2016/10/22.
 */

public class AlarmAdapter extends BaseAdapter {
    private List<Alarm> localAlarm;
    private Context context;

    public AlarmAdapter(List<Alarm> localAlarm, Context context) {
        this.localAlarm = localAlarm;
        this.context = context;
    }

    @Override
    public int getCount() {
        return localAlarm.size();
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
            convertView= LayoutInflater.from(context).inflate(R.layout.alarmitem,null);
            holder.d= (TextView) convertView.findViewById(R.id.itemdate);
            holder.r= (TextView) convertView.findViewById(R.id.itemremark);
            holder.p= (TextView) convertView.findViewById(R.id.itemperiod);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.d.setText(localAlarm.get(position).getDate());
        holder.r.setText(localAlarm.get(position).getRemark());
        holder.p.setText(localAlarm.get(position).getPeriod());
        return convertView;
    }

    class ViewHolder{
        TextView d,r,p;
    }
}
