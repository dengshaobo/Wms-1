package com.hzx.wms.check;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hzx.wms.R;
import com.hzx.wms.app.BaseActivity;
import com.vondear.rxtool.view.RxToast;

/**
 * @author qinl
 * @date 2019/7/16
 */

public class CheckDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_details);
        int id = getIntent().getExtras().getInt("id");
        RxToast.success(String.valueOf(id));
    }
}
