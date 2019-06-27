package com.hzx.wms.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by linhu on 2019/6/25.
 */

@Entity
public class RepeatBean {
    private String barcode;

    @Generated(hash = 596351956)
    public RepeatBean(String barcode) {
        this.barcode = barcode;
    }

    @Generated(hash = 1927900496)
    public RepeatBean() {
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public String toString() {
        return "RepeatBean{" +
                "barcode='" + barcode + '\'' +
                '}';
    }
}
