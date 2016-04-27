package com.ketangpai.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
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
import com.ketangpai.listener.OnItemLongClickListener;
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
public class MyCollectFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private TextView tv_hint_my_collect;
    private RecyclerView recycler_my_collect;
    private SwipeRefreshLayout swipe_collect_list;
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
        swipe_collect_list=(SwipeRefreshLayout)view.findViewById(R.id.swipe_collect_list);
        swipe_collect_list.setVisibility(View.VISIBLE);
        swipe_collect_list.setColorSchemeColors(R.color.colorPrimary);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_my_collect.setLayoutManager(layoutManager);
    }

    @Override
    protected void initData() {
        getCollect();
    }

    @Override
    protected void initListener() {
        swipe_collect_list.setOnRefreshListener(this);
    }

    @Override
    protected void loadData() {

    }
    private void getCollect(){
        collectInfos=new ArrayList<>();
        swipe_collect_list.setRefreshing(true);
        OkHttpClientManager.postAsyn(Configs.GETCOLLECT, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getActivity(), Configs.URLERROR,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Log.i("ming","getCollect    onResponse:  "+response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject;
                    CollectInfo collectInfo;
                    for (int i=0;i<jsonArray.length();i++){
                        jsonObject=jsonArray.getJSONObject(i);
                        collectInfo=new Gson().fromJson(jsonObject.toString(),CollectInfo.class);
                        collectInfos.add(collectInfo);
                    }
                    if (collectInfos.size()>0)
                    getGoodsList();
                    else {
                        swipe_collect_list.setRefreshing(false);
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new OkHttpClientManager.Param("user_id", getActivity().getSharedPreferences("user",0).getString("user_id","")));
    }
    private void initCollectList(){
        swipe_collect_list.setRefreshing(false);
        if (collectInfos.size()>0){
            tv_hint_my_collect.setVisibility(View.GONE);
            recycler_my_collect.setVisibility(View.VISIBLE);
        }
        Log.i("ming","initCollectList:  "+goodsInfos.size());
        collectAdapter=new CollectAdapter(getActivity(),goodsInfos);
//        recycler_my_collect.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        //长按item弹出对话框是否删除收藏记录
        collectAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, final int position) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("提示");
                builder.setMessage("是否删除该条收藏?");
                builder.setNegativeButton("取消",null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMyCollect(goodsInfos.get(position));
                    }
                });
                builder.create().show();
                return true;
            }
        });


    }

    @Override
    public void onRefresh() {
        getCollect();
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
        Log.i("ming","getGoodsList:  ");
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
                    Log.i("ming","getGoodsList  response:  "+response);
                    GoodsInfo goodsInfo=new Gson().fromJson(response,GoodsInfo.class);
                    goodsInfos.add(goodsInfo);
                    Log.i("ming","goodsInfos in ok:  "+goodsInfos.size());
                    if (goodsInfos.size()==collectInfos.size())
                        initCollectList();
                }
            },new OkHttpClientManager.Param("goods_no",collectInfo.getGoods_no()));
        }

    }
    //删除我的收藏
    private void deleteMyCollect(final GoodsInfo goodsInfo){
        OkHttpClientManager.postAsyn(Configs.DELETECOLLECT, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getActivity(),Configs.URLERROR,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                if (response.equals("1")){
                    goodsInfos.remove(goodsInfo);
                    initCollectList();
                }

            }
        },new OkHttpClientManager.Param[]{
                new OkHttpClientManager.Param("goods_no",String.valueOf(goodsInfo.getGoods_no())),
                new OkHttpClientManager.Param("user_name",getActivity().getSharedPreferences("user",0).getString("user_name","")),
                new OkHttpClientManager.Param("user_id",getActivity().getSharedPreferences("user",0).getString("user_id",""))
        });
    }

}
