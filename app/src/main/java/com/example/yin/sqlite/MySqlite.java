package com.example.yin.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.yin.entity.Alarm;
import com.example.yin.entity.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yin on 2016/10/11.
 */

public class MySqlite extends SQLiteOpenHelper {
    private static final String LOG_TAG = "MySqlite";
    public MySqlite(Context context) {
        super(context, "alarm.db", null, 1);
    }

    public MySqlite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "alarm.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if(!tabIsExist("alarm")){
            String sql ="create table alarm (id integer primary key autoincrement,date varchar not null,remark varchar,songPath varchar null,isDeleted integer default '0',state integer default '0',period varchar default '1,2,3,4,5,6,7')";
            db.execSQL(sql);
        }
        Log.i(LOG_TAG,"sqlite start");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addAlarm(String date,String remark,String path){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql=null;
        if(remark==null && path==null){
            sql="insert into alarm(date) values (?)";
            db.execSQL(sql,new Object[]{date});
            db.close();
        }else if(remark!=null && path!=null){
            sql="insert into alarm(date,remark,songPath) values (?,?,?)";
            db.execSQL(sql,new Object[]{date,remark,path});
            db.close();
        }else if(remark!=null && path==null){
            sql="insert into alarm(date,remark) values (?,?)";
            db.execSQL(sql,new Object[]{date,remark});
            db.close();
        }else if(remark==null && path!=null){
            sql="insert into alarm(date,songPath) values (?,?)";
            db.execSQL(sql,new Object[]{date,path});
            db.close();
        }
        Log.i(LOG_TAG,"add ok");
    }

    public List<Alarm> getAllAlarm(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "select * from alarm where isDeleted=0";
        Cursor cursor = db.rawQuery(sql, null);
        List<Alarm> alarms=new ArrayList<Alarm>();
        int id,isDeleted,state;
        String date,remark,songPath,period;
        while(cursor.moveToNext()){
            id=cursor.getInt(cursor.getColumnIndex("id"));
            isDeleted=cursor.getInt(cursor.getColumnIndex("isDeleted"));
            state=cursor.getInt(cursor.getColumnIndex("state"));
            date=cursor.getString(cursor.getColumnIndex("date"));
            remark=cursor.getString(cursor.getColumnIndex("remark"));
            songPath=cursor.getString(cursor.getColumnIndex("songPath"));
            period=cursor.getString(cursor.getColumnIndex("period"));
            alarms.add(new Alarm(id,date,remark,songPath,isDeleted,state,period));
            Log.i(LOG_TAG,id+";"+date+";"+remark+";"+songPath);
        }
        cursor.close();
        db.close();
        return alarms;
    }
    /**
     * 根据歌曲url删除
     * @param song_url
     */
    public void deleteFromUrl(String song_url) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "update mymusic set song_isdeleted=1 where song_url=?";
        db.execSQL(sql, new String[] { song_url});
        db.close();
    }

    /**
     * 查询表格是否存在
     *
     * @param tabName
     * @return
     */
    public boolean tabIsExist(String tabName) {
        if (tabName == null) {
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            // tabName.trim() 移除首尾的空白
            // 查询表格的个数
            String sql = "select count(*) as c from sqlite_master where type ='table' and name ="
                    + "'" + tabName.trim() + "'";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        if(db!=null){
            db.close();
        }
        return false;
    }
    /**
     * 从数据库删除Music
     * @param music
     */
    public void deleteMusic(Music music) {
        String url=music.getSong_url();
        deleteFromUrl(url);
    }
}
