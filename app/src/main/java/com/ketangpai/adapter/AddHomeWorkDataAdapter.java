package com.ketangpai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ketangpai.base.BaseAdapter;
import com.ketangpai.nan.ketangpai.R;

import java.util.List;

/**
 * Created by nan on 2016/3/27.
 */
public class AddHomeWorkDataAdapter extends BaseAdapter {

    public AddHomeWorkDataAdapter(Context mContext, List mDataList) {
        super(mContext, mDataList);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_add_homework_data;
    }



    @Override
    protected void bindData(ViewHolder holder, int position, Object item) {
        TextView fileName= (TextView) holder.getViewById(R.id.data_fileName);
        TextView fileSize= (TextView) holder.getViewById(R.id.data_fileSize);



    }


}
