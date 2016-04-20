package com.ketangpai.entity;

import java.io.Serializable;

/**
 * Created by Francis on 2016/4/14.
 */
public class User implements Serializable{
    private int id;
    private String name;
    private String password;
    private String tel;
    public User(int id,String name,String password,String tel){
        this.id=id;
        this.name=name;
        this.password=password;
        this.tel=tel;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getTel() {
        return tel;
    }
}
