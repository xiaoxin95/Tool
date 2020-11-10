package com.unipock.unipaytool.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {

    public static String SP_NAME = "sp_config";
    private static SharedPreferences sp;
    Context context;

    SPUtils(Context context) {
        super();
        this.context = context;
    }

    /**
     * 存储
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveString(Context context, String key, String value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, 0);
        }
        sp.edit().putString(key, value).commit();
    }

    public static void removeString(Context context, String key) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, 0);
        }
        sp.edit().remove(key).commit();
    }

    /**
     * 存储字符串数组
     *
     * @param context
     * @param key
     * @param values
     */
    public static void setSharedPreference(Context context, String key, String[] values) {
        String regularEx = "#";
        String str = "";
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        if (values != null && values.length > 0) {
            for (String value : values) {
                str += value;
                str += regularEx;
            }
            SharedPreferences.Editor et = sp.edit();
            et.putString(key, str);
            et.commit();
        }
    }

    /**
     * 获取字符串数组
     *
     * @param context
     * @param key
     * @return
     */
    public static String[] getSharedPreference(Context context, String key) {
        String regularEx = "#";
        String[] str = null;
        sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        String values;
        values = sp.getString(key, "");
        str = values.split(regularEx);

        return str;
    }

    // 得到SharePreference
    public static String getString(Context context, String key, String defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, 0);
        }
        return sp.getString(key, defValue);
    }

    // commit
    public static void saveBoolean(Context context, String key, boolean value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, 0);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key,
                                     boolean defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, 0);
        }
        return sp.getBoolean(key, defValue);
    }

}

