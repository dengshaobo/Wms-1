package com.hzx.wms.bean;

/**
 * @author qinl
 * @package qinl.com.hzxoderquery.bean
 * @date 2019/5/9 13:45
 * @fileName WhoBean
 * @describe TODO
 */

public class WhoBean {

    /**
     * user : {"id":1,"name":"管理员","account":"account","num":"CS0001","top_storage_id":null,"status":"0","created_at":"2019-05-05 10:29:21","updated_at":"2019-05-05 10:29:21"}
     */

    private UserBean user;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * id : 1
         * name : 管理员
         * account : account
         * num : CS0001
         * top_storage_id : null
         * status : 0
         * created_at : 2019-05-05 10:29:21
         * updated_at : 2019-05-05 10:29:21
         */

        private String id;
        private String name;
        private String account;
        private String num;
        private String top_storage_id;
        private String status;
        private String created_at;
        private String updated_at;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getTop_storage_id() {
            return top_storage_id;
        }

        public void setTop_storage_id(String top_storage_id) {
            this.top_storage_id = top_storage_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}
