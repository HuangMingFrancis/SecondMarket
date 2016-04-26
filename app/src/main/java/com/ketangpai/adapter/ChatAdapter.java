package com.ketangpai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.ketangpai.base.BaseAdapter;
import com.ketangpai.base.Configs;
import com.ketangpai.entity.MessageInfo;
import com.ketangpai.nan.ketangpai.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nan on 2016/3/21.
 */
public class ChatAdapter extends BaseAdapter<MessageInfo> {
    private String user;
    private String send_user_head;
    private String receive_user_head;

    public void setSend_user_head(String send_user_head) {
        this.send_user_head = send_user_head;
    }

    public void setReceive_user_head(String receive_user_head) {
        this.receive_user_head = receive_user_head;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ChatAdapter(Context mContext, List mDataList) {
        super(mContext, mDataList);
    }

    @Override
    public int getItemViewType(int position) {
        MessageInfo messageInfo=mDataList.get(position);
        if (messageInfo.getSend_user_name().equals(user))
        return 1;
        else
            return 0;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public void addItem(int positon, MessageInfo item) {
        super.addItem(positon, item);
    }

    @Override
    public void deleteItem(int positon) {
        super.deleteItem(positon);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        if (viewType == 0) {
            return R.layout.item_chat_acceptor;
        } else {
            return R.layout.item_chat_receiver;
        }
    }

    @Override
    protected void bindData(ViewHolder holder, int position, MessageInfo item) {
        //初始化view
        CircleImageView UserImg;
        TextView text;
        if(getItemViewType(position)==0){
             UserImg= (CircleImageView) holder.getViewById(R.id.img_chat_acceptor_userIcon);
             text= (TextView) holder.getViewById(R.id.tv_chat_acceptor_text);
            text.setText(item.getMessage_content());
            if (!send_user_head.equals(""))
                UserImg.setImageBitmap(Configs.base64ToBitmap(send_user_head));
        }else{
            UserImg= (CircleImageView) holder.getViewById(R.id.img_chat_receiver_userIcon);
            text= (TextView) holder.getViewById(R.id.tv_chat_receiver_text);
            text.setText(item.getMessage_content());
            if (!receive_user_head.equals(""))
                UserImg.setImageBitmap(Configs.base64ToBitmap(receive_user_head));
        }

        //初始化view的值

    }


}
