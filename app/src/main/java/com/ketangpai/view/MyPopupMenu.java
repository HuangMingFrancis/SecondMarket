package com.ketangpai.view;

import android.content.Context;
import android.view.View;
import android.widget.PopupMenu;

/**
 * Created by nan on 2016/3/15.
 */
public class MyPopupMenu extends PopupMenu {
    public MyPopupMenu(Context context, View anchor) {
        super(context, anchor);
    }
    public MyPopupMenu(Context context,View anchor,int menuId){
        super(context,anchor);
        this.inflate(menuId);
    }
}
