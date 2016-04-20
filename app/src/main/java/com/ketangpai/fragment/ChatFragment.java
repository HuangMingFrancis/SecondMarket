package com.ketangpai.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ketangpai.adapter.ChatAdapter;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.nan.ketangpai.R;

import java.util.ArrayList;

/**
 * Created by nan on 2016/3/19.
 */
public class ChatFragment extends BaseFragment implements View.OnClickListener, TextWatcher {

    //view
    EditText mSendTextEt;
    ImageView mSendtBtn;
    RecyclerView mChatList;

    //adapter
    ChatAdapter mChatAdapter;


    //变量
    ArrayList mChatRecondList;
    InputMethodManager mImm;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    protected void initView() {
        mSendtBtn = (ImageView) view.findViewById(R.id.img_chat_send);
        mSendTextEt = (EditText) view.findViewById(R.id.et_chat_sendText);
        initChatList();
    }

    @Override
    protected void initData() {
        mImm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        for (int i = 0; i < 10; ++i) {
            if (i % 2 == 0) {
                mChatRecondList.add(0);
            } else {
                mChatRecondList.add(1);
            }
        }
    }

    @Override
    protected void initListener() {
        mSendTextEt.setOnClickListener(this);
        mChatList.setOnClickListener(this);
        mSendTextEt.addTextChangedListener(this);
    }

    @Override
    protected void loadData() {

    }

    private void initChatList() {
        mChatList = (RecyclerView) view.findViewById(R.id.list_chat);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mChatList.setLayoutManager(linearLayoutManager);
        mChatRecondList = new ArrayList();
        mChatAdapter = new ChatAdapter(mContext, mChatRecondList);
        mChatList.setAdapter(mChatAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_chat_sendText:
                mImm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
                break;
            case R.id.list_chat:
                mImm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            default:
                break;
        }
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(mSendTextEt.length()==0){
            mSendtBtn.setImageResource(R.drawable.ic_send_default);
        }else{
            mSendtBtn.setImageResource(R.drawable.ic_send_light);
        }
    }
}
