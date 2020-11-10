package com.unipock.unipaytool.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;


/**
 * @author Admin
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    public Activity activity;
    public Context context;
    public String TAG;



    ActivityManagers activityManagers = ActivityManagers.getActivityManager(this);

    public <V extends View> V getView(Activity activity, int viewId) {
        return (V) activity.findViewById(viewId);
    }

    public <T extends View> T getView(View view, int viewId) {
        return (T) view.findViewById(viewId);
    }

    public abstract void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState!=null){
//            Intent intent=new Intent(this, LaunchActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
            return;
        }

        ImmersionBar.with(this)
                .hideBar(BarHide.FLAG_SHOW_BAR)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true)
                .init();

        activityManagers.putActivity(this);
        TAG = getLocalClassName();

        activity = this;
        context = this;

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityManagers.removeActivity(this);
    }

    /**
     * 退出所有 aty
     */
    public void exit() {
        activityManagers.exit();
    }

}
