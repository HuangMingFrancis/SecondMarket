package com.ketangpai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ketangpai.base.BaseAdapter;
import com.ketangpai.nan.ketangpai.R;

import java.util.List;

/**
 * Created by nan on 2016/3/17.
 */
public class CourseDataAdapter extends BaseAdapter<String> {


    public CourseDataAdapter(Context mContext, List<String> mDataList) {
        super(mContext, mDataList);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_data;
    }

    @Override
    protected void bindData(ViewHolder holder, int position, String s) {
        TextView mFileNameText = (TextView) holder.getViewById(R.id.tv_share_fileName);
        ImageView mFilePicImg = (ImageView) holder.getViewById(R.id.img_share_picImg);
    }





}
