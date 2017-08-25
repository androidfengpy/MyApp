package com.fengpy.myapp.tabdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fengpy.myapp.R;

/**
 * @ description:
 * @ time: 2017/8/23.
 * @ author: peiyun.feng
 * @ email: fengpy@aliyun.com
 */

public class TabDemoActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tab_demo);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.content:
                break;
        }
    }
}
