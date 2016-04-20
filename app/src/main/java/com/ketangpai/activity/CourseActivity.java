package com.ketangpai.activity;

import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.ketangpai.base.DrawerBaseActivity;
import com.ketangpai.fragment.CourseFragment;
import com.ketangpai.nan.ketangpai.R;

/**
 * Created by nan on 2016/3/16.
 */
public class CourseActivity extends DrawerBaseActivity {


    //view


    //变量
    private String mCourseName;
    private int resultIntent;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_base_nevigation;
    }

    @Override
    protected void initVariables() {
        super.initVariables();
        if (getIntent().getStringExtra("course") != null) {
            mCourseName = getIntent().getStringExtra("course");

        }

    }

    @Override
    protected Fragment getLayoutFragment() {
        return new CourseFragment();
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
        setSelectItemView(getIntent().getIntExtra("position", -1));
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            default:
                break;
        }
    }


    @Override
    public void onItemClick(View view, int position) {
        mCourseName = ((TextView) view.findViewById(R.id.tv_item_drawerCourseName)).getText().toString() + position;
        getSupportActionBar().setTitle(mCourseName);
        mDrawerContainer.closeDrawer(Gravity.LEFT);
        setSelectItemView(position);
    }


    public void setSelectItemView(int positon) {
        mDrawerCourseList.smoothScrollToPosition(positon);
        mNevigationCourseAdapter.setSelectPosition(positon);
        mNevigationCourseAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        getSupportActionBar().setTitle(mCourseName);
    }


}
