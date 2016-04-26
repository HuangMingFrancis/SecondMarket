package com.ketangpai.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ketangpai.base.BaseActivity;
import com.ketangpai.base.Configs;
import com.ketangpai.entity.GoodsInfo;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.OkHttpClientManager;
import com.ketangpai.view.GridViewForScrollView;
import com.squareup.okhttp.Request;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nan on 2016/3/18.
 */
public class GoodDesActivity extends BaseActivity implements View.OnClickListener{
    private GoodsInfo goodsInfo;
    private TextView tv_goods_name,tv_title,tv_goods_pricce,tv_goods_des,tv_goods_publisher,tv_goods_publish_date,tv_connect_publish;
    private ImageView iv_back;
    private Button btn_buy;
    private GridViewForScrollView gv_img;
    private BaseAdapter imgAdapter;
    private ArrayList<Bitmap> bitmapArrayList;
    @Override
    protected int getContentViewId() {
        return R.layout.fragment_goods_des;
    }

    @Override
    protected void initView() {
        tv_goods_name=(TextView)findViewById(R.id.tv_goods_name);
        tv_goods_pricce=(TextView)findViewById(R.id.tv_goods_price);
        tv_goods_des=(TextView)findViewById(R.id.tv_goods_des);
        tv_goods_publisher=(TextView)findViewById(R.id.tv_goods_publisher);
        tv_goods_publish_date=(TextView)findViewById(R.id.tv_goods_publish_date);
        tv_connect_publish=(TextView)findViewById(R.id.tv_connect_publish);
        tv_title=(TextView)findViewById(R.id.tv_title);
        iv_back=(ImageView)findViewById(R.id.iv_back);
        btn_buy=(Button)findViewById(R.id.btn_buy);
        gv_img=(GridViewForScrollView)findViewById(R.id.gv_img);

    }

    @Override
    protected void initData() {
        goodsInfo=(GoodsInfo) getIntent().getSerializableExtra("goodsinfo");
        tv_goods_name.setText(goodsInfo.getGoods_name());
        tv_title.setText(goodsInfo.getGoods_name());
        tv_goods_pricce.setText(goodsInfo.getGoods_price()+"¥");
        tv_goods_publisher.setText(goodsInfo.getGoods_publisher());
        tv_goods_des.setText(goodsInfo.getGoods_des());
        tv_goods_publish_date.setText(goodsInfo.getGoods_publish_date());
        if (goodsInfo.getGoods_publisher().equals(getSharedPreferences("user",0).getString("user_name",""))){
            tv_connect_publish.setText("该商品是您发布的");
            tv_connect_publish.setClickable(false);
        }
        else
        {
            tv_connect_publish.setText("联系卖家");
            tv_connect_publish.setClickable(true);
        }

        ArrayList<String> imgs=new ArrayList<>();
        bitmapArrayList=new ArrayList<>();
        imgs=new Gson().fromJson(goodsInfo.getGoods_imgs(),ArrayList.class);
        for (String img: imgs){
            bitmapArrayList.add(Configs.base64ToBitmap(img));
        }
        initImgAdapter();
        gv_img.setAdapter(imgAdapter);

    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
        btn_buy.setOnClickListener(this);
        tv_connect_publish.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_connect_publish:
                if (tv_connect_publish.getText().toString().equals("联系卖家"))
                connectPublish();
                break;
            case R.id.btn_buy:
                buyGoodsInfo();
                break;
        }
    }
    //购买商品
    private void buyGoodsInfo(){
        Log.i("ming","buyGoodsInfo");
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("购买");
        builder.setMessage("您确定要购买");
        builder.setNegativeButton("取消",null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateGoodsTradingStatus();
            }
        });
        builder.create().show();
    }
    //联系卖家
    private void connectPublish(){
        Intent intent=new Intent(this,MyNotificationDesActivity.class);
        intent.putExtra("receiver_user_name",goodsInfo.getGoods_publisher());
        startActivity(intent);
    }
    //改变商品的交易状态,让其不再显示在所有列表中
    private void updateGoodsTradingStatus(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        OkHttpClientManager.postAsyn(Configs.UPDATE_GOODS, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getApplication(), Configs.URLERROR,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Message message=new Message();
                message.obj=response;

                Log.i("ming","response:　"+response);
                if (!response.equals("0")){
                    Toast.makeText(getApplication(),"购买成功",Toast.LENGTH_SHORT).show();
                    finish();
                }
//                else
//                    Toast.makeText(getApplication(), "购买失败",Toast.LENGTH_SHORT).show();
            }
        },new OkHttpClientManager.Param[]{
                new OkHttpClientManager.Param("goods_no",String.valueOf(goodsInfo.getGoods_no())),
                new OkHttpClientManager.Param("goods_trading_status","1"),
                new OkHttpClientManager.Param("goods_trading_date",df.format(new Date()))
        });
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
                    convertView=new ImageView(getApplication());
                }
                ((ImageView)convertView).setImageBitmap(bitmapArrayList.get(position));
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(300,300));
                convertView.setLayoutParams(params);
                return convertView;
            }
        };
    }

}
