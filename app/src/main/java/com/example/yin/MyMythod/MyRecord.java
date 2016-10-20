package com.example.yin.MyMythod;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import com.example.yin.constant.MyConstant;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by yin on 2016/10/18.
 */

public class MyRecord {
    private static final String LOG_TAG = "MyRecord";
    /** sdCard状态 */
    private String state = null;
    /** 语音文件保存路径 */
    private String mFileName = null;
    /** 用于语音播放 */
    private MediaPlayer mPlayer = null;
    /** 用于完成录音 */
    private MediaRecorder mRecorder = null;
    /** 录音存储路径 */
    private static final String PATH = "/sdcard/MyAlarm/Ring/";

    public MyRecord() {
        state= Environment.getExternalStorageState();
        mPlayer=new MediaPlayer();
    }

    public void startVoice() {
        mFileName= PATH + UUID.randomUUID().toString() + ".amr";
        if (!state.equals(android.os.Environment.MEDIA_MOUNTED)) {
            Log.i(LOG_TAG, "SD Card is not mounted,It is  " + state + ".");
        }
        File directory = new File(mFileName).getParentFile();
        if (!directory.exists() && !directory.mkdirs()) {
            Log.i(LOG_TAG, "Path to file could not be created");
        }
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        mRecorder.start();
        MyConstant.isReadyToRecord=true;
    }

    public String stopVoice() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        MyConstant.isReadyToRecord=false;
        return mFileName;
    }
}
