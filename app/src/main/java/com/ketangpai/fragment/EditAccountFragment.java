package com.ketangpai.fragment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ketangpai.base.BaseFragment;
import com.ketangpai.base.Configs;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.FileTools;
import com.ketangpai.utils.SelectHeadTools;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Francis on 2016/4/25.
 */
public class EditAccountFragment extends BaseFragment implements View.OnClickListener{
    private CircleImageView img_head;
    private Button btn_edit_head;
    private EditText et_user_name,et_user_tel;
    private Uri photoUri = null;
    String user_name,user_tel,user_head="";

    public String getUser_tel() {
        user_tel=et_user_tel.getText().toString();
        return user_tel;
    }

    public String getUser_head() {
        return user_head;
    }

    public String getUser_name() {
        user_name=et_user_name.getText().toString();
        return user_name;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_account;
    }

    @Override
    protected void initView() {
        img_head=(CircleImageView)view.findViewById(R.id.img_head);
        btn_edit_head=(Button)view.findViewById(R.id.btn_edit_head);
        et_user_name=(EditText) view.findViewById(R.id.et_user_name);
        et_user_tel=(EditText) view.findViewById(R.id.et_user_tel);
    }

    @Override
    protected void initData() {
        user_name=mContext.getSharedPreferences("user",0).getString("user_name","");
        user_tel=mContext.getSharedPreferences("user",0).getString("user_tel","");
        user_head=mContext.getSharedPreferences("user",0).getString("user_head","");
        et_user_tel.setText(user_tel);
        et_user_name.setText(user_name);
        if (!user_head.equals(""))
            img_head.setImageBitmap(Configs.base64ToBitmap(user_head));

    }

    @Override
    protected void initListener() {
        btn_edit_head.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_edit_head:
                showCamear();
                break;
        }
    }
    private void showCamear(){
        if(!FileTools.hasSdcard()){
            Toast.makeText(getActivity(),"没有找到SD卡，请检查SD卡是否存在",Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            photoUri = FileTools.getUriByFileDirAndFileName(Configs.SystemPicture.SAVE_DIRECTORY, Configs.SystemPicture.SAVE_PIC_NAME);
        } catch (IOException e) {
            Toast.makeText(getActivity(), "创建文件失败。", Toast.LENGTH_SHORT).show();
            return;
        }
        SelectHeadTools.openDialog(getActivity(),photoUri);
    }

    public void setImageHead(Bitmap bitmap){
        img_head.setImageBitmap(bitmap);
        user_head=Configs.bitmapToBase64(bitmap);
    }
}
