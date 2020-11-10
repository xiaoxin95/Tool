package com.unipock.unipaytool.utils

import android.util.Log

object LogUtils {
    @JvmStatic
    fun e(tag: String?, msg: String?) {
//        if (BuildConfig.DEBUG) {
        if (msg != null) {
            Log.e(tag, msg)
        }
//        }
    }

    @JvmStatic
    fun d(tag: String?, msg: String?) {
//        if (BuildConfig.DEBUG) {
        if (msg != null) {
            Log.d(tag, msg)
        }
//        }
    }
}