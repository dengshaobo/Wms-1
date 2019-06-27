package com.hzx.wms.bean;

import java.util.List;

/**
 * @author qinl
 * @package qinl.com.hzxoderquery.bean
 * @date 2019/5/17 14:26
 * @fileName RuKuDetailsErrorBean
 * @describe TODO
 */

public class RuKuDetailsErrorBean {


    private List<String> msg;

    public List<String> getMsg() {
        return msg;
    }

    public void setMsg(List<String> msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "RuKuDetailsErrorBean{" +
                "msg=" + msg +
                '}';
    }
}
