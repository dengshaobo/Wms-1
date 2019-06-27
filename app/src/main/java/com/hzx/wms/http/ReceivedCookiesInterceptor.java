package com.hzx.wms.http;

import android.support.annotation.NonNull;

import com.vondear.rxtool.RxSPTool;
import com.vondear.rxtool.RxTool;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author qinl
 * @package qinl.com.hzxoderquery.http
 * @date 2019/5/6 14:48
 * @fileName ReceivedCookiesInterceptor
 * @describe TODO
 */

public class ReceivedCookiesInterceptor implements Interceptor {
    private static final String HEADER_COOKIE = "Authorization";

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers(HEADER_COOKIE).isEmpty()) {
            RxSPTool.putString(RxTool.getContext(), "token", String.valueOf(originalResponse.headers(HEADER_COOKIE)));
        }
        return originalResponse;
    }
}
