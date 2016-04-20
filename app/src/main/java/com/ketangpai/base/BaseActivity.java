package com.ketangpai.base;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Toast;

import com.ketangpai.utils.ActivityCollector;
import com.ketangpai.nan.ketangpai.R;


public abstract class BaseActivity extends AppCompatActivity {


    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentViewId());
        initVariables();
        initView();
        initData();
        initListener();
        loadData();

    }

    protected abstract int getContentViewId();


    protected void initVariables() {
        mContext = this;
        ActivityCollector.addActivity(this);
    }

    ;


    protected abstract void initView();

    ;


    protected abstract void initData()


            ;


    protected abstract void initListener()


            ;

    protected abstract void loadData();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    protected void showToast(String text, int duration) {
        Toast.makeText(mContext, text, duration).show();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}
