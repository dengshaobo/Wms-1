package com.hzx.wms.bean;



/**
 * @author qinl
 * @package qinl.com.hzxoderquery.bean
 * @date 2019/5/13 14:40
 * @fileName RuKuDetailsBean
 * @describe TODO
 */

public class RuKuDetailsBean {


    /**
     * num : 0
     * ware_location_id : 0
     * bar_code : string
     * maturity_date : string
     */
    private int num;
    private String ware_location;
    private String bar_code;
    private String maturity_date;

    public RuKuDetailsBean( int num, String ware_location, String bar_code,
            String maturity_date) {
        this.num = num;
        this.ware_location = ware_location;
        this.bar_code = bar_code;
        this.maturity_date = maturity_date;
    }

    public RuKuDetailsBean() {
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getWare_location() {
        return ware_location;
    }

    public void setWare_location(String ware_location) {
        this.ware_location = ware_location;
    }

    public String getBar_code() {
        return bar_code;
    }

    public void setBar_code(String bar_code) {
        this.bar_code = bar_code;
    }

    public String getMaturity_date() {
        return maturity_date;
    }

    public void setMaturity_date(String maturity_date) {
        this.maturity_date = maturity_date;
    }


    @Override
    public String toString() {
        return "RuKuDetailsBean{" +
                "num=" + num +
                ", ware_location=" + ware_location +
                ", bar_code='" + bar_code + '\'' +
                ", maturity_date='" + maturity_date + '\'' +
                '}';
    }
}
