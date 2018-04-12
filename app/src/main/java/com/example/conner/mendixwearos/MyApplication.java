package com.example.conner.mendixwearos;

import android.app.Application;

/**
 * Created by Conner on 4/11/18.
 */

public class MyApplication extends Application {
    private String baseUrl = "http://10.104.105.6:8080/rest/survey/v1";
    private String userName = "demo_user";

    public String getBaseUrl(){
        return this.baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
