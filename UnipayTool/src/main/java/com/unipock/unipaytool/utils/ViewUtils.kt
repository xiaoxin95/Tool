package com.unipock.unipaytool.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.widget.LinearLayout
import com.google.android.material.tabs.TabLayout
import java.lang.reflect.Field

object ViewUtils {

    /**
     * 设置 tabLayout下划线指标长度
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    fun setIndicator(tabs: TabLayout, leftDip: Int, rightDip: Int) {
        val tabLayout = tabs.javaClass
        var tabStrip: Field? = null
        try {
            tabStrip = tabLayout.getDeclaredField("slidingTabIndicator")
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }

        tabStrip!!.isAccessible = true
        var llTab: LinearLayout? = null
        try {
            llTab = tabStrip.get(tabs) as LinearLayout
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        val left = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip.toFloat(), Resources.getSystem().displayMetrics).toInt()
        val right = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip.toFloat(), Resources.getSystem().displayMetrics).toInt()

        for (i in 0 until llTab!!.childCount) {
            val child = llTab.getChildAt(i)
            child.setPadding(0, 0, 0, 0)
            val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
            params.leftMargin = left
            params.rightMargin = right
            child.layoutParams = params
            child.invalidate()
        }

    }

    /**
     *  根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     *  根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }


//    fun getAndroidScreenHeight(context: Context): Int {
//        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        val dm = DisplayMetrics()
//        wm.defaultDisplay.getMetrics(dm)
//        val width = dm.widthPixels         // 屏幕宽度（像素）
//        val height = dm.heightPixels       // 屏幕高度（像素）
//        val density = dm.density         // 屏幕密度（0.75 / 1.0 / 1.5）
//        val densityDpi = dm.densityDpi     // 屏幕密度dpi（120 / 160 / 240）
//        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
//        val screenWidth = (width / density).toInt()  // 屏幕宽度(dp)
//        val screenHeight = (height / density).toInt()// 屏幕高度(dp)
//        return height
//    }

//    fun getAndroidScreenWidth(context: Context): Int {
//        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        val dm = DisplayMetrics()
//        wm.defaultDisplay.getMetrics(dm)
//        val width = dm.widthPixels         // 屏幕宽度（像素）
//        val height = dm.heightPixels       // 屏幕高度（像素）
//        val density = dm.density         // 屏幕密度（0.75 / 1.0 / 1.5）
//        val densityDpi = dm.densityDpi     // 屏幕密度dpi（120 / 160 / 240）
//        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
//        val screenWidth = (width / density).toInt()  // 屏幕宽度(dp)
//        val screenHeight = (height / density).toInt()// 屏幕高度(dp)
//        return height
//    }

    /**
     * 获取是否存在NavigationBar
     * @param context
     * @return
     */
    fun checkDeviceHasNavigationBar(context: Context): Boolean {
        var hasNavigationBar = false
        val rs = context.resources
        val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id)
        }
        try {
            val systemPropertiesClass = Class.forName("android.os.SystemProperties")
            val m = systemPropertiesClass.getMethod("get", String::class.java)
            val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
            if ("1" == navBarOverride) {
                hasNavigationBar = false
            } else if ("0" == navBarOverride) {
                hasNavigationBar = true
            }
        } catch (e: Exception) {

        }

        return hasNavigationBar
    }

//    /**
//     * 获取虚拟功能键高度
//     * @param context
//     * @return
//     */
//    fun getVirtualBarHeigh(context: Context): Int {
//        var vh = 0
//        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        val display = windowManager.defaultDisplay
//        val dm = DisplayMetrics()
//        try {
//            val c = Class.forName("android.view.Display")
//            val method = c.getMethod("getRealMetrics", DisplayMetrics::class.java)
//            method.invoke(display, dm)
//            vh = dm.heightPixels - windowManager.defaultDisplay.height
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        return vh
//    }


    /**
     * 获取状态栏高度
     */
    fun getStatusBarHeight(context: Context):Int{
        val resources = context.resources
        val identifier = resources.getIdentifier("status_bar_height", "dimen", "android");
        val height = resources.getDimensionPixelSize(identifier);
        return height
    }

}
