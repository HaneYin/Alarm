package com.example.yin.constant;

import com.example.yin.entity.Alarm;
import com.example.yin.entity.Music;

import java.util.List;

/**
 * Created by yin on 2016/10/11.
 */

public class MyConstant {
    public static String chooseSong="铃声选择";
    public static String noSong="一首歌也没有...";
    public static String defaultRing="默认铃声";
    public static String discardSetting="要舍弃对该闹钟所做的更改吗？";
    public static String addAlarmOK="添加闹钟成功！";
    public static String sorry="录音权限未获取，无法使用此功能";
    public static String promptHaveNoRight="由于尚未开启录音权限，您将无法正常使用录音功能";
    public static String confirmUpdate="该闹钟已存在，是否修改？";
    public static String ok="确定";
    public static String cancel="取消";
    public static String startNow="立即开启";
    public static String twice="再按一次退出";
    public static String startRecord="开始录音...";
    public static String endRecord="录音结束";
    public static String tooShort="按住别松手";
    public static List<Music> localMusic = null;
    public static List<Alarm> localAlarm = null;
    public static boolean isReadyToRecord=false;
    public static boolean haveRadioRight=true;
    public static int listPosition=-1;
}
