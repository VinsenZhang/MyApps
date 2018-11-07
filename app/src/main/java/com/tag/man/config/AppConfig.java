package com.tag.man.config;

import android.os.Environment;


public class AppConfig {

    public static final boolean TEST = false;


    public static final String api_version = "1.0.0";

    
    //文件下载路径
    public static final String SD_ADS_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/.tag/";


}
