package com.ketangpai.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

import com.ketangpai.nan.ketangpai.R;

/**
 * Created by nan on 2016/3/17.
 */
public class EditTextWithDel extends EditText {

    private Drawable imgInable;
    private Context mContext;
    private Drawable leftDrawable;

    public EditTextWithDel(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        init();
    }

    private void init() {
        Drawable[] drawables = getCompoundDrawables();
        leftDrawable = drawables[0];
        if(leftDrawable!=null){
            leftDrawable.setBounds(0, 0, 45, 45);
        }
        imgInable = mContext.getResources().getDrawable(R.drawable.abc_ic_clear_material);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
            }
        });

        setDrawable();
    }

    private void setDrawable() {
        if (length() < 1) {
            setCompoundDrawables(leftDrawable, null, imgInable, null);
        } else {
            setCompoundDrawables(leftDrawable, null, imgInable, null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();

            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 100;
            if (rect.contains(eventX, eventY)) {
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }
}
