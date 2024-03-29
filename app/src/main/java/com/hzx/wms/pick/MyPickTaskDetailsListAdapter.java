package com.hzx.wms.pick;

import android.graphics.Color;
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
        helper.setText(R.id.text_picked_value, String.valueOf(item.getPick_num()));
        helper.setText(R.id.text_location_value, item.getWare_location());
        helper.setText(R.id.text_name, String.format("品名：%s", item.getName()));
        if (item.getPick_num() != 0) {
            helper.setTextColor(R.id.text_barcode, Color.RED);
            helper.setTextColor(R.id.text_num, Color.RED);
            helper.setTextColor(R.id.text_picked, Color.RED);
            helper.setTextColor(R.id.text_location, Color.RED);
            helper.setTextColor(R.id.text_barcode_value, Color.RED);
            helper.setTextColor(R.id.text_num_value, Color.RED);
            helper.setTextColor(R.id.text_picked_value, Color.RED);
            helper.setTextColor(R.id.text_location_value, Color.RED);
            helper.setTextColor(R.id.text_name, Color.RED);
        } else {
            helper.setTextColor(R.id.text_barcode, Color.BLACK);
            helper.setTextColor(R.id.text_num, Color.BLACK);
            helper.setTextColor(R.id.text_picked, Color.BLACK);
            helper.setTextColor(R.id.text_location, Color.BLACK);
            helper.setTextColor(R.id.text_barcode_value, Color.BLACK);
            helper.setTextColor(R.id.text_num_value, Color.BLACK);
            helper.setTextColor(R.id.text_picked_value, Color.BLACK);
            helper.setTextColor(R.id.text_location_value, Color.BLACK);
            helper.setTextColor(R.id.text_name, Color.BLACK);
        }
    }
}
