package com.sismatix.iheal.Preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Login_preference {
    public static SharedPreferences mPrefs;
    public static SharedPreferences.Editor prefsEditor;



    public static void setLogin_flag(Context context, String value)
    {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefsEditor = mPrefs.edit();
        prefsEditor.putString("login_flag", value);
        prefsEditor.commit();
    }
    public static String getLogin_flag(Context context)
    {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("login_flag", "");
    }
}
