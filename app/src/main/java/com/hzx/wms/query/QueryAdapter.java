package com.hzx.wms.query;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzx.wms.R;
import com.hzx.wms.bean.QueryBean;

import java.util.List;

/**
 * @author qinl
 * @package com.hzx.wms.query
 * @date 2019/7/8 16:01
 * @fileName QueryAdapter
 * @describe TODO
 */

public class QueryAdapter extends BaseQuickAdapter<QueryBean, BaseViewHolder> {
    public QueryAdapter(int layoutResId, @Nullable List<QueryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QueryBean item) {
        helper.setText(R.id.text_business, "商家：" + item.getBusiness().getName());
        helper.setText(R.id.text_name, "品名：" + item.getName());
        helper.setText(R.id.text_barcode, "条码：" + item.getBar_code());
        helper.setText(R.id.text_warehouse, "货位：" + item.getWare_location().getName());
        helper.setText(R.id.text_num, "数量：" + item.getNum());
        helper.setText(R.id.text_date, "效期：" + item.getMaturity_date());
    }
}
