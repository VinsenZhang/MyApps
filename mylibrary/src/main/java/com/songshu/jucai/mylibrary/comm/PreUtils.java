package com.songshu.jucai.mylibrary.comm;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * SharePreferences utils
 * Created by Vinsen on 17/3/8.
 */

public class PreUtils {

    private static SharedPreferences preferences;
    private static final String preProFix = "PreUtils_";
    private static SharedPreferences.Editor editor;


    public static void init(Context context) {
        String key = preProFix + context.getPackageName();
        preferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
    }


    public static boolean saveString(String key, String value) {
        if (preferences == null) {
            throw new NullPointerException("have not init...");
        }
        if (editor == null) {
            editor = preferences.edit();
        }
        return editor.putString(key, value).commit();
    }


    public static void saveStringN(String key, String value) {
        if (preferences == null) {
            throw new NullPointerException("have not init...");
        }
        if (editor == null) {
            editor = preferences.edit();
        }
        editor.putString(key, value).apply();
    }


    public static boolean savaInt(String key, int value) {
        if (preferences == null) {
            throw new NullPointerException("have not init...");
        }
        if (editor == null) {
            editor = preferences.edit();
        }
        return editor.putInt(key, value).commit();
    }


    public static void savaIntN(String key, int value) {
        if (preferences == null) {
            throw new NullPointerException("have not init...");
        }
        if (editor == null) {
            editor = preferences.edit();
        }
        editor.putInt(key, value).apply();
    }


    public static boolean saveFloat(String key, float value) {
        if (preferences == null) {
            throw new NullPointerException("have not init...");
        }
        if (editor == null) {
            editor = preferences.edit();
        }
        return editor.putFloat(key, value).commit();
    }

    public static boolean saveLong(String key, long value) {
        if (preferences == null) {
            throw new NullPointerException("have not init...");
        }
        if (editor == null) {
            editor = preferences.edit();
        }
        return editor.putLong(key, value).commit();
    }


    public static void saveFloatN(String key, float value) {
        if (preferences == null) {
            throw new NullPointerException("have not init...");
        }
        if (editor == null) {
            editor = preferences.edit();
        }
        editor.putFloat(key, value).apply();
    }


    public static boolean saveDouble(String key, long value) {
        if (preferences == null) {
            throw new NullPointerException("have not init...");
        }
        if (editor == null) {
            editor = preferences.edit();
        }
        return editor.putLong(key, value).commit();
    }


    public static void saveDoubleN(String key, long value) {
        if (preferences == null) {
            throw new NullPointerException("have not init...");
        }
        if (editor == null) {
            editor = preferences.edit();
        }
        editor.putLong(key, value).apply();
    }


    public static boolean saveBool(String key, boolean value) {
        if (preferences == null) {
            throw new NullPointerException("have not init...");
        }
        if (editor == null) {
            editor = preferences.edit();
        }
        return editor.putBoolean(key, value).commit();
    }


    public static void saveBoolN(String key, boolean value) {
        if (preferences == null) {
            throw new NullPointerException("have not init...");
        }
        if (editor == null) {
            editor = preferences.edit();
        }
        editor.putBoolean(key, value).apply();
    }


    public static String getString(String key, String defaultValue) {
        if (preferences == null) {
            throw new NullPointerException("have not init...");
        }
        return preferences.getString(key, defaultValue);
    }


    public static int getInt(String key, int defaultValue) {
        if (preferences == null) {
            throw new NullPointerException("have not init ...");
        }
        return preferences.getInt(key, defaultValue);
    }


    public static float getFloat(String key, float defaultValue) {
        if (preferences == null) {
            throw new NullPointerException("have not init ...");
        }
        return preferences.getFloat(key, defaultValue);
    }


    public static long getLong(String key, long defaultValue) {
        if (preferences == null) {
            throw new NullPointerException("have not init ...");
        }
        return preferences.getLong(key, defaultValue);
    }


    public static boolean getBool(String key, boolean defaultValue) {
        if (preferences == null) {
            throw new NullPointerException("have not init ...");
        }
        return preferences.getBoolean(key, defaultValue);
    }


    public static Map<String, ?> getAll() {
        if (preferences == null) {
            throw new NullPointerException("have not init ...");
        }
        return preferences.getAll();
    }


}
