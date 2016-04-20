package com.ketangpai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ketangpai.base.BaseAdapter;
import com.ketangpai.nan.ketangpai.R;

import java.util.List;

/**
 * Created by nan on 2016/3/21.
 */
public class SearchSingleLineAdapter extends BaseAdapter {

    public SearchSingleLineAdapter(Context mContext, List mDataList) {
        super(mContext, mDataList);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_search_singleline;
    }

    @Override
    protected void bindData(ViewHolder holder, int position, Object item) {
        //view初始化
        TextView textView = (TextView) holder.getViewById(R.id.tv_search_single_line_text);

        //view赋值
    }


}
