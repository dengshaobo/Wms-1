package com.hzx.wms.check;

import android.os.Bundle;
import android.widget.ImageView;

import com.hzx.wms.R;
import com.hzx.wms.app.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author qinl
 * @package com.hzx.wms.check
 * @date 2019/7/22 14:37
 * @fileName CheckActivity
 * @describe TODO
 */

public class CheckActivity extends BaseActivity {
    @Bind(R.id.img_back)
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
