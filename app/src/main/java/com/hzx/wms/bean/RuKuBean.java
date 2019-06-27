package com.hzx.wms.bean;

/**
 * @author qinl
 * @package qinl.com.hzxoderquery.bean
 * @date 2019/5/7 15:42
 * @fileName RuKuBean
 * @describe TODO
 */

public class RuKuBean {


    /**
     * id : 5
     * in_code : 1557214633309
     * user_id : 1
     * scan_user_id : 1
     * main_order : 1
     * total_net_weight : 1
     * total_gross_weight : 1
     * status : 1
     * created_at : 2019-05-07 15:37:13
     * updated_at : 2019-05-07 16:11:56
     * user : {"id":1,"name":"管理员"}
     * scan_user : {"id":1,"name":"管理员"}
     */

    private int id;
    private String in_code;
    private int user_id;
    private int scan_user_id;
    private String main_order;
    private String total_net_weight;
    private String total_gross_weight;
    private int status;
    private String created_at;
    private String updated_at;
    private UserBean user;
    private ScanUserBean scan_user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIn_code() {
        return in_code;
    }

    public void setIn_code(String in_code) {
        this.in_code = in_code;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getScan_user_id() {
        return scan_user_id;
    }

    public void setScan_user_id(int scan_user_id) {
        this.scan_user_id = scan_user_id;
    }

    public String getMain_order() {
        return main_order;
    }

    public void setMain_order(String main_order) {
        this.main_order = main_order;
    }

    public String getTotal_net_weight() {
        return total_net_weight;
    }

    public void setTotal_net_weight(String total_net_weight) {
        this.total_net_weight = total_net_weight;
    }

    public String getTotal_gross_weight() {
        return total_gross_weight;
    }

    public void setTotal_gross_weight(String total_gross_weight) {
        this.total_gross_weight = total_gross_weight;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public ScanUserBean getScan_user() {
        return scan_user;
    }

    public void setScan_user(ScanUserBean scan_user) {
        this.scan_user = scan_user;
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

    public static class ScanUserBean {
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

    @Override
    public String toString() {
        return "RuKuBean{" +
                "id=" + id +
                ", in_code='" + in_code + '\'' +
                ", user_id=" + user_id +
                ", scan_user_id=" + scan_user_id +
                ", main_order='" + main_order + '\'' +
                ", total_net_weight='" + total_net_weight + '\'' +
                ", total_gross_weight='" + total_gross_weight + '\'' +
                ", status=" + status +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", user=" + user +
                ", scan_user=" + scan_user +
                '}';
    }
}
