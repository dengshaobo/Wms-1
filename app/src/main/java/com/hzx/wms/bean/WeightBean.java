package com.hzx.wms.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by linhu on 2019/6/24.
 */

@Entity
public class WeightBean {

    /**
     * logistics_no : 5530166980209
     * weight : 500
     */

    private String logistics_no;
    private String weight;

    @Generated(hash = 994822446)
    public WeightBean(String logistics_no, String weight) {
        this.logistics_no = logistics_no;
        this.weight = weight;
    }

    @Generated(hash = 1142872506)
    public WeightBean() {
    }

    public String getLogistics_no() {
        return logistics_no;
    }

    public void setLogistics_no(String logistics_no) {
        this.logistics_no = logistics_no;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "WeightBean{" +
                "logistics_no='" + logistics_no + '\'' +
                ", weight=" + weight +
                '}';
    }
}
