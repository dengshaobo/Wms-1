package com.hzx.wms.bean;

/**
 * @author qinl
 * @package qinl.com.hzxoderquery.bean
 * @date 2019/6/12 9:32
 * @fileName CheckBean
 * @describe TODO
 */

public class CheckBean {


    /**
     * id : 83
     * prepare_out_order_id : 12
     * business_id : 1
     * order_no : D164164516111SPLIT703
     * gnum : 1
     * consignee : 郭俊秀
     * consignee_telephone : 18533956489
     * consignee_address : 四川省成都市双流区黄甲镇长梗新居
     * sender : 成都双流综保
     * sender_telephone : 89460888
     * sender_address : 四川省成都市双流区货运大道888号
     * normal_ware_id : 1
     * bar_code : 00001
     * num : 10
     * pick_num : 10
     * confirm_num : null
     * confirm_time : null
     * print_times : null
     * order_type : 1
     * declare_info : null
     * status : 1
     * deleted_at : null
     * created_at : 2019-06-11 15:45:57
     * updated_at : 2019-06-13 10:43:15
     * logistics_infos : {"order_no":"D164164516111SPLIT703","logistics_no":"73114812571266"}
     * prepare_out_order : {"id":12,"check_user_id":1}
     * normal_ware : {"id":1,"name":"测试商品1"}
     */

    private int id;
    private int prepare_out_order_id;
    private int business_id;
    private String order_no;
    private int gnum;
    private String consignee;
    private String consignee_telephone;
    private String consignee_address;
    private String sender;
    private String sender_telephone;
    private String sender_address;
    private int normal_ware_id;
    private String bar_code;
    private int num;
    private int pick_num;
    private int confirm_num;
    private Object confirm_time;
    private Object print_times;
    private int order_type;
    private Object declare_info;
    private int status;
    private Object deleted_at;
    private String created_at;
    private String updated_at;
    private LogisticsInfosBean logistics_infos;
    private PrepareOutOrderBean prepare_out_order;
    private NormalWareBean normal_ware;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrepare_out_order_id() {
        return prepare_out_order_id;
    }

    public void setPrepare_out_order_id(int prepare_out_order_id) {
        this.prepare_out_order_id = prepare_out_order_id;
    }

    public int getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(int business_id) {
        this.business_id = business_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getGnum() {
        return gnum;
    }

    public void setGnum(int gnum) {
        this.gnum = gnum;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsignee_telephone() {
        return consignee_telephone;
    }

    public void setConsignee_telephone(String consignee_telephone) {
        this.consignee_telephone = consignee_telephone;
    }

    public String getConsignee_address() {
        return consignee_address;
    }

    public void setConsignee_address(String consignee_address) {
        this.consignee_address = consignee_address;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSender_telephone() {
        return sender_telephone;
    }

    public void setSender_telephone(String sender_telephone) {
        this.sender_telephone = sender_telephone;
    }

    public String getSender_address() {
        return sender_address;
    }

    public void setSender_address(String sender_address) {
        this.sender_address = sender_address;
    }

    public int getNormal_ware_id() {
        return normal_ware_id;
    }

    public void setNormal_ware_id(int normal_ware_id) {
        this.normal_ware_id = normal_ware_id;
    }

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

    public int getConfirm_num() {
        return confirm_num;
    }

    public void setConfirm_num(int confirm_num) {
        this.confirm_num = confirm_num;
    }

    public Object getConfirm_time() {
        return confirm_time;
    }

    public void setConfirm_time(Object confirm_time) {
        this.confirm_time = confirm_time;
    }

    public Object getPrint_times() {
        return print_times;
    }

    public void setPrint_times(Object print_times) {
        this.print_times = print_times;
    }

    public int getOrder_type() {
        return order_type;
    }

    public void setOrder_type(int order_type) {
        this.order_type = order_type;
    }

    public Object getDeclare_info() {
        return declare_info;
    }

    public void setDeclare_info(Object declare_info) {
        this.declare_info = declare_info;
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

    public LogisticsInfosBean getLogistics_infos() {
        return logistics_infos;
    }

    public void setLogistics_infos(LogisticsInfosBean logistics_infos) {
        this.logistics_infos = logistics_infos;
    }

    public PrepareOutOrderBean getPrepare_out_order() {
        return prepare_out_order;
    }

    public void setPrepare_out_order(PrepareOutOrderBean prepare_out_order) {
        this.prepare_out_order = prepare_out_order;
    }

    public NormalWareBean getNormal_ware() {
        return normal_ware;
    }

    public void setNormal_ware(NormalWareBean normal_ware) {
        this.normal_ware = normal_ware;
    }

    public static class LogisticsInfosBean {
        /**
         * order_no : D164164516111SPLIT703
         * logistics_no : 73114812571266
         */

        private String order_no;
        private String logistics_no;

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getLogistics_no() {
            return logistics_no;
        }

        public void setLogistics_no(String logistics_no) {
            this.logistics_no = logistics_no;
        }
    }

    public static class PrepareOutOrderBean {
        /**
         * id : 12
         * check_user_id : 1
         */

        private int id;
        private int check_user_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCheck_user_id() {
            return check_user_id;
        }

        public void setCheck_user_id(int check_user_id) {
            this.check_user_id = check_user_id;
        }
    }

    public static class NormalWareBean {
        /**
         * id : 1
         * name : 测试商品1
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
