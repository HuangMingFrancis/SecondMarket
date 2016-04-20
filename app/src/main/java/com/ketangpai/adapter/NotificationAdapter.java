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
public class NotificationAdapter extends BaseAdapter {
    public NotificationAdapter(Context mContext, List mDataList) {
        super(mContext, mDataList);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_notification;
    }

    @Override
    protected void bindData(ViewHolder holder, int position, Object item) {
        TextView courseName = (TextView) holder.getViewById(R.id.item_notification_courseName);
        TextView content = (TextView) holder.getViewById(R.id.item_notification_content);
    }

}
