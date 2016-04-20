package com.ketangpai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ketangpai.base.BaseAdapter;
import com.ketangpai.nan.ketangpai.R;

import java.util.List;

/**
 * Created by nan on 2016/3/14.
 */
public class NevigationCourseAdapter extends BaseAdapter<String> {

    //选中的position
    private int selectPosition=-1;


    public NevigationCourseAdapter(Context mContext, List<String> mDataList) {
        super(mContext, mDataList);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_main_nevigation_course;
    }

    @Override
    protected void bindData(ViewHolder holder, int position, String s) {

        //初始化view
        TextView courseName = (TextView) holder.getViewById(R.id.tv_item_drawerCourseName);



        //初始化view的值
        if(position==getSelectPosition()){
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryLight));
        }else{
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(android.R.color.white));

        }
    }


    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }


}
