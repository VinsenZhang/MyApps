package com.tag.man.mylibrary.comm;

import android.util.Log;

import com.tag.man.mylibrary.BuildConfig;

public class Logger {

    private static final boolean isDebug = BuildConfig.DEBUG;


    public static void e(String tag, String msg){
        if (isDebug){
            Log.e(tag, msg);
        }
    }


    public static void i(String tag, String msg){
        if (isDebug){
            Log.i(tag, msg);
        }
    }


    public static void w(String tag, String msg){
        if (isDebug){
            Log.w(tag, msg);
        }
    }

}
