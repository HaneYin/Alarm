package com.yin.alarm.main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import com.yin.alarm.constant.Constants;
import com.yin.alarm.entity.Music;

public class StartActivity extends Activity {
	private Intent intent;
    private List<Music> localMusic;
    private Music music;
//    private MySqlite mySqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //1.初始化
        this.init();
        //2.获取本地音乐
        this.getLocalMusic();
        //3.获取本地闹钟
        this.getLocalAlarm();
        //4.启动主界面
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 初始化
     */
    private void init(){
        intent=new Intent(StartActivity.this,MainActivity.class);
        localMusic=new ArrayList<Music>();
//        mySqlite=new MySqlite(StartActivity.this);
    }
    
    private void getLocalMusic() {
    	//扫描本地音乐并存入数据库
        new Thread(new Runnable() {
            @SuppressWarnings("unused")
			@Override
            public void run() {
                String title,singer,album,url,_display_name,mime_type;
                int durations;long duration,size;
                // 得到所有Music对象
                Cursor cursor = getApplication().getContentResolver().query(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
                        null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                    if(duration<10000){
                        continue;
                    }
                    //歌曲名
                    title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    //歌手
                    singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    //专辑
                    album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                    //长度
                    size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                    //时长
                    durations = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                    //路径
                    url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    //显示的文件名
                    _display_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    //类型
                    mime_type = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE));
                    music = new Music(title, singer, duration, url);
                    localMusic.add(music);
                }
                cursor.close();
                Constants.localMusic=localMusic;
                //得到闹钟
//                MyConstant.localAlarm = mySqlite.getAllAlarm();
            }
        }).start();
    }
    
    private void getLocalAlarm() {
    	
    }
}
