package com.fengpy.myapp.tabdemo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fengpy.myapp.R;

/**
 * @ description:
 * @ time: 2017/8/17.
 * @ author: peiyun.feng
 * @ email: fengpy@aliyun.com
 */

public class Tab1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_tab1, null);
    }
}
