package com.fengpy.myapp.settingdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fengpy.myapp.MainActivity;
import com.fengpy.myapp.R;

/**
 * @ description:
 * @ time: 2017/10/24.
 * @ author: peiyun.feng
 * @ email: fengpy@aliyun.com
 */

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initUI();
    }

    public void initUI() {
        findViewById(R.id.btn_exit).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_exit:
                Intent exitIntent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(exitIntent);

                Intent intent = new Intent(MainActivity.EXITACTION);
                sendBroadcast(intent);
                break;
        }
    }
}
