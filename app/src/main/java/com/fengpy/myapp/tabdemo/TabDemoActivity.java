package com.fengpy.myapp.tabdemo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.fengpy.myapp.R;

/**
 * @ description:
 * @ time: 2017/8/23.
 * @ author: peiyun.feng
 * @ email: fengpy@aliyun.com
 */

public class TabDemoActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout tab1Ll, tab2Ll, tab3Ll;
    private Tab1 tab1;
    private Tab2 tab2;
    private Tab3 tab3;
    private ImageButton ib1, ib2, ib3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tab_demo);

        initUi();
        initEvent();
        showFragment(1);
    }

    private void initUi() {
        tab1Ll = (LinearLayout) findViewById(R.id.tab1);
        tab2Ll = (LinearLayout) findViewById(R.id.tab2);
        tab3Ll = (LinearLayout) findViewById(R.id.tab3);

        ib1 = (ImageButton) findViewById(R.id.ib1);
        ib2 = (ImageButton) findViewById(R.id.ib2);
        ib3 = (ImageButton) findViewById(R.id.ib3);

       /* tab1 = new Tab1();
        tab2 = new Tab2();
        tab3 = new Tab3();*/
    }

    private void initEvent(){
        tab1Ll.setOnClickListener(this);
        tab2Ll.setOnClickListener(this);
        tab3Ll.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        resetImg();
        switch (view.getId()) {
            case R.id.tab1:
                showFragment(1);
                break;

            case R.id.tab2:
                showFragment(2);
                break;

            case R.id.tab3:
                showFragment(3);
                break;
        }
    }

    private void showFragment(int index){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        hideFragment(ft);
        switch (index){
            case 1:
                ib1.setImageResource(R.mipmap.tab1_click);
                if(tab1 == null) {
                    tab1 = new Tab1();
                    ft.add(R.id.content, tab1);
                } else {
                    ft.show(tab1);
                }
                break;

            case 2:
                ib2.setImageResource(R.mipmap.tab2_click);
                if(tab2 == null) {
                    tab2 = new Tab2();
                    ft.add(R.id.content, tab2);
                } else {
                    ft.show(tab2);
                }
                break;

            case 3:
                ib3.setImageResource(R.mipmap.tab3_click);
                if(tab3 == null) {
                    tab3 = new Tab3();
                    ft.add(R.id.content, tab3);
                } else {
                    ft.show(tab3);
                }
                break;
        }
        ft.commit();
    }

    private void hideFragment(FragmentTransaction ft){
        if(tab1 != null) {
            ft.hide(tab1);
        }
        if(tab2 != null) {
            ft.hide(tab2);
        }
        if(tab3 != null) {
            ft.hide(tab3);
        }
    }

    /**
     * 切换图片之未选中状态
     */
    public void resetImg() {
        ib1.setImageResource(R.mipmap.tab_1);
        ib2.setImageResource(R.mipmap.tab_2);
        ib3.setImageResource(R.mipmap.tab_3);
    }

}
