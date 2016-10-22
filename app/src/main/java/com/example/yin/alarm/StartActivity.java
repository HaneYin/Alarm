package com.example.yin.alarm;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.yin.constant.MyConstant;
import com.example.yin.entity.Alarm;
import com.example.yin.entity.Music;
import com.example.yin.myview.CircleProgressView;
import com.example.yin.service.serviceImpl.AlarmServiceImpl;
import com.example.yin.sqlite.MySqlite;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {
    private static final String LOG_TAG = "StartActivity";
    private CircleProgressView myProgress;
    private Intent intent;
    private List<Music> localMusic;
    private Music music;
    private int c=0;
    private AlarmServiceImpl alarmService;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0x001){
                myProgress.setProgress(c);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        init();
        startMainActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 初始化
     */
    private void init(){
        myProgress= (CircleProgressView) findViewById(R.id.myProgress);
        intent=new Intent(StartActivity.this,MainActivity.class);
        localMusic=new ArrayList<>();
        music=new Music();
        alarmService=new AlarmServiceImpl(StartActivity.this);
    }

    /**
     * 启动主界面
     */
    private void startMainActivity(){
        //扫描本地音乐并存入数据库
        new Thread(new Runnable() {
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

                    c+=100/cursor.getCount();
                    c=((i==cursor.getCount()-1)&&(c<100) ? 100 : c);
                    c=(c>100 ? 100 : c);
                    try {
                        Thread.sleep(100);
                        handler.sendEmptyMessage(0x001);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(duration<10000){
                        continue;
                    }
                    music = new Music(title, singer, duration, url);
                    localMusic.add(music);
                }
                cursor.close();
                MyConstant.localMusic=localMusic;
                //得到闹钟
                MyConstant.localAlarm = alarmService.getAll();
                startActivity(intent);
                finish();
            }
        }).start();
    }
}
