package com.ketangpai.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ketangpai.activity.ChatActivity;
import com.ketangpai.activity.MainActivity;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.base.Configs;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.FileTools;
import com.ketangpai.utils.OkHttpClientManager;
import com.ketangpai.utils.SelectHeadTools;
import com.ketangpai.view.GridViewForScrollView;
import com.squareup.okhttp.Request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nan on 2016/3/15.
 */
public class ReleaseFragment extends BaseFragment implements ExpandableListView.OnChildClickListener,
        View.OnClickListener,AdapterView.OnItemSelectedListener{
    public static final int INSERTGOODS=100;
    public static final int INSERTGOODSIMGS=101;

    private Spinner spin_type,spin_second_type;
    private EditText et_goods_name,et_goods_price,et_goods_des;
    private GridViewForScrollView gv_img;
    private Uri photoUri = null;
    private ArrayList<ImageView> imageViewArrayList;
    private ArrayList<Bitmap> bitmapArrayList;
    private BaseAdapter imgAdapter;
    private ScrollView sv_release;
    private TextView tv_add_image;
    private Button btn_release;
    private String goods_category_no;
    private String[] second_type;
    private String[] first_type;
    private Map<String,String[]> goods;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case INSERTGOODS:
                    if (msg.obj!=null){
                        if (msg.obj.toString().equals("1")){
                            Toast.makeText(getActivity(),"商品发布成功",Toast.LENGTH_SHORT).show();
                            et_goods_price.setText("");
                            et_goods_des.setText("");
                            et_goods_name.setText("");
                            ((MainActivity)getActivity()).changeFragment();
                        }
                    }
                    break;
                case INSERTGOODSIMGS:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_release;
    }

    @Override
    protected void initView() {
        spin_type=(Spinner)view.findViewById(R.id.spin_type);
        spin_second_type=(Spinner)view.findViewById(R.id.spin_second_type);
        et_goods_des=(EditText)view.findViewById(R.id.et_goods_des);
        et_goods_name=(EditText)view.findViewById(R.id.et_goods_name);
        et_goods_price=(EditText)view.findViewById(R.id.et_goods_price);
        gv_img=(GridViewForScrollView) view.findViewById(R.id.gv_img);
        sv_release=(ScrollView)view.findViewById(R.id.sv_release);
        tv_add_image=(TextView)view.findViewById(R.id.tv_add_image);
        btn_release=(Button)view.findViewById(R.id.btn_release);

        second_type=mContext.getResources().getStringArray(R.array.electronic_product);
//        setSpinSecondType();

    }

    @Override
    protected void initData() {
        imageViewArrayList=new ArrayList<>();
        bitmapArrayList=new ArrayList<>();

        initImgAdapter();

        gv_img.setAdapter(imgAdapter);
        goods=new HashMap<>();
        ArrayList<String[]> second_level=new ArrayList<>();
        first_type=mContext.getResources().getStringArray(R.array.first_level);
        second_level.add(mContext.getResources().getStringArray(R.array.electronic_product));
        second_level.add(mContext.getResources().getStringArray(R.array.cloth_product));
        second_level.add(mContext.getResources().getStringArray(R.array.book_product));
        second_level.add(mContext.getResources().getStringArray(R.array.makeup_product));
        second_level.add(mContext.getResources().getStringArray(R.array.ornaments_product));
        second_level.add(mContext.getResources().getStringArray(R.array.bags_product));

        for (int i=0;i<first_type.length;i++){
            goods.put(first_type[i],second_level.get(i));
        }



    }

    @Override
    protected void initListener() {
        tv_add_image.setOnClickListener(this);
        spin_type.setOnItemSelectedListener(this);
        spin_second_type.setOnItemSelectedListener(this);
        btn_release.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }
    private void initImgAdapter(){
        imgAdapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return bitmapArrayList.size();
            }

            @Override
            public Object getItem(int position) {
                return bitmapArrayList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView==null){
                    convertView=new ImageView(getActivity());
                }
                ((ImageView)convertView).setImageBitmap(bitmapArrayList.get(position));
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(300,300));
                convertView.setLayoutParams(params);
                return convertView;
            }
        };
    }
    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Intent intent=new Intent(mContext, ChatActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_add_image:
                showCamear();
                break;
            case R.id.btn_release:
                setRegister();
                break;
        }
    }

    public void setImageView(Bitmap bitmap){
        bitmapArrayList.add(bitmap);
        imgAdapter.notifyDataSetChanged();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spin_type:
                goods_category_no=first_type[position];
                for (Map.Entry<String, String[]> entry : goods.entrySet()) {
                    if (entry.getKey().equals(first_type[position]))
                        second_type=entry.getValue();
                }
                goods_category_no=second_type[0];
                setSpinSecondType();
                break;
            case R.id.spin_second_type:
                goods_category_no=second_type[position];
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void setRegister(){
        if (et_goods_name.getText().toString().equals("")){
            showToast("商品名称不能为空");
            return;
        }
        if (et_goods_price.getText().toString().equals("")){
            showToast("商品价格不能为空");
            return;
        }
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        builder.setTitle("发布商品");
        builder.setMessage("是否确定发布该商品");
        builder.setNegativeButton("取消",null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                ArrayList<String> images=new ArrayList<>();
                for (Bitmap bitmap:bitmapArrayList){
                    images.add(Configs.bitmapToBase64(bitmap));
                }
                OkHttpClientManager.postAsyn(Configs.RELEASE_GOODS, new OkHttpClientManager.ResultCallback<String>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Toast.makeText(getActivity(),Configs.URLERROR,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        Message message=new Message();
                        message.what=INSERTGOODS;
                        message.obj=response;
                        mHandler.sendMessage(message);
                    }
                },new OkHttpClientManager.Param[]{
                        new OkHttpClientManager.Param("goods_name",et_goods_name.getText().toString()),
                        new OkHttpClientManager.Param("goods_category_no",goods_category_no),
                        new OkHttpClientManager.Param("goods_price",et_goods_price.getText().toString()),
                        new OkHttpClientManager.Param("goods_des",et_goods_des.getText().toString()),
                        new OkHttpClientManager.Param("goods_publisher",mContext.getSharedPreferences("user",0).getString("user_name","")),
                        new OkHttpClientManager.Param("goods_publish_date",df.format(new Date())),
                        new OkHttpClientManager.Param("goods_trading_status","0"),
                        new OkHttpClientManager.Param("goods_trading_date","0"),
                        new OkHttpClientManager.Param("goods_imgs",new Gson().toJson(images)),
                        new OkHttpClientManager.Param("goods_publisher_id",mContext.getSharedPreferences("user",0).getString("user_id",""))
                });
            }
        });
        builder.create().show();

    }

    private void showToast(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

    private void setSpinSecondType(){
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item, second_type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_second_type.setAdapter(adapter);
    }


}
