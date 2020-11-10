package com.unipock.unipaytool.http;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.unipock.unipaytool.exception.ApiException;
import com.unipock.unipaytool.exception.ExceptionEngine;
import com.unipock.unipaytool.exception.ServerException;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;


public class BaseHttpSubscriber<M> implements Subscriber<HttpResponse<M>> {

    ApiException apiException;
    private MutableLiveData<HttpResponse<M>> data;

    public BaseHttpSubscriber(MutableLiveData<HttpResponse<M>> data) {
        this.data = data;
    }

    public LiveData<HttpResponse<M>> get() {
        return data;
    }

    private void set(HttpResponse<M> data) {
        this.data.setValue(data);
    }

    private void onFinish(HttpResponse<M> m) {
        set(m);
    }

    @Override
    public void onSubscribe(Subscription s) {
        s.request(1);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onNext(HttpResponse<M> mHttpResponse) {

        int code = mHttpResponse.getCode();
        if (code == 0) {
            onFinish(mHttpResponse);
        } else {
            ServerException serverException = new ServerException(mHttpResponse.getCode(), mHttpResponse.getMsg());
            apiException = ExceptionEngine.getApiException(serverException);
            getErrorData(apiException);
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e("BaseHttpSubscriber", e.getMessage());
        apiException = ExceptionEngine.getApiException(e);

//        if (apiException.getCode() == 401) {
//            // 退出到登录页面
//            // 通过反射获取全局Application
//            Application applicationReflection = ReflectionUtils.getApplicationReflection();
//            Intent intent = new Intent();
//            assert applicationReflection != null;
//            intent.setClass(applicationReflection, LoginActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            applicationReflection.startActivity(intent);
//            ActivityManagers.getActivityManager(applicationReflection).exit();
//            ToastUtils.showShort(applicationReflection, "您的賬號在另壹臺設備上登錄，請重新登錄！");
//            return;
//        }

        getErrorData(apiException);
    }

    private void getErrorData(ApiException apiException) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setCode(apiException.getCode());
        httpResponse.setMsg(apiException.getMsg());
        onFinish(httpResponse);
    }

}
