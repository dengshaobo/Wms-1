package com.hzx.wms.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author qinl
 * @package qinl.com.hzxoderquery.activity.task
 * @date 2019/5/29 9:20
 * @fileName TaskListBean
 * @describe TODO
 */

public class TaskListBean implements Serializable {


    /**
     * id : 1
     * out_code : 1559785423626
     * out_id : 57222701241559782800
     * user_id : 1
     * scan_user_id : 1
     * check_user_id : null
     * print_times : null
     * order_status : null
     * pick_status : null
     * business_id : 1
     * level : 2
     * status : 3
     * deleted_at : null
     * created_at : 2019-06-06 09:43:43
     * updated_at : 2019-06-06 11:48:42
     * user : {"id":1,"name":"管理员"}
     * business : {"id":1,"name":"测试商家1"}
     * top_storage : null
     * storage : null
     * scan_user : {"id":1,"name":"管理员"}
     * order_list : ["D1641645161SPLIT560","D1641645161","D1641645162"]
     * bar_code_list : ["00001","00002","00003","00004","00005","00006","00007","00008","00009","00010","00011","00012","00013","00014","00015","00016","00001","00001"]
     * num_list : [{"bar_code":"00001","num":1,"pick_num":null,"ware_location":"AA1"},{"bar_code":"00002","num":11,"pick_num":null,"ware_location":"AA2"},{"bar_code":"00003","num":12,"pick_num":null,"ware_location":"测试货位3"},{"bar_code":"00004","num":13,"pick_num":null,"ware_location":"测试货位4"},{"bar_code":"00005","num":14,"pick_num":null,"ware_location":"测试货位5"},{"bar_code":"00006","num":15,"pick_num":null,"ware_location":"测试货位6"},{"bar_code":"00007","num":16,"pick_num":null,"ware_location":"测试货位7"},{"bar_code":"00008","num":17,"pick_num":null,"ware_location":"测试货位8"},{"bar_code":"00009","num":18,"pick_num":null,"ware_location":"测试货位9"},{"bar_code":"00010","num":19,"pick_num":null,"ware_location":"测试货位10"},{"bar_code":"00011","num":20,"pick_num":null,"ware_location":"测试货位11"},{"bar_code":"00012","num":21,"pick_num":null,"ware_location":"测试货位12"},{"bar_code":"00013","num":22,"pick_num":null,"ware_location":"测试货位13"},{"bar_code":"00014","num":23,"pick_num":null,"ware_location":"测试货位14"},{"bar_code":"00015","num":24,"pick_num":null,"ware_location":"测试货位15"},{"bar_code":"00016","num":25,"pick_num":null,"ware_location":"测试货位16"},{"bar_code":"00001","num":1,"pick_num":null,"ware_location":"AA1"},{"bar_code":"00001","num":8,"pick_num":null,"ware_location":"AA1"}]
     */

    private int id;
    private String out_code;
    private String out_id;
    private int user_id;
    private int scan_user_id;
    private int check_user_id;
    private Object print_times;
    private int order_status;
    private Object pick_status;
    private int business_id;
    private int level;
    private int status;
    private Object deleted_at;
    private String created_at;
    private String updated_at;
    private UserBean user;
    private BusinessBean business;
    private Object top_storage;
    private Object storage;
    private ScanUserBean scan_user;
    private List<String> order_list;
    private List<String> bar_code_list;
    private List<NumListBean> num_list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOut_code() {
        return out_code;
    }

    public void setOut_code(String out_code) {
        this.out_code = out_code;
    }

    public String getOut_id() {
        return out_id;
    }

    public void setOut_id(String out_id) {
        this.out_id = out_id;
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

    public int getCheck_user_id() {
        return check_user_id;
    }

    public void setCheck_user_id(int check_user_id) {
        this.check_user_id = check_user_id;
    }

    public Object getPrint_times() {
        return print_times;
    }

    public void setPrint_times(Object print_times) {
        this.print_times = print_times;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public Object getPick_status() {
        return pick_status;
    }

    public void setPick_status(Object pick_status) {
        this.pick_status = pick_status;
    }

    public int getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(int business_id) {
        this.business_id = business_id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Object deleted_at) {
        this.deleted_at = deleted_at;
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

    public BusinessBean getBusiness() {
        return business;
    }

    public void setBusiness(BusinessBean business) {
        this.business = business;
    }

    public Object getTop_storage() {
        return top_storage;
    }

    public void setTop_storage(Object top_storage) {
        this.top_storage = top_storage;
    }

    public Object getStorage() {
        return storage;
    }

    public void setStorage(Object storage) {
        this.storage = storage;
    }

    public ScanUserBean getScan_user() {
        return scan_user;
    }

    public void setScan_user(ScanUserBean scan_user) {
        this.scan_user = scan_user;
    }

    public List<String> getOrder_list() {
        return order_list;
    }

    public void setOrder_list(List<String> order_list) {
        this.order_list = order_list;
    }

    public List<String> getBar_code_list() {
        return bar_code_list;
    }

    public void setBar_code_list(List<String> bar_code_list) {
        this.bar_code_list = bar_code_list;
    }

    public List<NumListBean> getNum_list() {
        return num_list;
    }

    public void setNum_list(List<NumListBean> num_list) {
        this.num_list = num_list;
    }

    public static class UserBean implements Serializable {
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

    public static class BusinessBean implements Serializable {
        /**
         * id : 1
         * name : 测试商家1
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

    public static class ScanUserBean implements Serializable {
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

    public static class NumListBean implements Serializable {
        /**
         * bar_code : 00001
         * num : 1
         * pick_num : null
         * ware_location : AA1
         */

        private String bar_code;
        private int num;
        private int pick_num;
        private String ware_location;

        public String getBar_code() {
            return bar_code;
        }

        public void setBar_code(String bar_code) {
            this.bar_code = bar_code;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getPick_num() {
            return pick_num;
        }

        public void setPick_num(int pick_num) {
            this.pick_num = pick_num;
        }

        public String getWare_location() {
            return ware_location;
        }

        public void setWare_location(String ware_location) {
            this.ware_location = ware_location;
        }

        @Override
        public String toString() {
            return "NumListBean{" +
                    "bar_code='" + bar_code + '\'' +
                    ", num=" + num +
                    ", pick_num=" + pick_num +
                    ", ware_location='" + ware_location + '\'' +
                    '}';
        }
    }
}
