package com.hzx.wms.pick;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzx.wms.R;
import com.hzx.wms.bean.TaskListBean;

import java.util.List;


/**
 * @author qinl
 * @package qinl.com.hzxoderquery.activity.task
 * @date 2019/5/29 9:21
 * @fileName TaskListAdapter
 * @describe TODO
 */

public class TaskListAdapter extends BaseQuickAdapter<TaskListBean, BaseViewHolder> {
    public TaskListAdapter(int layoutResId, @Nullable List<TaskListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskListBean item) {
        helper.setText(R.id.text_out_code_value, item.getOut_code());
        helper.setText(R.id.text_out_id_value, item.getOut_id());
        helper.setText(R.id.text_business_value, item.getBusiness().getName());
        helper.addOnClickListener(R.id.text_get);
    }
}