package com.hzx.wms.bean;

/**
 * Created by linhu on 2019/6/19.
 */

public class DifferentBean {


    /**
     * id : 37
     * prepare_in_order_id : 5
     * ware_id : 23
     * num : 720
     * scan_num : 0
     * ware_location_id : null
     * maturity_date : 2020-06-19 00:00:00
     * top_storage_id : 1
     * storage_id : 1
     * status : 1
     * deleted_at : null
     * created_at : 2019-06-19 11:39:58
     * updated_at : 2019-06-19 11:39:58
     * ware : {"id":23,"name":"Nestle二合一速溶咖啡170g","bar_code":"7613035918443","item_record_no":"7613034025548","business_id":"3","mix":"1","business":{"id":3,"name":"田瑞福"}}
     * ware_location : null
     */

    private int id;
    private int prepare_in_order_id;
    private int ware_id;
    private String num;
    private String scan_num;
    private int ware_location_id;
    private String maturity_date;
    private String top_storage_id;
    private String storage_id;
    private int status;
    private Object deleted_at;
    private String created_at;
    private String updated_at;
    private WareBean ware;
    private String ware_location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrepare_in_order_id() {
        return prepare_in_order_id;
    }

    public void setPrepare_in_order_id(int prepare_in_order_id) {
        this.prepare_in_order_id = prepare_in_order_id;
    }

    public int getWare_id() {
        return ware_id;
    }

    public void setWare_id(int ware_id) {
        this.ware_id = ware_id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getScan_num() {
        return scan_num;
    }

    public void setScan_num(String scan_num) {
        this.scan_num = scan_num;
    }

    public Object getWare_location_id() {
        return ware_location_id;
    }

    public void setWare_location_id(int ware_location_id) {
        this.ware_location_id = ware_location_id;
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

    public WareBean getWare() {
        return ware;
    }

    public void setWare(WareBean ware) {
        this.ware = ware;
    }

    public Object getWare_location() {
        return ware_location;
    }

    public void setWare_location(String ware_location) {
        this.ware_location = ware_location;
    }

    public static class WareBean {
        /**
         * id : 23
         * name : Nestle二合一速溶咖啡170g
         * bar_code : 7613035918443
         * item_record_no : 7613034025548
         * business_id : 3
         * mix : 1
         * business : {"id":3,"name":"田瑞福"}
         */

        private int id;
        private String name;
        private String bar_code;
        private String item_record_no;
        private String business_id;
        private String mix;
        private BusinessBean business;

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

        public String getBusiness_id() {
            return business_id;
        }

        public void setBusiness_id(String business_id) {
            this.business_id = business_id;
        }

        public String getMix() {
            return mix;
        }

        public void setMix(String mix) {
            this.mix = mix;
        }

        public BusinessBean getBusiness() {
            return business;
        }

        public void setBusiness(BusinessBean business) {
            this.business = business;
        }

        public static class BusinessBean {
            /**
             * id : 3
             * name : 田瑞福
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
}
