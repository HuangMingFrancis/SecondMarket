package com.ketangpai.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.ketangpai.entity.User;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.OkHttpClientManager;
import com.squareup.okhttp.Request;

public class LoginActivity1 extends AppCompatActivity implements View.OnClickListener, GestureDetector.OnGestureListener {
    private static final int LOGING=100;
    private static final int REGISTER=101;
    private TextView tv_login, tv_register;
    private ViewFlipper vf_login;
    private GestureDetector detector; // 手势检测
    private Animation leftInAnimation;
    private Animation leftOutAnimation;
    private Animation rightInAnimation;
    private Animation rightOutAnimation;
    private Context mContext;
    private User user=null;
    private AlertDialog dialog_login,dialog_register,dialog_confirm_login;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case LOGING:
                    Log.i("ming","msg: "+msg.obj.toString());
                    if (msg.obj!=null){
                        User user1=new Gson().fromJson(msg.obj.toString(),User.class);
                        if (user1!=null){
                            user=user1;
                            saveUser();
                            startActivity(new Intent(mContext, MainActivity.class));
                            dialog_login.dismiss();
                            finish();
                        }
                        else{
                            Toast.makeText(getApplication(),"用户名或密码错误",Toast.LENGTH_SHORT).show();
                        }
                    }

                    break;
                case REGISTER:
                    if (msg.obj.toString().equals("1")){
                        dialog_register.dismiss();
                        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                        builder.setTitle("注册成功");
                        builder.setMessage("是否立刻登录");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        saveUser();
                                        startActivity(new Intent(mContext, MainActivity.class));
                                        finish();
                                    }
                                }
                        );
                        builder.setNegativeButton("取消", null);
                        dialog_confirm_login=builder.create();
                        dialog_confirm_login.show();
                    }
                    Log.i("ming","reponse:"+msg.obj.toString());
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        initViews();
        initDatas();
        initListerer();
    }

    private void initViews() {
        mContext = this;
        vf_login = (ViewFlipper) findViewById(R.id.vf_login);
        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_register = (TextView) findViewById(R.id.tv_register);
    }

    private void initDatas() {
        detector = new GestureDetector(this);

        vf_login.addView(getImageView(R.drawable.beijing1));
        vf_login.addView(getImageView(R.drawable.beijing2));
        vf_login.addView(getImageView(R.drawable.beijing3));

        // 动画效果
        leftInAnimation = AnimationUtils.loadAnimation(this, R.anim.left_in);
        leftOutAnimation = AnimationUtils.loadAnimation(this, R.anim.left_out);
        rightInAnimation = AnimationUtils.loadAnimation(this, R.anim.right_in);
        rightOutAnimation = AnimationUtils
                .loadAnimation(this, R.anim.right_out);
    }

    private void initListerer() {
        tv_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                showLoginDialog();
                break;
            case R.id.tv_register:
                showRegisterDialog();
                break;
        }
    }

    /**
     * 登录的dialog
     **/
    private void showLoginDialog() {
        dialog_login = new AlertDialog.Builder(mContext).create();
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_login, null);
        Button btn_login = (Button) view.findViewById(R.id.btn_login);
        final EditText et_username=(EditText)view.findViewById(R.id.et_username);
        final EditText et_userpsw=(EditText)view.findViewById(R.id.et_userpsw);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLogin(et_username.getText().toString(),et_userpsw.getText().toString());
            }
        });
        dialog_login.setView(view);
        dialog_login.show();
    }

    /**
     * 注册的dialog
     **/
    private void showRegisterDialog() {
        dialog_register = new AlertDialog.Builder(mContext).create();
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_register, null);
        Button btn_register = (Button) view.findViewById(R.id.btn_register);
        final EditText et_username=(EditText)view.findViewById(R.id.et_username);
        final EditText et_userpsw=(EditText)view.findViewById(R.id.et_userpsw);
        final EditText et_userpsw_again=(EditText)view.findViewById(R.id.et_userpsw_again);
        final EditText et_user_tel=(EditText)view.findViewById(R.id.et_user_tel);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRegister(et_username.getText().toString(),et_userpsw.getText().toString(),
                        et_userpsw_again.getText().toString(),et_user_tel.getText().toString());
//                startActivity(new Intent(mContext, MainActivity.class));
//                finish();
            }
        });
        dialog_register.setView(view);
        dialog_register.show();
    }

    /**
     * 得到imageview
     **/
    private ImageView getImageView(int id) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(id);
        return imageView;
    }

    private void setLogin(String name,String psw) {
        if (name.equals("")||psw.equals("")){
            Toast.makeText(this,"用户名和密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        String url="http://192.168.253.1:8080/Taobao/login?name="+name+"&password="+psw;
//        OkHttpClientManager.getAsyn(url, new OkHttpClientManager.ResultCallback<String>() {
//            @Override
//            public void onError(Request request, Exception e) {
//
//            }
//
//            @Override
//            public void onResponse(String response) {
//                Message message=new Message();
//                message.what=LOGING;
//                message.obj=response;
//                handler.sendMessage(message);
//            }
//        });

        OkHttpClientManager.postAsyn("http://192.168.253.1:8080/Taobao/login", new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                Message message=new Message();
                message.what=LOGING;
                message.obj=response;
                handler.sendMessage(message);
            }
        },new OkHttpClientManager.Param[]{
                new OkHttpClientManager.Param("name",name),
                new OkHttpClientManager.Param("password",psw),
        });

    }

    private void setRegister(String name,String psw,String psw_again,String tel){
        if (name.equals("")||psw.equals("")||tel.equals("")){
            Toast.makeText(getApplication(),"用户名或密码或手机号码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (psw_again.equals("")){
            Toast.makeText(getApplication(),"确定密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!psw.equals(psw_again)){
            Toast.makeText(getApplication(),"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
            return;
        }
        String url="http://192.168.253.1:8080/Taobao/register?name="+name+"&password="+psw+"&tel="+tel;
//        user.setName(name);
//        user.setPassword(psw);
//        user.setTel(tel);
        user=new User(0,name,psw,tel);
//        OkHttpClientManager.getAsyn(url, new OkHttpClientManager.ResultCallback<String>() {
//            @Override
//            public void onError(Request request, Exception e) {
//
//            }
//
//            @Override
//            public void onResponse(String response) {
//                Message message=new Message();
//                message.what=REGISTER;
//                message.obj=response;
//                handler.sendMessage(message);
//            }
//        });
        OkHttpClientManager.postAsyn("http://192.168.253.1:8080/Taobao/register", new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                Message message=new Message();
                message.what=REGISTER;
                message.obj=response;
                handler.sendMessage(message);
            }
        },new OkHttpClientManager.Param[]{
            new OkHttpClientManager.Param("name",name),
                new OkHttpClientManager.Param("password",psw),
                new OkHttpClientManager.Param("tel",tel)
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.detector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() > 120) {
            vf_login.setInAnimation(leftInAnimation);
            vf_login.setOutAnimation(leftOutAnimation);
            vf_login.showNext();// 向右滑动
            return true;
        } else if (e1.getX() - e2.getY() < -120) {
            vf_login.setInAnimation(rightInAnimation);
            vf_login.setOutAnimation(rightOutAnimation);
            vf_login.showPrevious();// 向左滑动
            return true;
        }
        return false;
    }
    //登录成功后保存用户信息
    private void saveUser(){
        SharedPreferences sharedPreferences=getSharedPreferences("user",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if (user!=null){
            editor.putString("user_name",user.getName());
            editor.putString("user_psw",user.getPassword());
            editor.putString("user_tel",user.getTel());
            editor.commit();
        }
    }

}
