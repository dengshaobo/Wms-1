package com.hzx.wms.http;

import com.vondear.rxtool.RxSPTool;
import com.vondear.rxtool.RxTool;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author qinl
 * @package qinl.com.hzxoderquery.http
 * @date 2019/5/7 10:37
 * @fileName AddCookiesInterceptor
 * @describe TODO
 */

public class AddCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        String token = RxSPTool.getString(RxTool.getContext(), "token");
        builder.addHeader("Authorization", token);
        builder.addHeader("Accept","application/json, text/javascript, */*; q=0.01");
      //  builder.addHeader("X-XSRF-TOKEN", "eyJpdiI6ImI2ZWZQSFgrK1BQVTlzNEgzY3VzWVE9PSIsInZhbHVlIjoiZGxwWXpvZHNxNEx4dkN5OXhZZnVyTkJRczJjeXpmS3BFSW41czlWMVVWSXEraDZzVmFLQUhLaDhZNVlITnlLcyIsIm1hYyI6IjA3NWRjMDY3ZTk1ZmYwYTY4ZWE0NmJkYTQ5ZTRkYWY0ZGQ3YTFkMmMxNTU2MmQzYmI2ZmZjYTYyOTUyM2YwZGMifQ==");
      // builder.addHeader("Cookie", "XSRF-TOKEN=eyJpdiI6IittYlRkckMyS1lDSXdEQW03NjE1emc9PSIsInZhbHVlIjoiRVlmbjM1enpUUG5xMTNmXC9iT01pUE9UMkUxWUJhTGRKRnl4SVFwYkZ2Vmg4TEF6d05QV0UyaDFBcDg5eldaaTciLCJtYWMiOiI0MjliNzgyZTAyY2I0ZWZlNGJjMWZmODVlOWI2YTc4M2M0N2ZmZGU3ODY3ZDk2YzliMzU2NGEzMDZhMDQwYzM1In0%3D; laravel_session=eyJpdiI6IkQ3WEJZRG1yZDA4T2RYTUpmOW53VWc9PSIsInZhbHVlIjoiREdGU0xVbExFTmNhVVd6blluNzA0a3EwS3M5RjlydzYxNytmNk9BMDZ1eVA5UEFCYUFtbFBoMVUzT3FydmMxUiIsIm1hYyI6ImE0MDU5MGIwOGY0OWMxYjFhYjM4MzE2ZDE2ZTk2ZjAzZGNjMDJkMjI0MWVmNWI0ODk1NjcyOTQ2ZThjYWFhM2QifQ%3D%3D");
        return chain.proceed(builder.build());
    }
}
