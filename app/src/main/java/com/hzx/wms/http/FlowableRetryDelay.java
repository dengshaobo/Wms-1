package com.hzx.wms.http;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @author qinl
 * @package qinl.com.hzxoderquery.http
 * @date 2019/5/8 14:08
 * @fileName FlowableRetryDelay
 * @describe TODO
 */

public class FlowableRetryDelay implements Function<Flowable<Throwable>, Publisher<?>> {

    private Function<Throwable, RetryConfig> provider;

    private int retryCount;

    public FlowableRetryDelay(Function<Throwable, RetryConfig> provider) {
        if (provider == null)
            throw new NullPointerException("The parameter provider can't be null!");
        this.provider = provider;
    }

    @Override
    public Publisher<?> apply(@NonNull Flowable<Throwable> throwableFlowable) throws Exception {
        return throwableFlowable
                .flatMap(new Function<Throwable, Publisher<?>>() {
                    @Override
                    public Publisher<?> apply(Throwable throwable) throws Exception {

                        RetryConfig retryConfig = provider.apply(throwable);
                        final long delay = retryConfig.getDelay();
                        final Throwable error = throwable;

                        if (++retryCount <= retryConfig.getMaxRetries()) {

                            return retryConfig
                                    .getRetryCondition()
                                    .call()
                                    .flatMapPublisher(new Function<Boolean, Publisher<?>>() {
                                        @Override
                                        public Publisher<?> apply(Boolean retry) throws Exception {
                                            if (retry) {
                                                return Flowable.timer(delay, TimeUnit.MILLISECONDS);
                                            } else {
                                                return Flowable.error(error);
                                            }
                                        }
                                    });
                        }
                        return Flowable.error(throwable);
                    }
                });
    }
}
