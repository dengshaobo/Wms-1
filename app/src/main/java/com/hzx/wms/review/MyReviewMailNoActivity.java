package com.hzx.wms.review;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzx.wms.R;
import com.hzx.wms.app.BaseActivity;
import com.hzx.wms.app.Constants;
import com.hzx.wms.utils.EditSearchAction;
import com.hzx.wms.utils.SoundPlayUtils;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.RxSPTool;
import com.vondear.rxtool.view.RxToast;


import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author qinl
 * @date 2019/7/3
 */
public class MyReviewMailNoActivity extends BaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.edt_warehouse_warehouseNum)
    EditText edtWarehouseWarehouseNum;
    @Bind(R.id.text_out_code)
    TextView textOutCode;
    @Bind(R.id.text_review_num)
    TextView textReviewNum;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review_mail_no);

        ButterKnife.bind(this);
        SoundPlayUtils.init(this);
        //软键盘回车搜索
        EditSearchAction action = new EditSearchAction();
        action.searchAction(this, edtWarehouseWarehouseNum);
        action.setListener(this::intentNext);

        id = getIntent().getStringExtra("id");
        String outCode = getIntent().getStringExtra("out_code");
        textOutCode.setText(String.format("预出库编号：%s", outCode));
    }

    @Override
    public void onResume() {
        super.onResume();
        int size = RxSPTool.getInt(MyReviewMailNoActivity.this, id);
        if (size == -1) {
            size = 0;
        }
        textReviewNum.setText(String.format(Locale.CHINA, "已复核：%d", size));
    }

    @Override
    public void intentNext(String message) {
        if (message == null) {
            SoundPlayUtils.play(8);
            return;
        }
        if (message.length() < Constants.WAREHOUSE_LENGTH) {
            RxToast.warning("扫描正确的单号", 4000);
            SoundPlayUtils.play(5);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("mailNo", message.trim());
        bundle.putString("id", id);
        RxActivityTool.skipActivity(this, MyReviewDetailsActivity.class, bundle);
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        RxSPTool.remove(MyReviewMailNoActivity.this, id);
        finish();
    }

}
