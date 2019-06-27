package com.hzx.wms.http;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

/**
 * @author qinl
 * @package qinl.com.hzxoderquery.http
 * @date 2019/5/8 14:05
 * @fileName RetryConfig
 * @describe TODO
 */

public class RetryConfig {
    private static int DEFAULT_RETRY_TIMES = 1;
    private static int DEFAULT_DELAY_DURATION = 1000;
    private static Suppiler<Single<Boolean>> DEFAULT_FUNCTION = () -> Single.just(false);

    private int maxRetries;
    private int delay;

    private Suppiler<Single<Boolean>> retryCondition;

    public RetryConfig() {
        this(DEFAULT_RETRY_TIMES, DEFAULT_DELAY_DURATION, DEFAULT_FUNCTION);
    }

    public RetryConfig(int maxRetries) {
        this(maxRetries, DEFAULT_DELAY_DURATION, DEFAULT_FUNCTION);
    }

    public RetryConfig(int maxRetries,
                       int delay) {
        this(maxRetries, delay, DEFAULT_FUNCTION);
    }

    public RetryConfig(Suppiler<Single<Boolean>> retryCondition) {
        this(DEFAULT_RETRY_TIMES, DEFAULT_DELAY_DURATION, retryCondition);
    }

    public RetryConfig(int maxRetries,
                       int delay,
                       @NonNull Suppiler<Single<Boolean>> retryCondition) {
        if (retryCondition == null) {
            throw new NullPointerException("the parameter retryCondition can't be null.");
        }

        this.maxRetries = maxRetries;
        this.delay = delay;
        this.retryCondition = retryCondition;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public int getDelay() {
        return delay;
    }

    public Suppiler<Single<Boolean>> getRetryCondition() {
        return retryCondition;
    }
}
