package com.zennow.zennow;

/**
 * Created by GHOST on 10/08/2016.
 */
public class Config {
    public String path = "http://www.adtc-app.com/AndroidConnect/";
    public String url_login = path + "login.php";
    public String SuccessResponse="Success";
    public final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    public final String TYPE_AUTOCOMPLETE = "/autocomplete";
    public final String OUT_JSON = "/json";
    public final String API_KEY = "AIzaSyD5zTOZNZMIk9F4V_ksxJFMceiort-4hdU";
    public static Config instance = new Config();
    public static Config GetInstance() {
        return instance;
    }
}
