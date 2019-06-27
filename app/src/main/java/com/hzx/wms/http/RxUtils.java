package com.hzx.wms.http;

import android.support.v4.app.FragmentActivity;

import com.hzx.wms.bean.BaseBean;
import com.hzx.wms.bean.RuKuDetailsErrorBean;
import com.hzx.wms.http.exception.ConnectFailedAlertDialogException;
import com.hzx.wms.http.exception.NavigatorFragment;
import com.hzx.wms.http.exception.RxDialog;
import com.hzx.wms.http.exception.TokenExpiredException;
import com.hzx.wms.utils.GsonUtils;
import com.vondear.rxtool.RxLogTool;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.view.dialog.RxDialogSure;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * @author qinl
 * @package qinl.com.hzxoderquery.http
 * @date 2019/5/8 14:10
 * @fileName RxUtils
 * @describe TODO
 */

public class RxUtils {
    private static final String STATUS_UNAUTHORIZED = "2001";
    private static final int NOT_FOUND = 404;

    public static <T extends BaseBean> GlobalErrorTransformer<T> handleGlobalError(FragmentActivity activity) {
        return new GlobalErrorTransformer<>(
                // 通过onNext流中数据的状态进行操作
                Observable::just,
                // 通过onError中Throwable状态进行操作
                error -> {
                    if (error instanceof HttpException) {
                        HttpException httpException = (HttpException) error;
                        if (httpException.code() == NOT_FOUND) {
                            RxToast.warning("无法连接服务器（404）",3000);
                        } else {
                            ResponseBody responseBody = ((HttpException) error).response().errorBody();
                            String result = responseBody.string();
                            RxLogTool.e("result：" + result);
                            BaseBean baseBean = GsonUtils.GsonToBean(result, BaseBean.class);
                            if (baseBean.getMsg().startsWith("[") && baseBean.getMsg().endsWith("]")) {
                                RxDialogSure dialogSure = new RxDialogSure(activity);
                                dialogSure.getContentView().setText(GsonUtils.GsonToBean("{\"msg\":" + baseBean.getMsg() + "}", RuKuDetailsErrorBean.class).getMsg().toString());
                                dialogSure.getTitleView().setText("错误");
                                dialogSure.getSureView().setOnClickListener(v -> dialogSure.cancel());
                                dialogSure.show();
                            } else {
                                RxToast.warning(baseBean.getMsg(),5000);
                            }
                            if (baseBean.getCode().equals(STATUS_UNAUTHORIZED)) {
                                return Observable.error(new TokenExpiredException());
                            }
                        }
                        return Observable.error(error);
                    }
                    return Observable.error(error);
                },
                error -> {
                    if (error instanceof ConnectFailedAlertDialogException) {
                        return new RetryConfig(
                                () -> RxDialog.showErrorDialog(activity, "ConnectException")
                                        .flatMap(Single::just));
                    }
                    if (error instanceof TokenExpiredException) {
                        // 最多重试1次，延迟3000ms
                        return new RetryConfig(1, 3000,
                                () -> {
                                    RxToast.warning("Token失效，跳转到Login重新登录！",3000);
                                    return new NavigatorFragment()
                                            .startLoginForResult(activity)
                                            .flatMap(Single::just);
                                }
                        );
                    }
                    // 其它异常都不重试
                    return new RetryConfig();
                },
                throwable -> {
                    if (throwable instanceof JSONException) {
                        RxToast.warning("全局异常捕获-Json解析异常！",3000);
                    }
                    if (throwable instanceof ConnectException) {
                        RxToast.error("网络似乎有问题，稍后再试！",3000);
                    }
                    if (throwable instanceof SocketTimeoutException) {
                        RxToast.error("网络似乎有点慢，连接服务器超时！",3000);
                    }
                }
        );
    }
}
