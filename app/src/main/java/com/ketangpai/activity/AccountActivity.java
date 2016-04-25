package com.ketangpai.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ketangpai.base.DrawerBaseActivity;
import com.ketangpai.fragment.EditAccountFragment;
import com.ketangpai.fragment.TAccountFragment;
import com.ketangpai.nan.ketangpai.R;

/**
 * Created by nan on 2016/3/21.
 */
public class AccountActivity extends DrawerBaseActivity {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_base_nevigation;
    }

    @Override
    protected Fragment getLayoutFragment() {
        return new TAccountFragment();
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        getSupportActionBar().setTitle("个人信息");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.acount_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit:
                startActivity(new Intent(mContext, EditAccountActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
