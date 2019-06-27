package com.hzx.wms.warehouse;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzx.wms.R;
import com.hzx.wms.bean.DifferentBean;

import java.util.List;

/**
 * Created by linhu on 2019/6/20.
 */

public class WarehouseDifferentAdapter extends BaseQuickAdapter<DifferentBean, BaseViewHolder> {
    public WarehouseDifferentAdapter(int layoutResId, @Nullable List<DifferentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DifferentBean item) {
        helper.setText(R.id.text_name, "品名:" + item.getWare().getName());
        helper.setText(R.id.text_barcode, "条码:" + item.getWare().getBar_code());
        helper.setText(R.id.text_num, "实上:" + item.getNum());
        helper.setText(R.id.text_warehouse_num, "已上:" + item.getScan_num());
        if (!item.getScan_num().equals("0")) {
            helper.setTextColor(R.id.text_name, Color.RED);
            helper.setTextColor(R.id.text_barcode, Color.RED);
            helper.setTextColor(R.id.text_num, Color.RED);
            helper.setTextColor(R.id.text_warehouse_num, Color.RED);
        } else {
            helper.setTextColor(R.id.text_name, Color.BLACK);
            helper.setTextColor(R.id.text_barcode, Color.BLACK);
            helper.setTextColor(R.id.text_num, Color.BLACK);
            helper.setTextColor(R.id.text_warehouse_num, Color.BLACK);
        }
    }
}