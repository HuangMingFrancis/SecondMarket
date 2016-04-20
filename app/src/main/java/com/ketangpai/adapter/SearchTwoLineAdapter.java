package com.ketangpai.adapter;

import android.content.Context;
import android.widget.TextView;

import com.ketangpai.base.BaseAdapter;
import com.ketangpai.nan.ketangpai.R;

import java.util.List;

/**
 * Created by nan on 2016/3/21.
 */
public class SearchTwoLineAdapter extends BaseAdapter {
    public SearchTwoLineAdapter(Context mContext, List mDataList) {
        super(mContext, mDataList);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_search_towline;
    }

    @Override
    protected void bindData(ViewHolder holder, int position, Object item) {
        //view的初始化
        TextView title= (TextView) holder.getViewById(R.id.tv_item_search_title);
        TextView content= (TextView) holder.getViewById(R.id.tv_item_search_content);

        //view的赋值

    }


}
