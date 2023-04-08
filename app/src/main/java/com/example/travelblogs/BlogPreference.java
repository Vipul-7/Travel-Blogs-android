package com.example.travelblogs;

import android.content.Context;
import android.content.SharedPreferences;

public class BlogPreference {
    private static final String KEY_LOGIN_STATE = "Key_login_state";

    private SharedPreferences preferences;

    BlogPreference(Context context){
        preferences = context.getSharedPreferences("travel-blog",Context.MODE_PRIVATE);
    }

    public boolean isLoggedIn(){
        return preferences.getBoolean(KEY_LOGIN_STATE,false);
    }

    public void setLoggedIn(boolean loggedIn){
        preferences.edit().putBoolean(KEY_LOGIN_STATE,loggedIn).apply();
    }
}
