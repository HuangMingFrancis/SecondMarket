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
import com.ketangpai.listener.OnItemClickListener;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.view.MyPopupMenu;

import java.util.List;

/**
 * Created by nan on 2016/3/14.
 */
public class CourseTMainCourseAdapter extends BaseAdapter<String> implements PopupMenu.OnMenuItemClickListener {


    public CourseTMainCourseAdapter(Context mContext, List<String> mDataList) {
        super(mContext, mDataList);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_main_teacher_course;
    }


    @Override
    protected void bindData(ViewHolder holder, int position, String s) {
        //初始化view
        TextView CourseName = (TextView) holder.getViewById(R.id.tv_item_courseName);
        TextView StudentCount = (TextView) holder.getViewById(R.id.tv_item_StudentCount);
        TextView CourseCode = (TextView) holder.getViewById(R.id.tv_item_courseCode);
        ImageView CourseEdit = (ImageView) holder.getViewById(R.id.img_item_courseMore);

        //设置view的事件
        //CourseCode的点击事件
        final MyPopupMenu mCourseCodePopupMenu = new MyPopupMenu(mContext, CourseCode, R.menu.teacher_courese_code_menu);
        mCourseCodePopupMenu.setOnMenuItemClickListener(this);
        CourseCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCourseCodePopupMenu.show();

            }
        });
        //CourseEdit的点击事件
        final MyPopupMenu mCourseEditPopupMenu = new MyPopupMenu(mContext, CourseEdit, R.menu.teacher_courese_edit_menu);
        mCourseEditPopupMenu.setOnMenuItemClickListener(this);
        CourseEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCourseEditPopupMenu.show();

            }
        });

        //初始化view的值

    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_delete:
                break;
            case R.id.item_menu_edit:
                break;
            case R.id.item_menu_stopCode:
                break;
            case R.id.item_menu_resetCode:
                break;

            default:
                break;
        }
        return true;
    }


}
