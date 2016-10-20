package com.example.yin.constant;

import com.example.yin.entity.Alarm;
import com.example.yin.entity.Music;

import java.util.List;

/**
 * Created by yin on 2016/10/11.
 */

public class MyConstant {
    public static String chooseSong="铃声选择";
    public static String defaultRing="默认铃声";
    public static String discardSetting="要舍弃对该闹钟所做的更改吗？";
    public static String addAlarmOK="添加闹钟成功！";
    public static String ok="确定";
    public static String cancel="取消";
    public static String twice="再按一次退出";
    public static String startRecord="开始录音...";
    public static String endRecord="录音结束";
    public static String tooShort="按住别松手";
    public static List<Music> localMusic = null;
    public static boolean isReadyToRecord=false;
    public static List<Alarm> localAlarm = null;
    public static int listPosition=-1;

}
