package com.hzx.wms.bean;

/**
 * Created by qinliang on 2018/5/29.
 */

public class AppUpdate {


    /**
     * id : 1
     * version : 1.0
     * content :
     * url :
     * created_at : 2018-05-29 16:58:01
     * updated_at : -0001-11-30 00:00:00
     */

    private int id;
    private String version;
    private String content;
    private String url;
    private String created_at;
    private String updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
