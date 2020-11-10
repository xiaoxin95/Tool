package com.unipock.unipaytool.utils;

import android.content.Context;
import android.widget.Toast;

import com.unipock.unipaytool.BuildConfig;


/**
 * ToastUtils 封装
 */
public class ToastUtils {

    private static Toast toast;

    /**
     * 短时间显示  Toast
     *
     * @param context
     * @param sequence
     */
    public static void showShort(Context context, CharSequence sequence) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        toast.setText(sequence);
        toast.show();
    }

    /**
     * 短时间显示  Toast
     * 调试模式弹框显示
     *
     * @param context
     * @param sequence
     */
    public static void showShortDebug(Context context, CharSequence sequence) {
        if (BuildConfig.DEBUG) {
            LogUtils.e(context.getPackageName(),sequence.toString());
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
            toast.setText(sequence);
            toast.show();
        }
    }

    /**
     * 隐藏toast
     */
    public static void hideToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

}
