package com.hzx.wms.bean;

/**
 * @author qinl
 * @package com.hzx.wms.bean
 * @date 2019/7/8 15:52
 * @fileName QueryBean
 * @describe TODO
 */

public class QueryBean {

    /**
     * id : 19
     * prepare_in_ware_id : 19
     * main_order : 1
     * business_id : 1
     * name : 澳大利亚Bellamys贝拉米有机婴儿配方奶粉1段900g
     * bar_code : 9332045000174
     * item_record_no :
     * num : 1000
     * put_num : 1000
     * ware_location_id : 1
     * net_weight : 1
     * gross_weight : 1.2
     * maturity_date : 2020-05-08 00:00:00
     * top_storage_id : 1
     * storage_id : 1
     * status : 1
     * created_at : 2019-07-08 10:23:00
     * updated_at : 2019-07-08 15:22:00
     * business : {"id":1,"name":"测试商家"}
     * ware_location : {"id":1,"name":"AA001"}
     * top_storage : {"id":1,"name":"综合保税仓"}
     * storage : {"id":1,"name":"A区库"}
     */

    private int id;
    private int prepare_in_ware_id;
    private String main_order;
    private int business_id;
    private String name;
    private String bar_code;
    private String item_record_no;
    private String num;
    private String put_num;
    private int ware_location_id;
    private String net_weight;
    private String gross_weight;
    private String maturity_date;
    private String top_storage_id;
    private String storage_id;
    private int status;
    private String created_at;
    private String updated_at;
    private BusinessBean business;
    private WareLocationBean ware_location;
    private TopStorageBean top_storage;
    private StorageBean storage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrepare_in_ware_id() {
        return prepare_in_ware_id;
    }

    public void setPrepare_in_ware_id(int prepare_in_ware_id) {
        this.prepare_in_ware_id = prepare_in_ware_id;
    }

    public String getMain_order() {
        return main_order;
    }

    public void setMain_order(String main_order) {
        this.main_order = main_order;
    }

    public int getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(int business_id) {
        this.business_id = business_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBar_code() {
        return bar_code;
    }

    public void setBar_code(String bar_code) {
        this.bar_code = bar_code;
    }

    public String getItem_record_no() {
        return item_record_no;
    }

    public void setItem_record_no(String item_record_no) {
        this.item_record_no = item_record_no;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPut_num() {
        return put_num;
    }

    public void setPut_num(String put_num) {
        this.put_num = put_num;
    }

    public int getWare_location_id() {
        return ware_location_id;
    }

    public void setWare_location_id(int ware_location_id) {
        this.ware_location_id = ware_location_id;
    }

    public String getNet_weight() {
        return net_weight;
    }

    public void setNet_weight(String net_weight) {
        this.net_weight = net_weight;
    }

    public String getGross_weight() {
        return gross_weight;
    }

    public void setGross_weight(String gross_weight) {
        this.gross_weight = gross_weight;
    }

    public String getMaturity_date() {
        return maturity_date;
    }

    public void setMaturity_date(String maturity_date) {
        this.maturity_date = maturity_date;
    }

    public String getTop_storage_id() {
        return top_storage_id;
    }

    public void setTop_storage_id(String top_storage_id) {
        this.top_storage_id = top_storage_id;
    }

    public String getStorage_id() {
        return storage_id;
    }

    public void setStorage_id(String storage_id) {
        this.storage_id = storage_id;
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

    public BusinessBean getBusiness() {
        return business;
    }

    public void setBusiness(BusinessBean business) {
        this.business = business;
    }

    public WareLocationBean getWare_location() {
        return ware_location;
    }

    public void setWare_location(WareLocationBean ware_location) {
        this.ware_location = ware_location;
    }

    public TopStorageBean getTop_storage() {
        return top_storage;
    }

    public void setTop_storage(TopStorageBean top_storage) {
        this.top_storage = top_storage;
    }

    public StorageBean getStorage() {
        return storage;
    }

    public void setStorage(StorageBean storage) {
        this.storage = storage;
    }

    public static class BusinessBean {
        /**
         * id : 1
         * name : 测试商家
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

    public static class WareLocationBean {
        /**
         * id : 1
         * name : AA001
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

    public static class TopStorageBean {
        /**
         * id : 1
         * name : 综合保税仓
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

    public static class StorageBean {
        /**
         * id : 1
         * name : A区库
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
