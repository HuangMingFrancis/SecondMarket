package com.ketangpai.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ketangpai.activity.MyNotificationDesActivity;
import com.ketangpai.base.BaseAdapter;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.base.Configs;
import com.ketangpai.entity.MessageInfo;
import com.ketangpai.entity.User;
import com.ketangpai.listener.OnItemClickListener;
import com.ketangpai.listener.OnItemLongClickListener;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.OkHttpClientManager;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Francis on 2016/4/16.
 */
public class MyNotifyFragment extends BaseFragment implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView recycler_my_notify;
    private SwipeRefreshLayout swipe_notify_list;
    private TextView tv_hint_my_notify;
    private ArrayList<MessageInfo> messageInfos,data,data1;
    private ArrayList<User> users;
    private MessagesInfoAdapter messagesInfoAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_collect;
    }

    @Override
    protected void initView() {
        recycler_my_notify=(RecyclerView)view.findViewById(R.id.recycle_my_collect);
        tv_hint_my_notify=(TextView)view.findViewById(R.id.tv_hint_my_collect);
        tv_hint_my_notify.setText("目前还没有任何消息");
        swipe_notify_list=(SwipeRefreshLayout)view.findViewById(R.id.swipe_collect_list);

        swipe_notify_list.setColorSchemeColors(R.color.red, R.color.blue, R.color.green,R.color.yellow);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_my_notify.setLayoutManager(layoutManager);

    }

    @Override
    protected void initData() {

        getMessageInfo();
    }

    @Override
    protected void initListener() {
        swipe_notify_list.setOnRefreshListener(this);
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        swipe_notify_list.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
    }

    @Override
    protected void loadData() {

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }

    private void getMessageInfo(){
        messageInfos=new ArrayList<>();
        data=new ArrayList<>();
        swipe_notify_list.setRefreshing(true);
        OkHttpClientManager.postAsyn(Configs.QUERY_ALL_MESSAGE, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getActivity(), Configs.URLERROR,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Log.i("ming","response:  "+response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject;
                    MessageInfo messageInfo=new MessageInfo();
                    for (int i=0;i<jsonArray.length();i++){
                        jsonObject=jsonArray.getJSONObject(i);
                        messageInfo=new Gson().fromJson(jsonObject.toString(),MessageInfo.class);
                        messageInfos.add(messageInfo);
                    }
                    if (!(messageInfos.size()>0)){
                        initMessagesList();
                        return;
                    }
                    data.add(messageInfos.get(0));
                    for (int i=0;i<messageInfos.size();i++){
                        boolean exist=false;
                        messageInfo=messageInfos.get(i);
                        for (int j=0;j<data.size();j++){
                            if (messageInfo.getSend_user_name().equals(data.get(j).getSend_user_name())){
                                exist=true;
                            }
                        }
                        if (!exist)
                            data.add(messageInfo);
                    }
                    initMessagesHead();
//                    initMessagesList();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new OkHttpClientManager.Param("receiver_user_name",
                getActivity().getSharedPreferences("user",0).getString("user_name","")));

    }
    //查找用户头像
    private void initMessagesHead(){
        data1=new ArrayList<>();
        users=new ArrayList<>();
        if (data.size()<=0){
            tv_hint_my_notify.setVisibility(View.VISIBLE);
            recycler_my_notify.setVisibility(View.GONE);
            return;
        }
        else{
            for (int i=0;i<data.size();i++){
                final MessageInfo messageInfo=data.get(i);
                Log.i("ming","send_user_name:  "+messageInfo.getSend_user_id());
                OkHttpClientManager.postAsyn(Configs.QUERY_USER_HEAD, new OkHttpClientManager.ResultCallback<String>() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        Log.i("ming","head response:  " +response);
                        User user=new Gson().fromJson(response,User.class);
                        users.add(user);
                        messageInfo.setUser_head(user.getHeadimg());
                        data1.add(messageInfo);
//                        messageInfo.setUser_head(user.getHeadimg());
                        if (users.size()==data.size()){
                            initMessagesList();
                        }
                    }
                },new OkHttpClientManager.Param("send_user_id",data.get(i).getSend_user_id()));
            }
        }
    }


    private void initMessagesList(){
        swipe_notify_list.setRefreshing(false);
        if (data.size()<=0){
            tv_hint_my_notify.setVisibility(View.VISIBLE);
            recycler_my_notify.setVisibility(View.GONE);
            return;
        }
        else{
            tv_hint_my_notify.setVisibility(View.GONE);
            recycler_my_notify.setVisibility(View.VISIBLE);

            messagesInfoAdapter=new MessagesInfoAdapter(getActivity(),data1);
            recycler_my_notify.setLayoutManager(new LinearLayoutManager(getActivity()));
            recycler_my_notify.setAdapter(messagesInfoAdapter);

            messagesInfoAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent=new Intent(getActivity(), MyNotificationDesActivity.class);
                    intent.putExtra("receiver_user_name", data1.get(position).getSend_user_name());
                    intent.putExtra("send_user_head",data1.get(position).getUser_head());
                    intent.putExtra("message",data1.get(position));
                    getActivity().startActivity(intent);
                }
            });
            messagesInfoAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(View view, final int position) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setTitle("提示");
                    builder.setMessage("是否删除该聊天记录?");
                    builder.setNegativeButton("取消",null);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteMyMessage(data1.get(position));
                        }
                    });
                    builder.create().show();
                    return true;
                }
            });

        }
    }

    @Override
    public void onRefresh() {
        getMessageInfo();
    }

    class MessagesInfoAdapter extends BaseAdapter<MessageInfo> {

        public MessagesInfoAdapter(Context mContext, List mDataList) {
            super(mContext, mDataList);
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.item_my_notify;
        }

        @Override
        protected void bindData(ViewHolder holder, int position, MessageInfo item) {
            TextView tv_message_user_name,tv_message_date,tv_message_content;
            CircleImageView img_head;
            tv_message_user_name=(TextView) holder.getViewById(R.id.tv_message_user_name);
            tv_message_date=(TextView)holder.getViewById(R.id.tv_message_date);
            tv_message_content=(TextView)holder.getViewById(R.id.tv_message_content);
            img_head=(CircleImageView)holder.getViewById(R.id.img_head);

            tv_message_user_name.setText(item.getSend_user_name());
            tv_message_date.setText(item.getMessage_date());
            tv_message_content.setText(item.getMessage_content());
            Log.i("ming","user_head:　"+item.getUser_head());
            if (!item.getUser_head().equals(""))
                img_head.setImageBitmap(Configs.base64ToBitmap(item.getUser_head()));
        }
    }

    private ArrayList<MessageInfo> getAUserMessages(String send_user_name){
        ArrayList<MessageInfo> data=new ArrayList<>();
        for (MessageInfo messageInfo:messageInfos){
            if (send_user_name.equals(messageInfo.getSend_user_name())){
                data.add(messageInfo);
            }
        }
        return data;
    }

    //删除聊天记录
    private void deleteMyMessage(final MessageInfo messageInfo){
        OkHttpClientManager.postAsyn(Configs.DELETE_MESSAGE, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getActivity(),Configs.URLERROR,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                if (!response.equals("0")){
                    Log.i("ming","onResponse  data:  "+data.size());
                    data.remove(messageInfo);
                    initMessagesList();
                }
            }
        },new OkHttpClientManager.Param[]{
                new OkHttpClientManager.Param("send_user_name",messageInfo.getSend_user_name()),
                new OkHttpClientManager.Param("receive_user_name",messageInfo.getReceive_user_name())
        });
    }


}
