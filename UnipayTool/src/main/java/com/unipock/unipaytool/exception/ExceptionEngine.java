package com.unipock.unipaytool.exception;

import android.net.ParseException;
import android.util.MalformedJsonException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;


public class ExceptionEngine {

    private static final int ERROR_DATA_PARSING_EXCEPTION = 50001;
    private static final int ERROR_CONNECT_EXCEPTION = 50002;
    private static final int ERROR_UNKNOWN_HOST_EXCEPTION = 50003;
    private static final int ERROR_TIME_OUT_EXCEPTION = 50004;
    private static final int ERROR_UN_KNOWN = 50005;

    public static ApiException getApiException(Throwable e) {

        ApiException apiException;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            if (httpException.code() == 401) {
                apiException = new ApiException(httpException, httpException.code());
                //令牌驗證失敗！
                apiException.setMsg("您的賬號在另壹臺設備上登錄，請重新登錄！");
            } else if (httpException.code() == 400) {
                apiException = new ApiException(httpException, httpException.code());
                apiException.setMsg("賬號或密碼錯誤！");
            } else if (httpException.code() == 403) {
                apiException = new ApiException(httpException, httpException.code());
                apiException.setMsg("您的賬號在另壹臺設備上登錄，請重新登錄！");
            } else if (httpException.code() == 404) {
                apiException = new ApiException(httpException, httpException.code());
                apiException.setMsg("數據異常，請聯繫管理員");
            } else if (httpException.code() == 1001) {
                apiException = new ApiException(httpException, httpException.code());
                apiException.setMsg("網絡異常，請稍後再試");
            } else if (httpException.code() == 1002) {
                apiException = new ApiException(httpException, httpException.code());
                apiException.setMsg("網絡異常，請稍後再試");
            } else if (httpException.code() == 1003) {
                apiException = new ApiException(httpException, httpException.code());
                apiException.setMsg("數據不存在，請聯繫管理員");
            } else if (httpException.code() == 2001) {
                apiException = new ApiException(httpException, httpException.code());
//                apiException.setMsg("數據不存在，請聯繫管理員");
                apiException.setMsg("訂單不存在");
            } else if (httpException.code() == 2002) {
                //订单状态错误
                apiException = new ApiException(httpException, httpException.code());
//                apiException.setMsg("數據異常，請聯繫管理員");
                apiException.setMsg("該訂單已處理");
            } else {
                apiException = new ApiException(httpException, httpException.code());
                apiException.setMsg("數據異常，請聯繫管理員");
            }
            return apiException;
        } else if (e instanceof ServerException) {
            ServerException serverExc = (ServerException) e;
            apiException = new ApiException(serverExc, serverExc.getCode());
            apiException.setMsg(serverExc.getMsg());
            return apiException;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException || e instanceof MalformedJsonException) {
            apiException = new ApiException(e, ERROR_DATA_PARSING_EXCEPTION);
            apiException.setMsg("網絡異常，請稍後再試");
            return apiException;
        } else if (e instanceof ConnectException) {
            ConnectException connectExc = (ConnectException) e;
            apiException = new ApiException(connectExc, ERROR_CONNECT_EXCEPTION);
            apiException.setMsg("網絡連接超時，請稍後再試");
            return apiException;
        } else if (e instanceof UnknownHostException) {//网络异常
            apiException = new ApiException(e, ERROR_UNKNOWN_HOST_EXCEPTION);
            apiException.setMsg("網絡連接超時，請稍後再試");
            return apiException;
        } else if (e instanceof SocketTimeoutException) {//网络超时
            apiException = new ApiException(e, ERROR_TIME_OUT_EXCEPTION);
            apiException.setMsg("網絡連接超時，請稍後再試");
            return apiException;
        } else {
            apiException = new ApiException(e, ERROR_UN_KNOWN);
            apiException.setMsg("網絡連接超時，請稍後再試");
            return apiException;
        }

    }

}
