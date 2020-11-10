package com.unipock.unipaytool.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unipock.unipaytool.config.Config;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 2017/8/27
 * Retrofit 请求工具类
 *
 * @author Administrator
 */

public class RetrofitHelper {

    private static final String BASIC_URL = "basic";
    private static final String CODING_URL = "coding_api";
    private static RetrofitHelper instance = null;
    private Retrofit mRetrofit = null;

    public static RetrofitHelper getInstance() {
        if (instance == null) {
            synchronized (RetrofitHelper.class) {
                instance = new RetrofitHelper();
            }
        }
        return instance;
    }

    private RetrofitHelper() {

        // 错误重连拦截器
        RetryInterceptor retryInterceptor = new RetryInterceptor.Builder()
                .executionCount(2)
                .retryInterval(1000).build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(70, TimeUnit.SECONDS);//连接服务器超时
        client.readTimeout(70, TimeUnit.SECONDS);//读取服务器的数据超时
        client.writeTimeout(70, TimeUnit.SECONDS);//写入本地的数据超时
        client.followRedirects(false);
        client.addInterceptor(retryInterceptor); // 断开重连2次

        Interceptor tokenInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response oldResponse = chain.proceed(request);

//                Response response = null;
//                if (isTokenExpired(oldResponse)) {
//                    // 退出到登录页面
//                    // 通过反射获取全局Application
//                    Application applicationReflection = ReflectionUtils.getApplicationReflection();
//                    Intent intent = new Intent();
//                    assert applicationReflection != null;
//                    intent.setClass(applicationReflection, LoginActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    applicationReflection.startActivity(intent);
//                    ActivityManagers.getActivityManager(applicationReflection).exit();
//                    return null;
//                }


//                List<String> headerValues = request.headers("urlName");
//                if (headerValues != null && headerValues.size() > 0) {
//                    String headerValue = headerValues.get(0);
//                    HttpUrl newBaseUrl;
//                    if (BASIC_URL.equals(headerValue)) {
//                        newBaseUrl = HttpUrl.parse(QianBaoTongConfig.BASE_URL);
//                    } else {
//                        newBaseUrl = request.url();
//                    }
//                    HttpUrl oldHttpUrl = request.url();  //从request中获取原有的HttpUrl实例oldHttpUrl
//                    HttpUrl newFullUrl = oldHttpUrl   //重建新的HttpUrl，修改需要修改的url部分
//                            .newBuilder()
//                            .scheme(newBaseUrl.scheme())
//                            .host(newBaseUrl.host())
//                            .port(newBaseUrl.port())
//                            .build();

//                }


                return oldResponse;

            }
        };
        client.addInterceptor(tokenInterceptor);
        mRetrofit = new Retrofit.Builder()
                .client(client.build())
                .baseUrl(Config.host)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build();

    }

    /**
     * 泛型
     *
     * @return
     */
    public EasyDropAPI EasyDropApiService() {
        return mRetrofit.create(EasyDropAPI.class);
    }

    /**
     * 根据Response，判断Token是否失效
     * 401表示token过期
     *
     * @param response
     * @return
     */
    private boolean isTokenExpired(Response response) {
        return response.code() == 401;
    }

//    private String getNewToken() throws IOException {
//        String username = SPUtils.getString(context, "username", "");
//        String password = SPUtils.getString(context, "password", "");
//        String deviceNo = DeviceUtils.INSTANCE.deviceNo(context);
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(QianBaoTongConfig.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
//        Map<String, String> maps = new HashMap<>();
//        maps.put("phone", username);
//        maps.put("password", password);
//        maps.put("deviceNo", deviceNo);
//        JSONObject result = new JSONObject(maps);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), result.toString());
//        retrofit2.Response<HttpResponse<Object>> tokenJson = retrofit.create(QianBaoTongApi.class).getUserRefreshToken(requestBody).execute();
//        if (tokenJson.body().getCode() == 0) {
//            String headerToken = (String) tokenJson.body().getValue();
//            SPUtils.saveString(context, "userToken", headerToken);
//            return headerToken;
//        } else {
//            return null;
//        }
//    }


}
