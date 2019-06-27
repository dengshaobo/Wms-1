package com.hzx.wms.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by linhu on 2019/6/24.
 */

public class SystemBarTintManager {
    private View mStatusBarTintView;

    public SystemBarTintManager(Activity activity) {
        ViewGroup decorViewGroup = (ViewGroup) activity.getWindow().getDecorView();//获取状态栏的View
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBarTintView = new View(activity);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT
                    , getInternalDimensionSize(activity.getResources()), Gravity.TOP);
            mStatusBarTintView.setLayoutParams(params);
            decorViewGroup.addView(mStatusBarTintView);
        }
    }

    /**
     * Apply the specified color tint to the system status bar.
     *
     * @param color The color of the background tint.
     */
    public void setStatusBarTintColor(int color) {
        mStatusBarTintView.setBackgroundColor(color);
    }
    /**
     * get system status bar height.
     *
     * @return height.
     */
    private int getInternalDimensionSize(Resources res) {
        int result = 0;
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
