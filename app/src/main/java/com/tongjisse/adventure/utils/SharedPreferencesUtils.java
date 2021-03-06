package com.tongjisse.adventure.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesUtils {
    @SuppressLint("StaticFieldLeak")
    private static SharedPreferencesUtils spUtils = null;
    private Context context;
    private SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor editor = null;

    /**
     * SharedPreferencesUtils Referenced from Github
     *
     * @param context：Context 必须传入Context环境
     * @author ZhentingYan
     */

    private SharedPreferencesUtils(Context context) {
        this.context = context;
    }

    public static SharedPreferencesUtils getInstance(Context context) {
        if (spUtils == null) {
            spUtils = new SharedPreferencesUtils(context);
        }
        return spUtils;
    }

    private SharedPreferences getPreferenceInstance() {
        if (sharedPreferences == null) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return sharedPreferences;
    }

    private SharedPreferences.Editor getPreferenceEditor() {
        if (editor == null) {
            editor = getPreferenceInstance().edit();
        }
        return editor;
    }

    public String readString(String key, String alternative) {
        return getPreferenceInstance().getString(key, alternative);
    }

    public float readFloat(String key, float alternative) {
        return getPreferenceInstance().getFloat(key, alternative);
    }

    public long readLong(String key, long alternative) {
        return getPreferenceInstance().getLong(key, alternative);
    }

    public int readInteger(String key, int alternative) {
        return getPreferenceInstance().getInt(key, alternative);
    }

    public boolean readBoolean(String key, boolean alternative) {
        return getPreferenceInstance().getBoolean(key, alternative);
    }

    public void writeString(String key, String str) {
        SharedPreferences.Editor tempEditor = getPreferenceEditor();
        tempEditor.putString(key, str);
        tempEditor.apply();
    }

    public void writeFloat(String key, float flt) {
        SharedPreferences.Editor tempEditor = getPreferenceEditor();
        tempEditor.putFloat(key, flt);
        tempEditor.apply();
    }

    public void writeLong(String key, long lng) {
        SharedPreferences.Editor tempEditor = getPreferenceEditor();
        tempEditor.putLong(key, lng);
        tempEditor.apply();
    }

    public void writeInteger(String key, int integer) {
        SharedPreferences.Editor tempEditor = getPreferenceEditor();
        tempEditor.putInt(key, integer);
        tempEditor.apply();
    }

    public void writeBoolean(String key, boolean bln) {
        SharedPreferences.Editor tempEditor = getPreferenceEditor();
        tempEditor.putBoolean(key, bln);
        tempEditor.apply();
    }

    public void remove(String key) {
        SharedPreferences.Editor tempEditor = getPreferenceEditor();
        tempEditor.remove(key);
        tempEditor.apply();
    }

    public void clearAll() {
        SharedPreferences.Editor tempEditor = getPreferenceEditor();
        tempEditor.clear();
        tempEditor.apply();
    }


}