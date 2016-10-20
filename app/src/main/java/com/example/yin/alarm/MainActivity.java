package com.example.yin.alarm;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.yin.constant.MyConstant;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private ListView lv;
    private ImageView add_img;
    private Intent intent;
    private boolean flag;
    private String time=null;
    private SimpleDateFormat dateFormat;
    private long times = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0x001){
                tv.setText(time);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        listener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setdefault: //设置默认铃声

                break;
            case R.id.moreset:  //更多设置
                Toast.makeText(getApplicationContext(), "moreset", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化
     */
    private void init(){
        tv= (TextView) findViewById(R.id.showTime);
        lv= (ListView) findViewById(R.id.timeList);
        add_img= (ImageView) findViewById(R.id.addImg);
        flag=true;
        dateFormat=new SimpleDateFormat("HH:mm:ss");
        tv.setText(dateFormat.format(new Date()));
    }

    /**
     * 开始刷新UI
     */
    private void refresh(){
        if(flag){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (flag) {
                        try {
                            Thread.sleep(1000);
                            time = dateFormat.format(new Date());

                            //handler刷新
                            handler.sendEmptyMessage(0x001);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    /**
     * 监听
     */
    private void listener(){
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(),AddAlarm.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if(System.currentTimeMillis()-times > 2000){
                times=System.currentTimeMillis();
                Toast.makeText(getApplicationContext(), MyConstant.twice, Toast.LENGTH_SHORT).show();
                return true;
            }else{
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        flag=true;
        refresh();
    }

    @Override
    protected void onStop() {
        super.onStop();
        flag=false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flag=false;
    }

}
