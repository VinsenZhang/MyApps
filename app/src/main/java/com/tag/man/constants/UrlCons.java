package com.tag.man.constants;

import com.tag.man.config.AppConfig;

/**
 * Created by vinse on 2018/9/13.
 */

public class UrlCons {

    public static final String DOMAIN = AppConfig.TEST ? "" :
            "";



    public static final String API_URL = "/user_api/v" + AppConfig.api_version;



}
