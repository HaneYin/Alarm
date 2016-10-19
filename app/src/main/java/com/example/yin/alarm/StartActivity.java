package com.example.yin.alarm;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yin.constant.MyConstant;
import com.example.yin.entity.Music;
import com.example.yin.myview.CircleProgressView;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {
    private CircleProgressView myProgress;
    private Intent intent;
    private List<Music> localMusic;
    private Music music;
    private int c=0;

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
    }

    /**
     * 启动主界面
     */
    private void startMainActivity(){
        //扫描本地音乐并存入数据库
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 得到所有Music对象
                Cursor cursor = getApplication().getContentResolver().query(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
                        null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    String title = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                    String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    music = new Music(title, artist, duration, url);
                    localMusic.add(music);
                    c+=100/cursor.getCount();
                    c=((i==cursor.getCount()-1)&&(c<100) ? 100 : c);
                    c=(c>100 ? 100 : c);
                    try {
                        Thread.sleep(100);
                        handler.sendEmptyMessage(0x001);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                cursor.close();
//                if(localMusic!=null){
//                    mySqlite.manageMusic(localMusic);
//                }
//                MyConstant.localMusic=mySqlite.getAllMusic();
                MyConstant.localMusic=localMusic;
                startActivity(intent);
                finish();
            }
        }).start();
    }
}
