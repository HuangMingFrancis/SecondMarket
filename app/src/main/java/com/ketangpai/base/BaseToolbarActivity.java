package com.ketangpai.base;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.ketangpai.nan.ketangpai.R;

/**
 * Created by nan on 2016/3/27.
 */
public abstract class BaseToolbarActivity extends BaseActivity {

    //view
    Toolbar mToolbar;




    @Override
    protected void initView() {
        initToolbar();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_main_container, getLayoutFragment()).commit();
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

    protected abstract  Fragment getLayoutFragment();

    protected  void initToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    };
}
