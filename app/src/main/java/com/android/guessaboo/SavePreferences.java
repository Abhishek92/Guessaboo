package com.android.guessaboo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ranosys-abhi1 on 3/7/15.
 */
public class SavePreferences {

    private Context context;
    private final String user_pref = "Guessaboo";
    private static SharedPreferences shared_preferences;
    private static final int MODE_PRIVATE = 0;

    public SavePreferences(Context context) {
        shared_preferences = context.getSharedPreferences(user_pref, MODE_PRIVATE);
    }

    public void setIsLoggedIn(boolean flag)
    {
        SharedPreferences.Editor editor = shared_preferences.edit();
        editor.putBoolean("isLogged", flag);
        editor.commit();
    }

    public boolean isLoggedIn()
    {
        return shared_preferences.getBoolean("isLogged", false);
    }

    public void setUserId(String userId)
    {
        SharedPreferences.Editor editor = shared_preferences.edit();
        editor.putString("userId", userId);
        editor.commit();
    }

    public String getUserId()
    {
        String userId = "";
        userId = shared_preferences.getString("userId", "0");
        return userId;
    }



    public void setUserName(String userName)
    {
        SharedPreferences.Editor editor = shared_preferences.edit();
        editor.putString("userName", userName);
        editor.commit();
    }

    public String getUserName()
    {
        String userName = "";
        userName = shared_preferences.getString("userName", "");
        return userName;
    }

    public void setEmail(String email)
    {
        SharedPreferences.Editor editor = shared_preferences.edit();
        editor.putString("email", email);
        editor.commit();
    }

    public String getEmail()
    {
        String email = "";
        email = shared_preferences.getString("email", "");
        return email;
    }

    public void clearPrefs()
    {
        shared_preferences.edit().clear().commit();
    }
}
