package com.unipock.unipaytool.callback;

import android.view.View;

public interface IOnItemClickListener {

    /**
     * Item 点击
     * @param view 视图
     * @param position 下标
     */
    void onItemClickListener(View view, int position);

    /**
     * Item 点击
     * @param view 视图
     * @param position 下标
     */
    void onItemClickListener(View view, int position, Object data);





}
