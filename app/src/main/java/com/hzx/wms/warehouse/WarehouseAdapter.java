package com.hzx.wms.warehouse;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzx.wms.R;
import com.hzx.wms.bean.RuKuBean;

import java.util.List;

/**
 * Created by linhu on 2019/6/19.
 */

public class WarehouseAdapter extends BaseQuickAdapter<RuKuBean, BaseViewHolder> {
    public WarehouseAdapter(int layoutResId, @Nullable List<RuKuBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RuKuBean item) {
        helper.setText(R.id.in_code, item.getIn_code());
        helper.setText(R.id.main_order, item.getMain_order());
        if (item.getScan_user() != null) {
            helper.setText(R.id.scan_user, item.getScan_user().getName());
        }
    }
}
