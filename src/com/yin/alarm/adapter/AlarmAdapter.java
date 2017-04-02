package com.yin.alarm.adapter;

import java.util.List;

import com.yin.alarm.entity.Alarm;
import com.yin.alarm.main.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AlarmAdapter extends BaseAdapter {
	private Context context;
	private List<Alarm> alarms;
	private boolean existAlarm;
	
	public AlarmAdapter(Context context, List<Alarm> alarms) {
		this.context = context;
		this.alarms = alarms;
		this.existAlarm = alarms!=null && alarms.size()>0;
	}

	@Override
	public int getCount() {
		return existAlarm ? alarms.size() : 0;
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
		if(!existAlarm)
			return null;
		convertView = LayoutInflater.from(context).inflate(R.layout.alarmitem, null);
		TextView tvName = (TextView) convertView.findViewById(R.id.alarmName);
		TextView tvMark = (TextView) convertView.findViewById(R.id.alarmMarks);
		tvName.setText(alarms.get(position).getSongPath());
		tvMark.setText(alarms.get(position).getRemark());
		return convertView;
	}

}
