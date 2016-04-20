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
public class CourseTExamAdapter extends BaseAdapter<String> implements PopupMenu.OnMenuItemClickListener {


    public CourseTExamAdapter(Context mContext, List<String> mDataList) {
        super(mContext, mDataList);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_t_exam;
    }


    @Override
    protected void bindData(ViewHolder holder, int position, String s) {
        //初始化view
        TextView mPublishTimeText = (TextView) holder.getViewById(R.id.tv_t_exam_publishTime);
        TextView mTitleText = (TextView) holder.getViewById(R.id.tv_t_exam_title);
        TextView mContentText = (TextView) holder.getViewById(R.id.tv_t_exam_content);
        TextView mCheckCountText = (TextView) holder.getViewById(R.id.tv_t_exam_checkCount);
        TextView mNoCheckCountText = (TextView) holder.getViewById(R.id.tv_t_exam_noCheckCount);
        TextView mNoPostCountText = (TextView) holder.getViewById(R.id.tv_t_exam_noPostCount);
        ImageView mEditImg = (ImageView) holder.getViewById(R.id.img_t_exam_edit);

        //为view设置事件
        //mEditImg的点击事件
        final MyPopupMenu mEditPopupMenu = new MyPopupMenu(mContext, mEditImg, R.menu.t_exam_edit_menu);
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
            case R.id.t_exam_edit_menu:
                break;
            case R.id.t_exam_delete_menu:
                break;

            default:
                break;
        }
        return true;
    }


}
