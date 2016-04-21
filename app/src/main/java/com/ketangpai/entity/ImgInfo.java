package com.ketangpai.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Francis on 2016/4/21.
 */
public class ImgInfo implements Serializable{
    private int img_no;
    private String goods_no;
    private ArrayList<String> img_res;

    public void setGoods_no(String goods_no) {
        this.goods_no = goods_no;
    }

    public void setImg_no(int img_no) {
        this.img_no = img_no;
    }

    public void setImg_res(ArrayList<String> img_res) {
        this.img_res = img_res;
    }

    public ArrayList<String> getImg_res() {
        return img_res;
    }

    public int getImg_no() {
        return img_no;
    }

    public String getGoods_no() {
        return goods_no;
    }
}
