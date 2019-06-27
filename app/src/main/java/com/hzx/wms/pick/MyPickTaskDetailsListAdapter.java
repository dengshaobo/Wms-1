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
 * @date 2019/5/29 14:36
 * @fileName MyPickTaskDetailsListAdapter
 * @describe TODO
 */

public class MyPickTaskDetailsListAdapter extends BaseQuickAdapter<TaskListBean.NumListBean, BaseViewHolder> {

    public MyPickTaskDetailsListAdapter(int layoutResId, @Nullable List<TaskListBean.NumListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskListBean.NumListBean item) {
        helper.setText(R.id.text_barcode_value, item.getBar_code());
        helper.setText(R.id.text_num_value, String.valueOf(item.getNum()));
        helper.setText(R.id.text_picked_value,String.valueOf(item.getPick_num()));
        helper.setText(R.id.text_location_value,item.getWare_location());
    }
}
