package com.ketangpai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
 * Created by nan on 2016/3/17.
 */
public class CourseNoticeAdapter extends BaseAdapter<String> implements PopupMenu.OnMenuItemClickListener {


    public CourseNoticeAdapter(Context mContext, List<String> mDataList) {
        super(mContext, mDataList);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_notice;
    }


    @Override
    protected void bindData(ViewHolder holder, int position, String s) {
        TextView mNoticeTitleText = (TextView) holder.getViewById(R.id.tv_notice_title);
        TextView mNoticeTimeText = (TextView) holder.getViewById(R.id.tv_notice_publishTime);
        TextView mNoticeContentText = (TextView) holder.getViewById(R.id.tv_notice_content);
        ImageView mNoticeEdit = (ImageView) holder.getViewById(R.id.img_notice_edit);

        //设置点击事件
        final MyPopupMenu mNoticeEditPopupMenu = new MyPopupMenu(mContext, mNoticeEdit, R.menu.notice_edit_menu);
        mNoticeEditPopupMenu.setOnMenuItemClickListener(this);
        mNoticeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNoticeEditPopupMenu.show();
            }
        });

        //初始化值

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notice_edit_menu:
                break;
            case R.id.notice_delete_menu:
                break;

            default:
                break;
        }
        return true;
    }



}
