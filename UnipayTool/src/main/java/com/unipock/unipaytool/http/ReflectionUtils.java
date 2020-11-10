package com.unipock.unipaytool.http;

import android.annotation.SuppressLint;
import android.app.Application;

/**
 * 反射工具类
 */
public class ReflectionUtils {

    /**
     * 获取Application
     * @return
     */
    @SuppressLint("PrivateApi")
    public static Application getApplicationReflection() {
        try {
            return (Application) Class.forName("android.app.ActivityThread")
                    .getMethod("currentApplication").invoke(null, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
