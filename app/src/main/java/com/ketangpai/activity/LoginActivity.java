package com.ketangpai.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ketangpai.base.BaseActivity;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.ActivityCollector;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by nan on 2016/3/9.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    //    view
    private EditText mName, mPassword;
    private TextView mRegister;
    private Button mBtn_login;
    private CircleImageView mUserIconImg;

    //变量
    //软键盘
    private InputMethodManager mimm;
    //保存点击的时间
    private long exitTime;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initVariables() {
        super.initVariables();
    }

    @Override
    protected void initView() {
        initEtNameAndPassword();
        mBtn_login = (Button) findViewById(R.id.btn_login_login);
        mRegister = (TextView) findViewById(R.id.tv_login_register);
        mUserIconImg = (CircleImageView) findViewById(R.id.img_login_logo);
    }

    @Override
    protected void initData() {
        mimm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    protected void initListener() {
        mBtn_login.setOnClickListener(this);
        mName.setOnClickListener(this);
        mPassword.setOnClickListener(this);
        mRegister.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }


    //初始化nameEdittext和passwordEdittext


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_login:
                mimm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                startActivity(new Intent(mContext, MainActivity.class));
                finish();
                break;
            case R.id.et_login_name:
                mimm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
                break;
            case R.id.et_login_password:
                mimm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
                break;
            case R.id.tv_login_register:
                startChooseTypeDialog();
                mimm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            default:
                break;
        }
    }

    private void startChooseTypeDialog() {
        new AlertDialog.Builder(mContext).setTitle("注册").setNeutralButton("学生", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showRegisterDialog(0);
            }


        }).setPositiveButton("老师", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showRegisterDialog(1);
            }
        }).create().show();
    }

    private void initEtNameAndPassword() {

        //设置用户名左边drawable
        mName = (EditText) findViewById(R.id.et_login_name);
        Drawable nameDrawableLeft = getResources().getDrawable(R.drawable.icon_user_username);
        nameDrawableLeft.setBounds(0, 0, 45, 45);
        mName.setCompoundDrawables(nameDrawableLeft, null, null, null);
        mName.setCompoundDrawablePadding(20);

        mPassword = (EditText) findViewById(R.id.et_login_password);
        Drawable passwordDrawableLeft = getResources().getDrawable(R.drawable.icon_user_password);
        passwordDrawableLeft.setBounds(0, 0, 45, 45);
        mPassword.setCompoundDrawables(passwordDrawableLeft, null, null, null);
        mPassword.setCompoundDrawablePadding(20);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //双击退出程序
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                showToast("再按一次退出", 0);
                exitTime = System.currentTimeMillis();
                return true;
            } else {

                ActivityCollector.finishAllActivity();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showRegisterDialog(int type) {
        AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        View view;
        if (type == 0) {
            view = LayoutInflater.from(mContext).inflate(R.layout.dialog_register_student, null);

        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.dialog_register_teacher, null);
        }

        dialog.setView(view);
        dialog.show();

    }
}
