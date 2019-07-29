package com.hzx.wms.stocktaking;

import android.os.Bundle;

import com.hzx.wms.R;
import com.hzx.wms.app.BaseActivity;
import com.vondear.rxtool.view.RxToast;

/**
 * @author qinl
 * @date 2019/7/16
 */

public class StocktakingDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocktaking_details);
        int id = getIntent().getExtras().getInt("id");
        RxToast.success(String.valueOf(id));
    }
}
