package com.ketangpai.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ketangpai.activity.GoodDesActivity;
import com.ketangpai.base.BaseAdapter;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.base.Configs;
import com.ketangpai.entity.CollectInfo;
import com.ketangpai.entity.GoodsInfo;
import com.ketangpai.listener.OnItemClickListener;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.OkHttpClientManager;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Francis on 2016/4/15.
 */
public class MyCollectFragment extends BaseFragment {
    private TextView tv_hint_my_collect;
    private RecyclerView recycler_my_collect;
    private ArrayList<CollectInfo> collectInfos;
    private ArrayList<GoodsInfo> goodsInfos;
    private CollectAdapter collectAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_collect;
    }

    @Override
    protected void initView() {
        tv_hint_my_collect=(TextView)view.findViewById(R.id.tv_hint_my_collect);
        recycler_my_collect=(RecyclerView)view.findViewById(R.id.recycle_my_collect);
    }

    @Override
    protected void initData() {
        collectInfos=new ArrayList<>();
        getCollect();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {

    }
    private void getCollect(){
        OkHttpClientManager.postAsyn(Configs.GETCOLLECT, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getActivity(), Configs.URLERROR,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject;
                    CollectInfo collectInfo;
                    for (int i=0;i<jsonArray.length();i++){
                        jsonObject=jsonArray.getJSONObject(i);
                        collectInfo=new Gson().fromJson(jsonObject.toString(),CollectInfo.class);
                        collectInfos.add(collectInfo);
                    }
                    Log.i("ming","collectInfos:  "+collectInfos.size());
                    getGoodsList();
//                    initCollectList();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new OkHttpClientManager.Param("user_name", getActivity().getSharedPreferences("user",0).getString("user_name","")));
    }
    private void initCollectList(){
        if (collectInfos.size()>0){
            tv_hint_my_collect.setVisibility(View.GONE);
            recycler_my_collect.setVisibility(View.VISIBLE);
        }
        Log.i("ming","initCollectList:  "+goodsInfos.size());
        collectAdapter=new CollectAdapter(getActivity(),goodsInfos);
        recycler_my_collect.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_my_collect.setAdapter(collectAdapter);

        //item点击事件
        collectAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(), GoodDesActivity.class);
                intent.putExtra("goodsinfo",goodsInfos.get(position));
                startActivity(intent);
            }
        });


    }

    class CollectAdapter extends BaseAdapter<GoodsInfo> {
        public CollectAdapter(Context mContext, List<GoodsInfo> mDataList) {
            super(mContext, mDataList);
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.item_goods_list;
        }

        @Override
        protected void bindData(ViewHolder holder, int position, GoodsInfo item) {
            TextView tv_goods_name,tv_goods_price,tv_goods_publisher,tv_goods_publish_date;
            ImageView iv_collect;
            tv_goods_name = (TextView) holder.getViewById(R.id.tv_goods_name);
            tv_goods_price= (TextView) holder.getViewById(R.id.tv_goods_price);
            tv_goods_publisher= (TextView) holder.getViewById(R.id.tv_goods_publisher);
            tv_goods_publish_date= (TextView) holder.getViewById(R.id.tv_goods_publish_date);

            iv_collect=(ImageView)holder.getViewById(R.id.iv_collect);

            iv_collect.setVisibility(View.GONE);
            tv_goods_name.setText(item.getGoods_name());
            tv_goods_price.setText(item.getGoods_price());
            tv_goods_publisher.setText(item.getGoods_publisher());
            tv_goods_publish_date.setText(item.getGoods_publish_date());

        }

    }
    //由收藏列表获得商品列表
    private void getGoodsList(){
        goodsInfos=new ArrayList<>();
        for (final CollectInfo collectInfo: collectInfos){
            Log.i("ming","collectinfo user_no :  "+collectInfos.get(0).getGoods_no());
            OkHttpClientManager.postAsyn(Configs.QUERYGOODS_BY_GOODSNO, new OkHttpClientManager.ResultCallback<String>() {
                @Override
                public void onError(Request request, Exception e) {
                    Toast.makeText(getActivity(),Configs.URLERROR,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String response) {
                    Log.i("ming","response:  "+response);
                    GoodsInfo goodsInfo=new Gson().fromJson(response,GoodsInfo.class);
                    goodsInfos.add(goodsInfo);
                    Log.i("ming","goodsInfos in ok:  "+goodsInfos.size());
                    if (goodsInfos.size()==collectInfos.size())
                        initCollectList();
                }
            },new OkHttpClientManager.Param("goods_no",collectInfo.getGoods_no()));
        }

    }

}
