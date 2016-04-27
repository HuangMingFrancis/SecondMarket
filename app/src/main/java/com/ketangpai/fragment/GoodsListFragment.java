package com.ketangpai.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.OkHttpClientManager;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Francis on 2016/4/14.
 */
public class GoodsListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    public static final  int GETGOODSLIST=100;
    private RecyclerView recycler_goods;
    private SwipeRefreshLayout swipe_goods_list;
    private ArrayList<GoodsInfo> goodsInfos;
    private GoodsAdapter goodsAdapter;
    private ArrayList<CollectInfo> collectInfos;
    private String goods_type;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_goods_list;
    }

    @Override
    protected void initView() {
        recycler_goods=(RecyclerView)view.findViewById(R.id.recycler_goods);
        swipe_goods_list=(SwipeRefreshLayout)view.findViewById(R.id.swipe_goods_list);

        swipe_goods_list.setColorSchemeColors(R.color.red, R.color.blue, R.color.green,R.color.yellow);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_goods.setLayoutManager(layoutManager);


    }

    @Override
    protected void initData() {
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("goods_type",0);
        goods_type=sharedPreferences.getString("goods_type","");
//        getGoodsList(goods_type);
//        getCollectInfo();
    }

    @Override
    protected void initListener() {
        swipe_goods_list.setOnRefreshListener(this);
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        swipe_goods_list.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

    }

    @Override
    protected void loadData() {

    }

    private void getGoodsList(String goods_type){
        Log.i("ming","goods_type:  "+goods_type);
        OkHttpClientManager.postAsyn(Configs.QUERYGOODS_BY_GOODSCATEGORY, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getActivity(),Configs.URLERROR,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Log.i("ming","goods:  "+response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject;
                    GoodsInfo goodsInfo;
                    for (int i=0;i<jsonArray.length();i++){
                        jsonObject=jsonArray.getJSONObject(i);
                        goodsInfo=new Gson().fromJson(jsonObject.toString(),GoodsInfo.class);
                        goodsInfos.add(goodsInfo);
                    }
                    Log.i("ming","goodsInfos:  "+goodsInfos.size());
                    getCollectInfo();
//                    initGoodsList();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new OkHttpClientManager.Param("goods_category_no",goods_type));
    }
    private void initGoodsList(){
        goodsAdapter=new GoodsAdapter(getActivity(),goodsInfos);
        recycler_goods.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_goods.setAdapter(goodsAdapter);
        goodsAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(), GoodDesActivity.class);
                intent.putExtra("goodsinfo",goodsInfos.get(position));
                getActivity().startActivity(intent);


            }
        });

    }
    //下拉刷新
    @Override
    public void onRefresh() {
        goodsInfos=new ArrayList<>();
        getGoodsList(goods_type);
        getCollectInfo();
    }

    class GoodsAdapter extends BaseAdapter<GoodsInfo> {
        public GoodsAdapter(Context mContext, List<GoodsInfo> mDataList) {
            super(mContext, mDataList);
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.item_goods_list;
        }

        @Override
        protected void bindData(ViewHolder holder, final int position, final GoodsInfo item) {
            final TextView tv_goods_name,tv_goods_price,tv_goods_publisher,tv_goods_publish_date;
            final ImageView iv_collect;
            tv_goods_name = (TextView) holder.getViewById(R.id.tv_goods_name);
            tv_goods_price = (TextView) holder.getViewById(R.id.tv_goods_price);
            tv_goods_publisher = (TextView) holder.getViewById(R.id.tv_goods_publisher);
            tv_goods_publish_date = (TextView) holder.getViewById(R.id.tv_goods_publish_date);
            iv_collect=(ImageView)holder.getViewById(R.id.iv_collect);

            tv_goods_name.setText(item.getGoods_name());
            tv_goods_price.setText(item.getGoods_price()+"¥");
//            tv_goods_publisher.setText("发布者"+item.getGoods_publisher());
            tv_goods_publish_date.setText("发布者:"+item.getGoods_publisher());
            for (CollectInfo collectInfo:collectInfos){
                if (collectInfo.getGoods_no().equals(String.valueOf(item.getGoods_no()))){
                    iv_collect.setBackgroundResource(R.drawable.ic_toggle_star);
                    goodsInfos.get(position).setGoods_is_collect(1);
                }
            }
            final GoodsInfo goodsInfo=item;
            iv_collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean is_collect=false;
                    for (CollectInfo collectInfo:collectInfos){
                        if (collectInfo.getGoods_no().equals(String.valueOf(item.getGoods_no()))){
                            is_collect=true;
                        }
                    }
                    if (goodsInfos.get(position).getGoods_is_collect()==0){
                        goodsInfos.get(position).setGoods_is_collect(1);
                        iv_collect.setBackgroundResource(R.drawable.ic_toggle_star);
                        CollectInfo collectInfo=new CollectInfo();
                        collectInfo.setGoods_no(String.valueOf(item.getGoods_no()));
                        collectInfo.setUser_name(getActivity().getSharedPreferences("user",0).getString("user_name",""));
                        collectInfos.add(collectInfo);
                        addCollect(item);
//                        return;
                    }
                    else{
                        goodsInfos.get(position).setGoods_is_collect(1);
                        iv_collect.setBackgroundResource(R.drawable.ic_toggle_star_outline);
                        CollectInfo collectInfo1=null;
                        for (CollectInfo collectInfo:collectInfos){
                            if (collectInfo.getGoods_no().equals(String.valueOf(item.getGoods_no()))){
                                collectInfo1=collectInfo;
                            }
                        }
                        collectInfos.remove(collectInfo1);
                        deleteCollect(item);
                    }

                }
            });
        }

    }
    //添加收藏商品
    private void addCollect(GoodsInfo goodsInfo){
        OkHttpClientManager.postAsyn(Configs.ADDCOLLECT, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getActivity(),Configs.URLERROR,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
            }
        },new OkHttpClientManager.Param[]{
                new OkHttpClientManager.Param("goods_no",String.valueOf(goodsInfo.getGoods_no())),
                new OkHttpClientManager.Param("user_name",getActivity().getSharedPreferences("user",0).getString("user_name","")),
                new OkHttpClientManager.Param("user_id",getActivity().getSharedPreferences("user",0).getString("user_id",""))
        });

    }
    //删除收藏商品
    private void deleteCollect(GoodsInfo goodsInfo){
        OkHttpClientManager.postAsyn(Configs.DELETECOLLECT, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getActivity(),Configs.URLERROR,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
            }
        },new OkHttpClientManager.Param[]{
                new OkHttpClientManager.Param("goods_no",String.valueOf(goodsInfo.getGoods_no())),
                new OkHttpClientManager.Param("user_name",getActivity().getSharedPreferences("user",0).getString("user_name","")),
                new OkHttpClientManager.Param("user_id",getActivity().getSharedPreferences("user",0).getString("user_id",""))
        });

    }
    //获得当前用户的收藏商品列表
    private void getCollectInfo(){
        collectInfos=new ArrayList<>();
        OkHttpClientManager.postAsyn(Configs.GETCOLLECT, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getActivity(),Configs.URLERROR,Toast.LENGTH_SHORT).show();
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
                    swipe_goods_list.setRefreshing(false);
                    initGoodsList();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new OkHttpClientManager.Param("user_id",getActivity().getSharedPreferences("user",0).getString("user_id","")));
    }

    @Override
    public void onResume() {
        super.onResume();
        goodsInfos=new ArrayList<>();
        swipe_goods_list.setRefreshing(true);
        getGoodsList(goods_type);
        getCollectInfo();
    }
}
