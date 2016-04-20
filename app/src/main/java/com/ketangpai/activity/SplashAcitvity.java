package com.ketangpai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ketangpai.nan.ketangpai.R;

/**
 * Created by nan on 2016/3/9.
 */
public class SplashAcitvity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startActivity(new Intent(this,LoginActivity1.class));
        finish();

    }
}
