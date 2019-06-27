package com.hzx.wms.review;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzx.wms.R;
import com.hzx.wms.bean.CheckBean;

import java.util.List;

/**
 * @author qinl
 * @package qinl.com.hzxoderquery.activity.task
 * @date 2019/6/12 9:24
 * @fileName MineCheckTaskDetailsAdapter
 * @describe TODO
 */

public class MineCheckTaskDetailsAdapter extends BaseQuickAdapter<CheckBean, BaseViewHolder> {
    public MineCheckTaskDetailsAdapter(int layoutResId, @Nullable List<CheckBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckBean item) {
        helper.setText(R.id.text_code, "条码：" + item.getBar_code());
        helper.setText(R.id.text_name, "品名：" +item.getNormal_ware().getName());
        helper.setText(R.id.text_num, "数量：" + String.valueOf(item.getNum()));
        helper.setText(R.id.text_check_num, "复核数量：" + String.valueOf(item.getConfirm_num()));
    }
}
