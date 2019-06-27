package com.hzx.wms.bean;

/**
 * @author qinl
 * @package qinl.com.hzxoderquery.activity.task
 * @date 2019/5/29 10:02
 * @fileName TaskDistributionBean
 * @describe TODO
 */

public class TaskDistributionBean {


    /**
     * ids : 1,2,3
     * user_id : 1
     */

    private String ids;
    private String user_id;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "TaskDistributionBean{" +
                "ids='" + ids + '\'' +
                ", user_id=" + user_id +
                '}';
    }
}
