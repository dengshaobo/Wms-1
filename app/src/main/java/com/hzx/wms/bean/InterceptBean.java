package com.hzx.wms.bean;

/**
 * @author qinl
 * @package com.hzx.wms.bean
 * @date 2019/7/23 9:42
 * @fileName InterceptBean
 * @describe TODO
 */

public class InterceptBean {


    /**
     * logistics_no : 1
     * now_status_id : 800
     * now_status : {"declare_status_id":800,"declare_status_msg":"放行"}
     */

    private String logistics_no;
    private String now_status_id;
    private NowStatusBean now_status;

    public String getLogistics_no() {
        return logistics_no;
    }

    public void setLogistics_no(String logistics_no) {
        this.logistics_no = logistics_no;
    }

    public String getNow_status_id() {
        return now_status_id;
    }

    public void setNow_status_id(String now_status_id) {
        this.now_status_id = now_status_id;
    }

    public NowStatusBean getNow_status() {
        return now_status;
    }

    public void setNow_status(NowStatusBean now_status) {
        this.now_status = now_status;
    }

    public static class NowStatusBean {
        /**
         * declare_status_id : 800
         * declare_status_msg : 放行
         */

        private int declare_status_id;
        private String declare_status_msg;

        public int getDeclare_status_id() {
            return declare_status_id;
        }

        public void setDeclare_status_id(int declare_status_id) {
            this.declare_status_id = declare_status_id;
        }

        public String getDeclare_status_msg() {
            return declare_status_msg;
        }

        public void setDeclare_status_msg(String declare_status_msg) {
            this.declare_status_msg = declare_status_msg;
        }
    }
}
