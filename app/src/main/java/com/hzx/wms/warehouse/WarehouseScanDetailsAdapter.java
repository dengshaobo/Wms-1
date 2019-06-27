package com.hzx.wms.warehouse;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzx.wms.R;
import com.hzx.wms.bean.RuKuDetailsBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by linhu on 2019/6/20.
 */

public class WarehouseScanDetailsAdapter extends BaseQuickAdapter<RuKuDetailsBean, BaseViewHolder> {

    public WarehouseScanDetailsAdapter(int layoutResId, @Nullable List<RuKuDetailsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RuKuDetailsBean item) {
        helper.setText(R.id.text_hw_value, "货位:" + item.getWare_location());
        helper.setText(R.id.text_code_value, "条码:" + item.getBar_code());
        helper.setText(R.id.text_san_num_value, "数量:" + String.valueOf(item.getNum()));
        String timeString = "";
        if (!"".equals(item.getMaturity_date())) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            long l = Long.valueOf(item.getMaturity_date()) * 1000;
            timeString = sdf.format(new Date(l));
        }
        if (timeString.equals("")) {
            helper.setText(R.id.text_date_value, "效期:无");
        } else {
            helper.setText(R.id.text_date_value, "效期:" + timeString);
        }

        helper.addOnClickListener(R.id.img_edit);
        helper.addOnClickListener(R.id.text_san_num_value);
    }
}

