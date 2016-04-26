package com.ketangpai.entity;

import java.io.Serializable;

/**
 * Created by Francis on 2016/4/16.
 */
public class MessageInfo implements Serializable {
    private int message_no;
    private String send_user_name;
    private String receive_user_name;
    private String message_date;
    private String message_content;
    private String send_user_id;
    private String receive_user_id;
    private String user_head;

    public String getUser_head() {
        return user_head;
    }

    public void setUser_head(String user_head) {
        this.user_head = user_head;
    }

    public void setSend_user_id(String send_user_id) {
        this.send_user_id = send_user_id;
    }

    public void setReceive_user_id(String receive_user_id) {
        this.receive_user_id = receive_user_id;
    }

    public String getSend_user_id() {
        return send_user_id;
    }

    public String getReceive_user_id() {
        return receive_user_id;
    }


    public MessageInfo() {
    }

    ;

    public MessageInfo(int message_no, String send_user_name, String receive_user_name, String message_date, String message_content,String send_user_id,String receive_user_id,String user_head) {
        this.message_no = message_no;
        this.send_user_name = send_user_name;
        this.receive_user_name = receive_user_name;
        this.message_date = message_date;
        this.message_content = message_content;
        this.send_user_id=send_user_id;
        this.receive_user_id=receive_user_id;
        this.user_head=user_head;
    }

    public String getSend_user_name() {
        return send_user_name;
    }

    public int getMessage_no() {
        return message_no;
    }

    public String getMessage_content() {
        return message_content;
    }

    public String getMessage_date() {
        return message_date;
    }

    public void setSend_user_name(String send_user_name) {
        this.send_user_name = send_user_name;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }

    public void setMessage_date(String message_date) {
        this.message_date = message_date;
    }

    public void setMessage_no(int message_no) {
        this.message_no = message_no;
    }

    public String getReceive_user_name() {
        return receive_user_name;
    }

    public void setReceive_user_name(String receive_user_name) {
        this.receive_user_name = receive_user_name;
    }

}
