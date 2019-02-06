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

    public static void setcustomer_id(Context context, String value)
    {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefsEditor = mPrefs.edit();
        prefsEditor.putString("customer_id", value);
        prefsEditor.commit();
    }
    public static String getcustomer_id(Context context)
    {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("customer_id", "");
    }
    public static void setemail(Context context, String value)
    {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefsEditor = mPrefs.edit();
        prefsEditor.putString("email", value);
        prefsEditor.commit();
    }
    public static String getemail(Context context)
    {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("email", "");
    }
    public static void setfullname(Context context, String value)
    {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefsEditor = mPrefs.edit();
        prefsEditor.putString("fullname", value);
        prefsEditor.commit();
    }
    public static String getfullname(Context context)
    {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("fullname", "");
    }

}
