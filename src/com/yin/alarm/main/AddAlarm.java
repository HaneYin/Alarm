package com.yin.alarm.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
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

import com.yin.alarm.adapter.SongAdapter;
import com.yin.alarm.constant.Constants;
import com.yin.alarm.mythod.MyRecord;
import com.yin.alarm.sqlite.MySqlite;

/**
 * 闹钟设置界面
 */
public class AddAlarm extends Activity {
    private static final String LOG_TAG = "AddAlarm";
    private TimePicker tp;
    private TextView showName;
    private EditText etRemark;
    private Button choose,ok,longBtn;
    private SongAdapter songAdapter;
    private AlertDialog.Builder builder;
    private MyRecord myRecord;
    private String tpCurHour,tpCurMin,curHour,curMin,date,remark,ringPath;
    private MySqlite mySqlite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm);
        //保持屏幕唤醒，否则在录音过程当中锁屏容易被回收
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        init();
        startListener();
    }

//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == event.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            builder=new AlertDialog.Builder(AddAlarm.this);
//            builder.setMessage(Constants.discardSetting)
//                    .setPositiveButton(Constants.ok, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            finish();
//                        }
//                    })
//                    .setNegativeButton(Constants.cancel,null)
//                    .show();
//        }
//        return super.onKeyDown(keyCode, event);
//    }

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
        myRecord=new MyRecord();
        mySqlite=new MySqlite(AddAlarm.this);
        
        //设置初始值
        tp.setIs24HourView(true);
        tp.setCurrentHour(8);
        tp.setCurrentMinute(00);
        
        //设置view
        songAdapter=new SongAdapter(Constants.localMusic, AddAlarm.this);
    }

    private void startListener(){
        choose.setOnClickListener(listener);
        ok.setOnClickListener(listener);

        longBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                myRecord.startVoice();
                Toast.makeText(AddAlarm.this,Constants.startRecord,Toast.LENGTH_LONG).show();
                return false;
            }
        });
        longBtn.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        if(Constants.isReadyToRecord){
                            String ringPath= myRecord.stopVoice();
                            showName.setText(ringPath);
                            Toast.makeText(AddAlarm.this,Constants.endRecord,Toast.LENGTH_SHORT).show();
                            Log.i(LOG_TAG, "录音结束");
                        }else{
                            Toast.makeText(AddAlarm.this,Constants.tooShort,Toast.LENGTH_SHORT).show();
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
                    View view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.choose_song_dialog,null);
                    ListView songList= (ListView) view.findViewById(R.id.showSongList);
                    builder=new AlertDialog.Builder(AddAlarm.this);
                    builder.setTitle(Constants.chooseSong);
                    builder.setView(view);
                    builder.setNegativeButton(Constants.cancel,null);
                    final AlertDialog dialog = builder.show();
                    
                    if(Constants.localMusic!=null && Constants.localMusic.size()>0){
                    	songList.setAdapter(songAdapter);
                        songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                showName.setText(Constants.localMusic.get(position).getSong_title());
                                Constants.localMusic.get(position).getSong_url();
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
                    //TODO 铃声路径
                    ringPath=(showName.getText().toString());
                    mySqlite.addAlarm(date,remark,ringPath);
                    Toast.makeText(AddAlarm.this,Constants.addAlarmOK,Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }

        }
    };
}
