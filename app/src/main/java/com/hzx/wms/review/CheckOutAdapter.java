package com.hzx.wms.review;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzx.wms.R;
import com.hzx.wms.bean.TaskListBean;

import java.util.List;


/**
 * @author  qinl
 * @date  2019/6/10
*/

public class CheckOutAdapter extends BaseQuickAdapter<TaskListBean, BaseViewHolder> {
    public CheckOutAdapter(int layoutResId, @Nullable List<TaskListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskListBean item) {
        helper.setText(R.id.text_out_code_value, item.getOut_code());
        helper.setText(R.id.text_out_id_value, item.getOut_id());
        helper.setText(R.id.text_business_value, item.getBusiness().getName());
        //helper.addOnClickListener(R.id.text_get);
        helper.getView(R.id.text_get).setVisibility(View.GONE);
    }
}
