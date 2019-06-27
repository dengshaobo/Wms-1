package com.hzx.wms.review;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.hzx.wms.R;
import com.hzx.wms.app.BaseActivity;
import com.hzx.wms.utils.EditSearchAction;
import com.hzx.wms.utils.SoundPlayUtils;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.view.RxToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyReviewMailNoActivity extends BaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.edt_warehouse_warehouseNum)
    EditText edtWarehouseWarehouseNum;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review_mail_no);
        ButterKnife.bind(this);
        //软键盘回车搜索
        EditSearchAction action = new EditSearchAction();
        action.searchAction(this, edtWarehouseWarehouseNum);
        action.setListener(this::intentNext);

        SoundPlayUtils.init(this);
    }

    @Override
    public void intentNext(String message) {
        if(message.length()<9){
            RxToast.warning("扫描正确的单号",4000);
            SoundPlayUtils.play(5);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("mailNo", message.trim().toString());
        RxActivityTool.skipActivity(this, MyReviewDetailsActivity.class, bundle);
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
