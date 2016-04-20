package com.ketangpai.fragment;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ketangpai.adapter.CourseTabsPagerAdapter;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.view.SlidingTabLayout;

/**
 * Created by nan on 2016/3/19.
 */
public class CourseFragment extends BaseFragment {
    //view
    private SlidingTabLayout mTabs;
    private ViewPager mTabViewPager;

    //adpter
    private CourseTabsPagerAdapter mCourseTabsPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_course;
    }

    @Override
    protected void initView() {
        initTabsLayout();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {

    }

    private void initTabsLayout() {
        mTabs = (SlidingTabLayout) view.findViewById(R.id.stl_course_tabs);
        mTabViewPager = (ViewPager) view.findViewById(R.id.vp_course_content);


        mCourseTabsPagerAdapter = new CourseTabsPagerAdapter(((AppCompatActivity) mContext).getSupportFragmentManager(), mContext);
        mTabViewPager.setAdapter(mCourseTabsPagerAdapter);
        mTabs.setViewPager(mTabViewPager);
        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.WHITE;
            }

            @Override
            public int getDividerColor(int position) {
                return 0;
            }
        });



    }
}
