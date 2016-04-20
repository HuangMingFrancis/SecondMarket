package com.ketangpai.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.ketangpai.activity.AddHomeWorkActivity;
import com.ketangpai.adapter.CourseDataAdapter;
import com.ketangpai.adapter.CourseNoticeAdapter;
import com.ketangpai.adapter.CourseTExamAdapter;
import com.ketangpai.adapter.CourseTHomeworkAdapter;
import com.ketangpai.base.BaseAdapter;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.listener.OnItemClickListener;
import com.ketangpai.nan.ketangpai.R;
import com.shamanland.fab.ShowHideOnScroll;

import java.util.List;

/**
 * Created by nan on 2016/3/20.
 */
public class CourseTabFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, OnItemClickListener, View.OnClickListener {

    //view
    FloatingActionButton mPublishBtn;
    RecyclerView mTabList;
    SwipeRefreshLayout mSwipeRefreshLayout;
    LinearLayout mllSimple, mllGroup;


    //adapter
    private BaseAdapter mTabAdapter;

    //变量
    private List mTabContents;
    private int mPosition;
    private Animation mAddCloseAnim, mAddOpenAnim;


    public void setPosition(int position) {
        this.mPosition = position;
    }

    public static CourseTabFragment newInstance(int positon) {
        CourseTabFragment fragment = new CourseTabFragment();
        fragment.setPosition(positon);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_course_tab;
    }

    @Override
    protected void initView() {
        mPublishBtn = (FloatingActionButton) view.findViewById(R.id.btn_course_tab_publish);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fresh_course_tab);
        mTabList = (RecyclerView) view.findViewById(R.id.list_course_tab);
        initTabList();

    }

    @Override
    protected void initData() {
        initAnim();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        for (int i = 0; i < 10; ++i) {
            mTabAdapter.addItem(i, "ssss");
        }
    }

    @Override
    protected void initListener() {

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mTabAdapter.setOnItemClickListener(this);
        mPublishBtn.setOnClickListener(this);
        mTabList.setOnTouchListener(new ShowHideOnScroll(mPublishBtn));
    }

    @Override
    protected void loadData() {

    }

    private void initTabList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mTabList.setLayoutManager(linearLayoutManager);
        changeTabAdaterByPosition(mPosition, mContext);
    }

    public void changeTabAdaterByPosition(int position, Context context) {
        switch (position) {
            case 0:
                mTabAdapter = new CourseTHomeworkAdapter(context, mTabContents);
                break;
            case 1:
                mTabAdapter = new CourseDataAdapter(context, mTabContents);
                break;
            case 2:
                mTabAdapter = new CourseNoticeAdapter(context, mTabContents);
                break;
            case 3:
                mTabAdapter = new CourseTExamAdapter(context, mTabContents);
                break;


            default:
                break;
        }
        mTabList.setAdapter(mTabAdapter);

    }


    private void initAnim() {
        mAddCloseAnim = AnimationUtils.loadAnimation(mContext, R.anim.fab_rotate_close);
        mAddOpenAnim = AnimationUtils.loadAnimation(mContext, R.anim.fab_rotate_open);

        mAddOpenAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mPublishBtn.startAnimation(mAddCloseAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }




    @Override
    public void onRefresh() {

    }

    @Override
    public void onItemClick(View view, int position) {
        switch (mPosition) {

            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_course_tab_publish) {
            mPublishBtn.startAnimation(mAddOpenAnim);
            switch (mPosition) {
                case 0:
                    startActivity(new Intent(mContext, AddHomeWorkActivity.class));
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;

                default:
                    break;
            }
        }

    }
}
