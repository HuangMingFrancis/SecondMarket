package com.ketangpai.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.ketangpai.base.BaseAdapter;
import com.ketangpai.listener.OnItemClickListener;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.view.MyPopupMenu;

import java.util.List;

/**
 * Created by nan on 2016/3/14.
 */
public class CourseSMainCourseAdapter extends BaseAdapter<String> implements PopupMenu.OnMenuItemClickListener {


    public CourseSMainCourseAdapter(Context mContext, List<String> mDataList) {
        super(mContext, mDataList);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_main_student_course;
    }


    @Override
    protected void bindData(ViewHolder holder, int position, String s) {
        //初始化view
        TextView CourseName = (TextView) holder.getViewById(R.id.tv_item_courseName);
        TextView CourseCode = (TextView) holder.getViewById(R.id.tv_item_courseCode);
        TextView StudentName = (TextView) holder.getViewById(R.id.tv_item_StudentName);
        ImageView CourseEdit = (ImageView) holder.getViewById(R.id.img_item_courseMore);

        //设置事件
        //courseEdit点击事情

        final MyPopupMenu mPopupMenu = new MyPopupMenu(mContext, CourseEdit, R.menu.student_courese_edit_menu);
        mPopupMenu.setOnMenuItemClickListener(this);
        CourseEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupMenu.show();

            }
        });

        //初始化view的值


    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return true;
    }


}
