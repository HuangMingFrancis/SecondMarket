package com.ketangpai.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ketangpai.activity.MyNotificationDesActivity;
import com.ketangpai.base.BaseAdapter;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.base.Configs;
import com.ketangpai.entity.MessageInfo;
import com.ketangpai.listener.OnItemClickListener;
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

/**
 * Created by Francis on 2016/4/16.
 */
public class MyNotifyFragment extends BaseFragment implements View.OnClickListener{
    private RecyclerView recycler_my_notify;
    private TextView tv_hint_my_notify;
    private ArrayList<MessageInfo> messageInfos;
    private MessagesInfoAdapter messagesInfoAdapter;
    private ArrayList<ArrayList<MessageInfo>> messagesList;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_collect;
    }

    @Override
    protected void initView() {
        recycler_my_notify=(RecyclerView)view.findViewById(R.id.recycle_my_collect);
        tv_hint_my_notify=(TextView)view.findViewById(R.id.tv_hint_my_collect);
        tv_hint_my_notify.setText("目前还没有任何消息");

    }

    @Override
    protected void initData() {
        getMessageInfo();
        messageInfos=new ArrayList<>();
        messagesList=new ArrayList<>();
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void loadData() {

    }
    protected void connectServerWithTCPSocket() {
        Socket socket=null;
        BufferedWriter bufferedWriter=null;
        BufferedReader reader=null;
        try {
            Log.i("ming","hello1 ");
            socket=new Socket("192.168.253.1",2133);
            Log.i("ming","hello2 ");
            bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter.write("hello"+"\n");
            bufferedWriter.flush();
            String serveMsg=reader.readLine();
            Log.i("ming","getServerMsg: "+serveMsg);


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();
                bufferedWriter.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }

    private void getMessageInfo(){
        OkHttpClientManager.postAsyn(Configs.QUERY_ALL_MESSAGE, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getActivity(), Configs.URLERROR,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject;
                    MessageInfo messageInfo=new MessageInfo();
                    for (int i=0;i<jsonArray.length();i++){
                        jsonObject=jsonArray.getJSONObject(i);
                        messageInfo=new Gson().fromJson(jsonObject.toString(),MessageInfo.class);
                        messageInfos.add(messageInfo);
                    }
                    ArrayList<MessageInfo> messageInfos1=new ArrayList<MessageInfo>();
                    messageInfos1.add(messageInfos.get(0));
                    for (int i=0;i<messageInfos.size();i++){
                        boolean exist=false;
                        messageInfo=messageInfos.get(i);
                        for (int j=0;j<messageInfos1.size();j++){
                            if (messageInfo.getSend_user_name().equals(messageInfos1.get(j).getSend_user_name())){
                                exist=true;
                            }
                        }
                        if (!exist)
                            messageInfos1.add(messageInfo);
                    }
                    initMessagesList(messageInfos1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new OkHttpClientManager.Param("receiver_user_name",
                getActivity().getSharedPreferences("user",0).getString("user_name","")));

    }

    private void initMessagesList(final ArrayList<MessageInfo> data){
        if (data.size()<0){
            tv_hint_my_notify.setVisibility(View.VISIBLE);
            recycler_my_notify.setVisibility(View.GONE);
            return;
        }
        else{
            tv_hint_my_notify.setVisibility(View.GONE);
            recycler_my_notify.setVisibility(View.VISIBLE);

            messagesInfoAdapter=new MessagesInfoAdapter(getActivity(),data);
            recycler_my_notify.setLayoutManager(new LinearLayoutManager(getActivity()));
            recycler_my_notify.setAdapter(messagesInfoAdapter);

            messagesInfoAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent=new Intent(getActivity(), MyNotificationDesActivity.class);
                    intent.putExtra("receiver_user_name", data.get(position).getSend_user_name());
                    getActivity().startActivity(intent);
                }
            });

        }
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
            tv_message_user_name=(TextView) holder.getViewById(R.id.tv_message_user_name);
            tv_message_date=(TextView)holder.getViewById(R.id.tv_message_date);
            tv_message_content=(TextView)holder.getViewById(R.id.tv_message_content);

            tv_message_user_name.setText(item.getSend_user_name());
            tv_message_date.setText(item.getMessage_date());
            tv_message_content.setText(item.getMessage_content());
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


}
