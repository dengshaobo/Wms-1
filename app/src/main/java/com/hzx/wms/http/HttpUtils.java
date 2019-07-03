package com.hzx.wms.http;


import com.hzx.wms.BuildConfig;
import com.hzx.wms.app.MyApplication;
import com.vondear.rxtool.RxLogTool;
import com.vondear.rxtool.RxSPTool;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author qinl
 * @package com.hzx.wms.utils
 * @date 2019/4/8 14:56
 * @fileName HttpUtils
 * @describe TODO
 */

public class HttpUtils {

    // private static final String BASE_URL = " http://139.224.57.189:8017";
    //private String BASE_URL = "http://192.168.1.6:8002/api/";
    private String BASE_URL = "";
    private Retrofit retrofit;

    private static class SingletonHolder {
        private static final HttpUtils INSTANCE = new HttpUtils();
    }

    private HttpUtils() {
        BASE_URL = RxSPTool.getString(MyApplication.getContext(), "BaseUrl");
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.addInterceptor(new ReceivedCookiesInterceptor());
        builder.addInterceptor(new AddCookiesInterceptor());

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(RxLogTool::i);
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }


    public static HttpUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 这里返回一个泛型类，主要返回的是定义的接口类
     */
    public <T> T createService(Class<T> clazz) {
        return retrofit.create(clazz);
    }
}
