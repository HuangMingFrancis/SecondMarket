package com.ketangpai.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ExpandableListView;

import com.ketangpai.activity.ChatActivity;
import com.ketangpai.adapter.MessageExAdapter;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.nan.ketangpai.R;

import java.util.ArrayList;

/**
 * Created by nan on 2016/3/15.
 */
public class MarketFragment extends BaseFragment implements ExpandableListView.OnChildClickListener {

    //view
    ExpandableListView mMessageExList;

    //adpter
    MessageExAdapter mMessageExAdapter;

    //变量
    ArrayList<String> mGroupNames;
    ArrayList<ArrayList<String>> mGroupItemUsers;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView() {
        mMessageExList = (ExpandableListView) view.findViewById(R.id.exlist_messagme);
        initMessageExList();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mMessageExList.setOnChildClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    private void initMessageExList() {
        mGroupNames = new ArrayList<>();
        mGroupItemUsers = new ArrayList<>();
        mGroupNames=getArrayList(getResources().getStringArray(R.array.first_level));

        mGroupItemUsers.add(getArrayList(getResources().getStringArray(R.array.electronic_product)));
        mGroupItemUsers.add(getArrayList(getResources().getStringArray(R.array.cloth_product)));
        mGroupItemUsers.add(getArrayList(getResources().getStringArray(R.array.book_product)));
        mGroupItemUsers.add(getArrayList(getResources().getStringArray(R.array.makeup_product)));
        mGroupItemUsers.add(getArrayList(getResources().getStringArray(R.array.ornaments_product)));
        mGroupItemUsers.add(getArrayList(getResources().getStringArray(R.array.bags_product)));
        mMessageExAdapter = new MessageExAdapter(mContext, mGroupNames, mGroupItemUsers);
        mMessageExList.setAdapter(mMessageExAdapter);

    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Intent intent=new Intent(mContext, ChatActivity.class);
        intent.putExtra("type",mGroupItemUsers.get(groupPosition).get(childPosition));
        intent.putExtra("fragment_type",1);
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("goods_type",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("goods_type",mGroupItemUsers.get(groupPosition).get(childPosition));
        editor.commit();
        startActivity(intent);
        return true;
    }

    private ArrayList getArrayList(String[] strings){
        ArrayList<String > strings1=new ArrayList<>();
        for (int i=0;i<strings.length;i++)
            strings1.add(strings[i]);
        return strings1;
    }
}
