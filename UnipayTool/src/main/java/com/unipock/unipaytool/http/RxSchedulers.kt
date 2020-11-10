package com.unipock.easydrop.http

import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 线程调度，访问数据放到 io ，然后到 android 主线程（mainThread）
 * @author Administrator
 */

object RxSchedulers {

    @JvmStatic
    fun <T> observableToMain():ObservableTransformer<T, T> {
        return ObservableTransformer{
            upstream ->
            upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    @JvmStatic
    fun <T> flowableToMain(): FlowableTransformer<T, T> {
        return FlowableTransformer{
            upstream ->
            upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

}
