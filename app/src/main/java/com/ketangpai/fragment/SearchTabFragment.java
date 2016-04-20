package com.ketangpai.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ketangpai.adapter.SearchTwoLineAdapter;
import com.ketangpai.adapter.SearchSingleLineAdapter;
import com.ketangpai.base.BaseAdapter;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.nan.ketangpai.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nan on 2016/3/21.
 */
public class SearchTabFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    //view
    RecyclerView mSearchTabList;
    SwipeRefreshLayout mSwipeRefreshLayout;

    //adpter
    BaseAdapter mTabAdapter;

    //变量
    List mTabContents;
    private int position;

    public void setPosition(int position) {
        this.position = position;
    }

    public static SearchTabFragment newInstance(int position) {


        SearchTabFragment fragment = new SearchTabFragment();
        fragment.setPosition(position);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_tab;
    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.fresh_search_tab);
        initSearchTabList();
    }

    @Override
    protected void initData() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        for (int i = 0; i <10; ++i) {
            mTabAdapter.addItem(i,"11111");
        }

    }

    @Override
    protected void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void loadData() {

    }

    private void initSearchTabList() {
        mSearchTabList= (RecyclerView) view.findViewById(R.id.list_search_tab);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mSearchTabList.setLayoutManager(linearLayoutManager);
        mTabContents=new ArrayList();
        changeTabAdaterByPosition(position,mContext);
    }



    public void changeTabAdaterByPosition(int position, Context context) {
        switch (position) {
            case 0:
                mTabAdapter = new SearchSingleLineAdapter(context, mTabContents);
                break;
            case 1:
                mTabAdapter = new SearchTwoLineAdapter(context, mTabContents);
                break;
            case 2:
                mTabAdapter = new SearchTwoLineAdapter(context, mTabContents);
                break;
            case 3:
                mTabAdapter = new SearchTwoLineAdapter(context, mTabContents);
                break;
            default:
                break;
        }
        mSearchTabList.setAdapter(mTabAdapter);

    }

    @Override
    public void onRefresh() {

    }
}
