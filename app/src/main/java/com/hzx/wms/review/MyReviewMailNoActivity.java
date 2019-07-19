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
    int id;
    String out_code;
    @Bind(R.id.text_out_code)
    TextView textOutCode;
    @Bind(R.id.text_review_num)
    TextView textReviewNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review_mail_no);
        ButterKnife.bind(this);
        //软键盘回车搜索
        EditSearchAction action = new EditSearchAction();
        action.searchAction(this, edtWarehouseWarehouseNum);
        action.setListener(this::intentNext);

        id = getIntent().getIntExtra("id", 0);
        out_code = getIntent().getStringExtra("out_code");

        textOutCode.setText(String.format("预出库编号：%s", out_code));
        int size = RxSPTool.getInt(MyReviewMailNoActivity.this, String.valueOf(id));
        textReviewNum.setText(String.format(Locale.CHINA,"已复核数量：%d", size));
        SoundPlayUtils.init(this);
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
        bundle.putInt("id", id);
        RxActivityTool.skipActivity(this, MyReviewDetailsActivity.class, bundle);
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
