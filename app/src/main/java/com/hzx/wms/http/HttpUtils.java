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
    //http://106.15.73.135:8017正式
    //http://192.168.1.7:8002测试


    private static final String BASE_URL = "http://106.15.73.135:8017/api/";
    private Retrofit retrofit;

    private static class SingletonHolder {
        private static final HttpUtils INSTANCE = new HttpUtils();
    }

    private HttpUtils() {
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
