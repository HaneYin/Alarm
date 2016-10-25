package com.example.yin.mythod;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Mr.Yin on 2016/10/25.
 */

public class MyMythod {
    public static void showDialog(Context context,String content,int i){
        switch (i){
            case 0://短按
                Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
                break;
            case 1://长按
                Toast.makeText(context,content,Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
