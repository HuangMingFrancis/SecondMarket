package com.ketangpai.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ketangpai.adapter.ChatAdapter;
import com.ketangpai.base.BaseActivity;
import com.ketangpai.base.Configs;
import com.ketangpai.entity.MessageInfo;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Francis on 2016/4/16.
 */
public class MyNotificationDesActivity extends BaseActivity implements View.OnClickListener{
    public static final int GETMESSAGE=100,GETSOCKET=101;

    //view
    private EditText mSendTextEt;
    private ImageView mSendtBtn,img_back;
    private RecyclerView mChatList;

    //adapter
    private ChatAdapter mChatAdapter;


    //变量
    private InputMethodManager mImm;
    private Socket socket=null;
    private String receiver_user_name="";

    private ArrayList<MessageInfo> messageInfos;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GETMESSAGE:
                    try {
                        JSONArray jsonArray = new JSONArray(msg.obj.toString());
                        JSONObject jsonObject;
                        MessageInfo messageInfo = new MessageInfo();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            messageInfo = new Gson().fromJson(jsonObject.toString(), MessageInfo.class);
                            messageInfos.add(messageInfo);
                        }
                        initChatList();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case GETSOCKET:
                    MessageInfo messageInfo=new MessageInfo();
                    messageInfo=new Gson().fromJson(msg.obj.toString(),MessageInfo.class);
                    messageInfos.add(messageInfo);
                    initChatList();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_chat;
    }

    @Override
    protected void initView() {
        mSendtBtn = (ImageView) findViewById(R.id.img_chat_send);
        mSendTextEt = (EditText) findViewById(R.id.et_chat_sendText);
        img_back=(ImageView)findViewById(R.id.iv_back);
    }

    @Override
    protected void initData() {
        setMessage();
    }

    @Override
    protected void initListener() {
        mSendtBtn.setOnClickListener(this);
        img_back.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    private void initChatList() {
        Collections.sort(messageInfos, new Comparator<MessageInfo>() {
            @Override
            public int compare(MessageInfo lhs, MessageInfo rhs) {
                return lhs.getMessage_date().compareTo(rhs.getMessage_date());
            }
        });
        mChatList = (RecyclerView)findViewById(R.id.list_chat);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mChatList.setLayoutManager(linearLayoutManager);
        mChatAdapter = new ChatAdapter(mContext, messageInfos);
        mChatAdapter.setUser(getSharedPreferences("user",0).getString("user_name",""));
        mChatList.setAdapter(mChatAdapter);
        mChatList.smoothScrollToPosition(messageInfos.size());
    }
    private void setMessage(){
        messageInfos=new ArrayList<>();
        receiver_user_name=getIntent().getStringExtra("receiver_user_name");
        OkHttpClientManager.postAsyn(Configs.QUERY_MESSAGE_BY_RECEIVE_AND_SEND, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getApplication(),Configs.URLERROR,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Message message=new Message();
                message.what=GETMESSAGE;
                message.obj=response;
                mHandler.sendMessage(message);


            }
        },new OkHttpClientManager.Param[]{
                new OkHttpClientManager.Param("send_user_name",getSharedPreferences("user",0).getString("user_name","")),
                new OkHttpClientManager.Param("receiver_user_name",receiver_user_name)
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_chat_send:
                if (mSendTextEt.getText().toString().equals("")){
                    showToast("消息不能为空", Toast.LENGTH_SHORT);
                    return;
                }
                final MessageInfo messageInfo=new MessageInfo();
                messageInfo.setSend_user_name(getSharedPreferences("user",0).getString("user_name",""));
                messageInfo.setReceive_user_name(receiver_user_name);
                Date date=new Date();
                SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                messageInfo.setMessage_date(formatter.format(date));
                messageInfo.setMessage_content(mSendTextEt.getText().toString());
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        connectServerWithTCPSocket(messageInfo);
                    }
                }.start();

                messageInfos.add(messageInfo);
                mSendTextEt.setText("");
                initChatList();

                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver_user_name=getIntent().getStringExtra("receiver_user_name");
        new Thread(){
            @Override
            public void run() {
                try {
                    Log.i("ming","socket before");
                    socket=new Socket("192.168.253.1",2133);
                    Log.i("ming","socket after  "+socket.hashCode());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                super.run();
                MessageInfo messageInfo=new MessageInfo();
                messageInfo.setReceive_user_name(receiver_user_name);
                messageInfo.setSend_user_name(getSharedPreferences("user",0).getString("user_name",""));
                messageInfo.setMessage_date("");
                messageInfo.setMessage_content("");
                connectServerWithTCPSocket(messageInfo);
                receiveServerMessage();
            }
        }.start();
    }

    @Override
    protected void onStop() {
        if (socket!=null){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onStop();
    }

    protected void connectServerWithTCPSocket(MessageInfo messageInfo) {
        final String data=new Gson().toJson(messageInfo);
        BufferedWriter bufferedWriter=null;
//        BufferedReader reader=null;
        try {
            Log.i("ming","hello2 "+data);
            bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//            reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            receiveServerMessage();
            if (!data.equals("")){
                Log.i("ming","data.equals:　　");
                bufferedWriter.write(data+"\n");
                bufferedWriter.flush();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
//
//            try {
//                reader.close();
//                bufferedWriter.close();
//                socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

    private void receiveServerMessage(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (true){
                    try {
                        BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String serveMsg=reader.readLine();
                        Log.i("ming","serverMsg: "+serveMsg);
                        MessageInfo messageInfo;
                        messageInfo=new Gson().fromJson(serveMsg,MessageInfo.class);
                        if (messageInfo==null){
                            return;
                        }
                        if (messageInfo.getReceive_user_name().equals(getSharedPreferences("user",0).getString("user_name",""))){
                            if(!messageInfo.getMessage_content().equals("")){
                                Message message=new Message();
                                message.what=GETSOCKET;
                                message.obj=serveMsg;
                                mHandler.sendMessage(message);
                            }
                        }
                        Log.i("ming","getServerMsg: "+serveMsg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }
}
