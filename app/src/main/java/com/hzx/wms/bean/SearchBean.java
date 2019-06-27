package com.hzx.wms.bean;

import java.util.List;

public class SearchBean {


    /**
     * num : 1000
     * list : [{"ware_location":"AA01","num":"1000"}]
     */

    private String num;
    private List<ListBean> list;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * ware_location : AA01
         * num : 1000
         */

        private String ware_location;
        private String num;

        public String getWare_location() {
            return ware_location;
        }

        public void setWare_location(String ware_location) {
            this.ware_location = ware_location;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }
}
