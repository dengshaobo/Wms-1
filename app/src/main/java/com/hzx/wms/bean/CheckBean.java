package com.hzx.wms.bean;

/**
 * @author qinl
 * @package com.hzx.wms.bean
 * @date 2019/7/16 11:00
 * @fileName CheckBean
 * @describe TODO
 */

public class CheckBean {

    /**
     * id : 1
     * inventory_no : 1563172463909
     * business_id : null
     * user_id : 1
     * check_user_id : 1
     * status : 0
     * created_at : 2019-07-15 14:34:23
     * updated_at : 2019-07-16 10:06:41
     * business : null
     * user : {"id":1,"name":"管理员"}
     * check_user : {"id":1,"name":"管理员"}
     */

    private int id;
    private String inventory_no;
    private String business_id;
    private int user_id;
    private int check_user_id;
    private String status;
    private String created_at;
    private String updated_at;
    private String business;
    private UserBean user;
    private CheckUserBean check_user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInventory_no() {
        return inventory_no;
    }

    public void setInventory_no(String inventory_no) {
        this.inventory_no = inventory_no;
    }

    public Object getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCheck_user_id() {
        return check_user_id;
    }

    public void setCheck_user_id(int check_user_id) {
        this.check_user_id = check_user_id;
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

    public Object getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public CheckUserBean getCheck_user() {
        return check_user;
    }

    public void setCheck_user(CheckUserBean check_user) {
        this.check_user = check_user;
    }

    public static class UserBean {
        /**
         * id : 1
         * name : 管理员
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class CheckUserBean {
        /**
         * id : 1
         * name : 管理员
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
