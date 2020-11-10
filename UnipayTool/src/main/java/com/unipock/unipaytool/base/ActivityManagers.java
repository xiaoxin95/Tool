package com.unipock.unipaytool.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 2017/9/30
 */
public class ActivityManagers {

    private Context context;
    private static ActivityManagers activityManagers;

    public static ActivityManagers getActivityManager(Context context) {
        if (activityManagers == null) {
            activityManagers = new ActivityManagers(context);
        }
        return activityManagers;
    }

    private ActivityManagers(Context context) {
        this.context = context;
    }

    /**
     * task map，用于记录activity栈，方便退出程序（这里为了不影响系统回收activity，所以用软引用）
     */
    private final HashMap<String, SoftReference<Activity>> taskMap = new HashMap<>();

    /**
     * 往应用task map加入activity
     */
    public final void putActivity(Activity atv) {
        taskMap.put(atv.toString(), new SoftReference<Activity>(atv));
    }

    /**
     * 移除
     * 往应用task map加入activity
     */
    public final void removeActivity(Activity atv) {
        taskMap.remove(atv.toString());
    }

    /**
     * 清除应用的task栈，如果程序正常运行这会导致应用退回到桌面
     */
    public final void exit() {
        for (Iterator<Map.Entry<String, SoftReference<Activity>>> iterator = taskMap.entrySet().iterator(); iterator.hasNext(); ) {
            SoftReference<Activity> activityReference = iterator.next().getValue();
            Activity activity = activityReference.get();
            if (activity != null) {
                activity.finish();
            }
        }
        taskMap.clear();
    }

    /**
     * 指定删除activity
     * @param cls 类名
     */
    public final void finishAty(Class<?> cls){
        for (Iterator<Map.Entry<String, SoftReference<Activity>>> iterator = taskMap.entrySet().iterator(); iterator.hasNext(); ) {
            SoftReference<Activity> activityReference = iterator.next().getValue();
            Activity activity = activityReference.get();
            if (activity != null&&activity.getClass().equals(cls)) {
                this.taskMap.remove(activity);
                activity.finish();
                break;
            }
        }
    }

    /**
     * 判断某个Activity 界面是否在前台
     * @param context
     * @param className 某个界面名称
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static boolean  isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断某activity是否处于栈顶
     * @param cls
     * @param context
     * @return true在栈顶 false不在栈顶
     */
    public static boolean isActivityTop(Class cls, Context context){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String name = manager.getRunningTasks(1).get(0).topActivity.getClassName();
        return name.equals(cls.getName());
    }





}
