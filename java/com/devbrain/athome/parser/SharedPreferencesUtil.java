package com.devbrain.athome.parser;

import android.content.Context;
import android.content.SharedPreferences;

import com.devbrain.athome.modal.MGrocery;


/**
 * Created by Mukesh Jha on 9/3/2015.
 */
public class SharedPreferencesUtil
{
    public enum KEY
    {
        FIRST_TIME_USER("first_time"),
        REGISTERED_USER("registered"),
        SKIPPED_REGISTRATION("is_skipped_registration"),
        DEVICE_INFO("device_information"),
        DEVICE_LATITUDE("device_latitude"),
        DEVICE_LONGITUDE("device_longitude"),
        ONLINE_APPS("online_apps");

        private final String value;

        KEY(String value)
        {
           this.value = value;
        }

        public String getValue()
        {
            return this.value;
        }
    }

    private static SharedPreferencesUtil sharedPreferencesUtil = null;
    private static SharedPreferences sharedPreferences = null;

    private SharedPreferencesUtil()
    {
        sharedPreferences = MGrocery.getInstance().getActivity().getSharedPreferences("mobi_pref", Context.MODE_PRIVATE);
    }

    public static synchronized SharedPreferencesUtil getInstance()
    {
        if(sharedPreferencesUtil == null)
        {
            sharedPreferencesUtil = new SharedPreferencesUtil();
        }

        return sharedPreferencesUtil;
    }

    public boolean hasKey(KEY key)
    {
        boolean isKeyExist = false;

        /*if(sharedPreferences.contains(key.getValue()) && !TextUtils.isEmpty(sharedPreferences.getString(key.getValue(), "")))
        {
            isKeyExist = true;
        }*/

        if(sharedPreferences.contains(key.getValue()))
        {
            isKeyExist = true;
        }

        return isKeyExist;
    }

    public boolean saveString(KEY key, String value)
    {
        return sharedPreferences.edit().putString(key.getValue(), value).commit();
    }

    public boolean saveInt(KEY key, int value)
    {
        return sharedPreferences.edit().putInt(key.getValue(), value).commit();
    }

    public boolean saveBoolean(KEY key, boolean value)
    {
        return sharedPreferences.edit().putBoolean(key.getValue(), value).commit();
    }

    public boolean saveFloat(KEY key, float value)
    {
        return sharedPreferences.edit().putFloat(key.getValue(), value).commit();
    }

    public boolean saveDataLong(KEY key, long value)
    {
        return sharedPreferences.edit().putLong(key.getValue(), value).commit();
    }

    /*public boolean saveDataStringSetInSharedPreference(String key, Set<String> value)
    {
        return sharedPreferences.edit().putStringSet(key, value).commit();
    }*/

    public String getString(KEY key)
    {
        if(sharedPreferences.contains(key.getValue()))
        {
            return sharedPreferences.getString(key.getValue(), "");
        }
        else
        {
            return null;
        }
    }

    public boolean getBoolean(KEY key)
    {
        if(sharedPreferences.contains(key.getValue()))
        {
            return sharedPreferences.getBoolean(key.getValue(), false);
        }
        else
        {
            return false;
        }
    }

    public float getFloat(KEY key)
    {
        if(sharedPreferences.contains(key.getValue()))
        {
            return sharedPreferences.getFloat(key.getValue(), 0.0f);
        }
        else
        {
            return 0.0f;
        }
    }

    public float getLong(KEY key)
    {
        if(sharedPreferences.contains(key.getValue()))
        {
            return sharedPreferences.getLong(key.getValue(), 0L);
        }
        else
        {
            return 0L;
        }
    }

    public boolean removeKey(KEY key)
    {
        boolean isDeleted = false;
        if(sharedPreferences.contains(key.getValue()))
        {
            isDeleted = sharedPreferences.edit().remove(key.getValue()).commit();
        }

        return isDeleted;
    }
}
