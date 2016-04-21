package com.ketangpai.base;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by ZYMAppOne on 2015/12/16.
 */
public class Configs {
    /*****
     * 系统相册（包含有 照相、选择本地图片）
     */
    public class SystemPicture{
        /***
         * 保存到本地的目录
         */
        public static final String SAVE_DIRECTORY = "/zym";
        /***
         * 保存到本地图片的名字
         */
        public static final String SAVE_PIC_NAME="head.jpeg";
        /***
         *标记用户点击了从照相机获取图片  即拍照
         */
        public static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
        /***
         *标记用户点击了从图库中获取图片  即从相册中取
         */
        public static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
        /***
         * 返回处理后的图片
         */
        public static final int PHOTO_REQUEST_CUT = 3;// 结果
    }
    //基本地址
    public static final String URL="http://192.168.253.1:8080/Taobao/";
    //获得收藏者
    public static final String GETCOLLECT=URL+"queryallcollect";
    //添加收藏者
    public static final String ADDCOLLECT=URL+"insertcollect";
    //删除收藏者
    public static final String DELETECOLLECT=URL+"deletecollect";
    //根据goods_no查找商品
    public static final String QUERYGOODS_BY_GOODSNO=URL+"queryagoods";
    //根据goods_category_no查找商品列表
    public static final String QUERYGOODS_BY_GOODSCATEGORY=URL+"querygoods";
    //发布商品
    public static final String RELEASE_GOODS=URL+"insertgoods";
    //根据消息的接收方和发送方获得消息
    public static final String QUERY_MESSAGE_BY_RECEIVE_AND_SEND=URL+"querymessagesbyuser";
    //购买商品后交易状态刷新
    public static final String UPDATE_GOODS=URL+"updategoods";
    //输入查询条件获得商品
    public static final String SEARCH_GOODS=URL+"searchgoods";
    //查询所有聊天信息
    public static final String QUERY_ALL_MESSAGE=URL+"querymessages";
    //删除聊天信息
    public static final String DELETE_MESSAGE=URL+"deletemessage";
    //添加商品图片信息
    public static final String ADD_IMGS=URL+"insertimg";

    //请求出错
    public static final String URLERROR="网络请求出错,请检查!";

    /**
     * bitmap转为base64
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
