package com.fengpy.myapp.jsonanalysis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.fengpy.myapp.R;

/**
 * @ description: json数据解析示例
 * @ time: 2017/11/13.
 * @ author: peiyun.feng
 * @ email: fengpy@aliyun.com
 */

public class AnalyzeJsonActivity extends AppCompatActivity {

    private TextView tvJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_analyze_json);

        tvJson = (TextView) findViewById(R.id.tv_display_json);




    }
}
