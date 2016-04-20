package com.ketangpai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.ketangpai.base.BaseAdapter;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.view.MyPopupMenu;

import java.util.List;

/**
 * Created by nan on 2016/3/16.
 */
public class CourseTHomeworkAdapter extends BaseAdapter<String> implements PopupMenu.OnMenuItemClickListener {


    public CourseTHomeworkAdapter(Context mContext, List<String> mDataList) {
        super(mContext, mDataList);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_t_homework;
    }


    @Override
    protected void bindData(ViewHolder holder, int position, String s) {
        //初始化view
        TextView mTypeText = (TextView) holder.getViewById(R.id.tv_t_homework_type);
        TextView mPublishTimeText = (TextView) holder.getViewById(R.id.tv_t_homework_publishTime);
        TextView mEndTimeText = (TextView) holder.getViewById(R.id.tv_t_homework_endTime);
        TextView mTitleText = (TextView) holder.getViewById(R.id.tv_t_homework_title);
        TextView mContentText = (TextView) holder.getViewById(R.id.tv_t_homework_content);
        TextView mAccessoryText = (TextView) holder.getViewById(R.id.tv_t_homework_accessory);
        TextView mCheckCountText = (TextView) holder.getViewById(R.id.tv_t_homework_checkCount);
        TextView mNoCheckCountText = (TextView) holder.getViewById(R.id.tv_t_homework_noCheckCount);
        TextView mNoPostCountText = (TextView) holder.getViewById(R.id.tv_t_homework_noPostCount);
        ImageView mEditImg = (ImageView) holder.getViewById(R.id.img_t_home_edit);

        //设置事件
        //mEditImg点击事件
        final MyPopupMenu mEditPopupMenu;
        mEditPopupMenu = new MyPopupMenu(mContext, mEditImg, R.menu.t_homework_edit_menu);
        mEditPopupMenu.setOnMenuItemClickListener(this);
        mEditImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditPopupMenu.show();
            }
        });

        //初始化view的值


    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.t_homework_edit_menu:
                break;
            case R.id.t_homework_delete_menu:
                break;

            default:
                break;
        }
        return true;
    }


}
