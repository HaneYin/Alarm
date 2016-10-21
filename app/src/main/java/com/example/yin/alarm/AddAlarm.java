package com.example.yin.alarm;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.yin.MyMythod.MyRecord;
import com.example.yin.adapter.SongAdapter;
import com.example.yin.constant.MyConstant;
import com.example.yin.entity.Alarm;
import com.example.yin.entity.Music;
import com.example.yin.service.serviceImpl.AlarmServiceImpl;
import com.example.yin.sqlite.MySqlite;

import java.util.ArrayList;

/**
 * 闹钟设置界面
 */
public class AddAlarm extends AppCompatActivity {
    private static final String LOG_TAG = "AddAlarm";
    private TimePicker tp;
    private TextView showName;
    private EditText etRemark;
    private Button choose,ok,longBtn;
    private View view;
    private ListView songList;
    private SongAdapter songAdapter;
    private AlertDialog.Builder builder;
    private MyRecord myRecord;
    private String tpCurHour,tpCurMin,curHour,curMin,date,remark,ringPath;
    private int pos;//记录点击本地音乐列表
    private AlarmServiceImpl alarmService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm);
        //保持屏幕唤醒，否则在录音过程当中锁屏容易被回收
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		//禁止主动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        init();
        startListener();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            builder=new AlertDialog.Builder(AddAlarm.this);
            builder.setMessage(MyConstant.discardSetting)
                    .setPositiveButton(MyConstant.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton(MyConstant.cancel,null)
                    .show();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 初始化
     */
    private void init() {
        showName=(TextView) findViewById(R.id.showSongName);
        etRemark=(EditText) findViewById(R.id._remark);
        choose=(Button) findViewById(R.id.chooseSong);
        ok=(Button) findViewById(R.id.saveSetting);
        longBtn= (Button) findViewById(R.id.record);
        tp= (TimePicker) findViewById(R.id.timePicker);
        pos=-1;
        myRecord=new MyRecord();
        tp.setIs24HourView(true);
        tp.setCurrentHour(8);
        tp.setCurrentMinute(00);
        songAdapter=new SongAdapter((MyConstant.localMusic==null ? new ArrayList<Music>() : MyConstant.localMusic),AddAlarm.this);
        alarmService=new AlarmServiceImpl(AddAlarm.this);
    }

    private void startListener(){
        choose.setOnClickListener(listener);
        ok.setOnClickListener(listener);

        longBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(ContextCompat.checkSelfPermission(AddAlarm.this, Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED){
                    //进入到这里代表没有权限.
                    if(ActivityCompat.shouldShowRequestPermissionRationale(AddAlarm.this,Manifest.permission.RECORD_AUDIO)){
                        //已经禁止提示了
                        Toast.makeText(AddAlarm.this, "您已禁止该权限，需要重新开启。", Toast.LENGTH_LONG).show();
                    }else{
                        ActivityCompat.requestPermissions(AddAlarm.this, new String[]{Manifest.permission.RECORD_AUDIO},0);
                    }
                } else {
                    myRecord.startVoice();
                    pos=-1;
                    Toast.makeText(AddAlarm.this,MyConstant.startRecord,Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
        longBtn.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        if(MyConstant.isReadyToRecord){
                            String ringPath= myRecord.stopVoice();
                            showName.setText(ringPath);
                            Toast.makeText(AddAlarm.this,MyConstant.endRecord,Toast.LENGTH_SHORT).show();
                            Log.i(LOG_TAG, "录音结束");
                        }else{
                            Toast.makeText(AddAlarm.this,MyConstant.tooShort,Toast.LENGTH_SHORT).show();
                            Log.i(LOG_TAG, "时间太短了");
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

    };

    /**
     * 监听
     */
    View.OnClickListener listener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.chooseSong://点我
                    MyConstant.listPosition=-1;
                    view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.choose_song_dialog,null);
                    songList= (ListView) view.findViewById(R.id.showSongList);
                    if(MyConstant.localMusic==null || MyConstant.localMusic.size()==0){
                        builder=new AlertDialog.Builder(AddAlarm.this);
                        builder.setMessage(MyConstant.noSong)
                                .setPositiveButton(MyConstant.ok,null)
                                .show();
                    }else{
                        builder=new AlertDialog.Builder(AddAlarm.this);
                        builder.setTitle(MyConstant.chooseSong);
                        builder.setView(view);
                        builder.setNegativeButton(MyConstant.cancel,null);
                        final AlertDialog dialog = builder.show();
                        songList.setAdapter(songAdapter);
                        songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                pos=position;
                                showName.setText(MyConstant.localMusic.get(position).getSong_title());
                                MyConstant.listPosition=position;
                                songAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });
                    }
                    break;
                case R.id.saveSetting://确定
                    tpCurHour=tp.getCurrentHour().toString();
                    tpCurMin=tp.getCurrentMinute().toString();
                    curHour=(tpCurHour.length()==1 ? "0"+tpCurHour : tpCurHour);
                    curMin=(tpCurMin.length()==1 ? "0"+tpCurMin : tpCurMin);
                    date=(curHour+":"+curMin);
                    remark=(etRemark.getText().toString()==null || etRemark.getText().toString().trim().equals("") ? null : etRemark.getText().toString());
                    ringPath=(pos==-1 ? (!showName.getText().toString().equals(MyConstant.defaultRing) ? showName.getText().toString() : null) : MyConstant.localMusic.get(pos).getSong_url());
                    alarmService.save(new Alarm(date,remark,ringPath));
                    Toast.makeText(AddAlarm.this,MyConstant.addAlarmOK,Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }

        }
    };
}
