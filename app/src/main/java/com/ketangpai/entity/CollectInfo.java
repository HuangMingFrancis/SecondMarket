package com.ketangpai.entity;

import java.io.Serializable;

/**
 * Created by Francis on 2016/4/15.
 */
public class CollectInfo implements Serializable{
    private int collect_no;
    private String goods_no;
    private String user_name;
    private String user_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setCollect_no(int collect_no) {
        this.collect_no = collect_no;
    }

    public void setGoods_no(String goods_no) {
        this.goods_no = goods_no;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getCollect_no() {
        return collect_no;
    }

    public String getGoods_no() {
        return goods_no;
    }

    public String getUser_name() {
        return user_name;
    }
}
