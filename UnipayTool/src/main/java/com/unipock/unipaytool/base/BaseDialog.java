package com.unipock.unipaytool.base;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

public abstract class BaseDialog extends Dialog {

    public Context context;

    public abstract View initView();

    public BaseDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        setContentView(initView());
    }

}
