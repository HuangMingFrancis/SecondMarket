package com.ketangpai.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ketangpai.base.Configs;
import com.ketangpai.base.DrawerBaseActivity;
import com.ketangpai.fragment.EditAccountFragment;
import com.ketangpai.fragment.ReleaseFragment;
import com.ketangpai.fragment.TAccountFragment;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.FileTools;
import com.ketangpai.utils.OkHttpClientManager;
import com.ketangpai.utils.SelectHeadTools;
import com.squareup.okhttp.Request;

import java.io.File;
import java.io.IOException;

/**
 * Created by Francis on 2016/4/25.
 */
public class EditAccountActivity extends DrawerBaseActivity {
    private EditAccountFragment editAccountFragment=new EditAccountFragment();
    private Uri photoUri = null;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_base_nevigation;
    }

    @Override
    protected Fragment getLayoutFragment() {
        return editAccountFragment;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        initUri();
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
        getSupportActionBar().setTitle("编辑个人信息");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_account_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit:
                if (editAccountFragment.getUser_tel().equals("")||editAccountFragment.getUser_name().equals(""))
                {
                    Toast.makeText(mContext,"用户名或手机号码不能为空",Toast.LENGTH_SHORT).show();
                    break;
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                builder.setTitle("编辑");
                builder.setMessage("是否确认修改");
                builder.setNegativeButton("取消",null);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editAccout();
                    }
                });
                builder.create().show();
                Log.i("ming","user_name:  "+editAccountFragment.getUser_name()+"  user_tel:　"+editAccountFragment.getUser_tel());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //照片返回
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case Configs.SystemPicture.PHOTO_REQUEST_TAKEPHOTO: // 拍照
                SelectHeadTools.startPhotoZoom(this,photoUri, 600);
                break;
            case Configs.SystemPicture.PHOTO_REQUEST_GALLERY://相册获取
                if (data==null)
                    return;
                SelectHeadTools.startPhotoZoom(this, data.getData(), 600);
                break;
            case Configs.SystemPicture.PHOTO_REQUEST_CUT:  //接收处理返回的图片结果
                if (data==null)
                    return;
                Bitmap bit = data.getExtras().getParcelable("data");
                File file = FileTools.getFileByUri(this,photoUri);
                editAccountFragment.setImageHead(bit);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    //初始化照片路径
    private void initUri(){
        if(!FileTools.hasSdcard()){
            Toast.makeText(this,"没有找到SD卡，请检查SD卡是否存在",Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            photoUri = FileTools.getUriByFileDirAndFileName(Configs.SystemPicture.SAVE_DIRECTORY, Configs.SystemPicture.SAVE_PIC_NAME);
        } catch (IOException e) {
            Toast.makeText(this, "创建文件失败。", Toast.LENGTH_SHORT).show();
            return;
        }
    }
    //修改用户信息
    private void editAccout(){
        OkHttpClientManager.postAsyn(Configs.EDIT_ACCOUNT, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(mContext,Configs.URLERROR,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                if (response.equals("1")){
                    Toast.makeText(mContext,"修改成功",Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences=getSharedPreferences("user",0);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("user_head",editAccountFragment.getUser_head());
                    editor.putString("user_name",editAccountFragment.getUser_name());
                    editor.putString("user_tel",editAccountFragment.getUser_tel());
                    editor.commit();
                    finish();
                }
                Log.i("ming","response:　"+response);
            }
        },new OkHttpClientManager.Param[]{
                new OkHttpClientManager.Param("user_name",editAccountFragment.getUser_name()),
                new OkHttpClientManager.Param("user_tel",editAccountFragment.getUser_tel()),
                new OkHttpClientManager.Param("user_head",editAccountFragment.getUser_head()),
                new OkHttpClientManager.Param("user_id",getSharedPreferences("user",0).getString("user_id","0"))
        });
    }

}
