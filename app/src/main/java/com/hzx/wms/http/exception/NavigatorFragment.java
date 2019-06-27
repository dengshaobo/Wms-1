package com.hzx.wms.http.exception;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.hzx.wms.login.LoginActivity;

import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;


/**
 * @author qinl
 * @package qinl.com.hzxoderquery.http.exception
 * @date 2019/5/8 14:26
 * @fileName NavigatorFragment
 * @describe TODO
 */

public class NavigatorFragment extends Fragment {

    private static final String TAG = "NavigatorFragment";

    private PublishSubject<Boolean> resultSubject;
    private PublishSubject<Boolean> cancelSubject;
    private PublishSubject<Boolean> attachSubject = PublishSubject.create();

    public Single<Boolean> startLoginForResult(FragmentActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(TAG);
        if (fragment == null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(this, TAG).commitAllowingStateLoss();
            return startLoginSingle();
        } else {
            return ((NavigatorFragment) fragment).startLoginSingle();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        attachSubject.onNext(true);
        attachSubject.onComplete();
    }

    public Single<Boolean> startLoginSingle() {
        resultSubject = PublishSubject.create();
        cancelSubject = PublishSubject.create();
        startLogin();
        return resultSubject
                .takeUntil(cancelSubject)
                .single(false);
    }

    @SuppressLint("CheckResult")
    public void startLogin() {
        if (!isAdded()) {
            attachSubject.subscribe(__ -> startLoginForResult());
        } else {
            startLoginForResult();
        }
    }

    private void startLoginForResult() {

        startActivity(new Intent(getContext(), LoginActivity.class));
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            boolean isLogin = data.getBooleanExtra(LoginActivity.EXTRA_SUCCESS, false);
//            resultSubject.onNext(isLogin);
//            resultSubject.onComplete();
//        } else {
//            cancelSubject.onNext(true);
//        }
//    }
}
