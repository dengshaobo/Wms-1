package com.hzx.wms.bean;

/**
 * @author qinl
 * @package qinl.com.hzxoderquery.bean
 * @date 2019/5/6 14:09
 * @fileName LoginBean
 * @describe TODO
 */

public class LoginBean {
    private String token;

    public LoginBean(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "token='" + token + '\'' +
                '}';
    }
}
