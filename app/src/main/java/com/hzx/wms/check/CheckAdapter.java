package com.hzx.wms.check;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzx.wms.R;
import com.hzx.wms.bean.CheckBean;


import java.util.List;

/**
 * @author qinl
 * @package com.hzx.wms.check
 * @date 2019/7/16 11:07
 * @fileName CheckAdapter
 * @describe TODO
 */

public class CheckAdapter extends BaseQuickAdapter<CheckBean, BaseViewHolder> {
    public CheckAdapter(int layoutResId, @Nullable List<CheckBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckBean item) {
        helper.setText(R.id.check_no, "盘点单号：" + item.getInventory_no());
    }
}
