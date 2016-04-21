package com.ketangpai.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Francis on 2016/4/14.
 */
public class GoodsInfo implements Serializable{
    private int goods_no;
    private String goods_name;
    private String goods_category_no;
    private String goods_price;
    private String goods_des;
    private String goods_publisher;
    private String goods_publish_date;
    private String goods_trading_status;
    private String goods_trading_date;
    private int goods_is_collect=0;
    private String goods_imgs;
    public GoodsInfo(int goods_no, String goods_name, String goods_category_no,
                     String goods_price, String goods_des, String goods_publisher,
                     String goods_publish_date, String goods_trading_status,
                     String goods_trading_date,String goods_imgs) {
        super();
        this.goods_no=goods_no;
        this.goods_name = goods_name;
        this.goods_category_no = goods_category_no;
        this.goods_price = goods_price;
        this.goods_des = goods_des;
        this.goods_publisher = goods_publisher;
        this.goods_publish_date = goods_publish_date;
        this.goods_trading_status = goods_trading_status;
        this.goods_trading_date = goods_trading_date;
        this.goods_imgs=goods_imgs;
    }

    public String getGoods_imgs() {
        return goods_imgs;
    }

    public void setGoods_imgs(String goods_imgs) {
        this.goods_imgs = goods_imgs;
    }

    public int getGoods_no() {
        return goods_no;
    }
    public void setGoods_no(int goods_no) {
        this.goods_no = goods_no;
    }
    public String getGoods_name() {
        return goods_name;
    }
    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }
    public String getGoods_category_no() {
        return goods_category_no;
    }
    public void setGoods_category_no(String goods_category_no) {
        this.goods_category_no = goods_category_no;
    }
    public String getGoods_price() {
        return goods_price;
    }
    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }
    public String getGoods_des() {
        return goods_des;
    }
    public void setGoods_des(String goods_des) {
        this.goods_des = goods_des;
    }
    public String getGoods_publisher() {
        return goods_publisher;
    }
    public void setGoods_publisher(String goods_publisher) {
        this.goods_publisher = goods_publisher;
    }
    public String getGoods_publish_date() {
        return goods_publish_date;
    }
    public void setGoods_publish_date(String goods_publish_date) {
        this.goods_publish_date = goods_publish_date;
    }
    public String getGoods_trading_status() {
        return goods_trading_status;
    }
    public void setGoods_trading_status(String goods_trading_status) {
        this.goods_trading_status = goods_trading_status;
    }
    public String getGoods_trading_date() {
        return goods_trading_date;
    }
    public void setGoods_trading_date(String goods_trading_date) {
        this.goods_trading_date = goods_trading_date;
    }

    public void setGoods_is_collect(int goods_is_collect) {
        this.goods_is_collect = goods_is_collect;
    }

    public int getGoods_is_collect() {
        return goods_is_collect;
    }
}
