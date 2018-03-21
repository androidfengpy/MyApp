package com.fengpy.myapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fengpy.myapp.charts.ChartsActivity;
import com.fengpy.myapp.dbdemo.DBDemoActivity;
import com.fengpy.myapp.gaode.GaodeActivity;
import com.fengpy.myapp.gaodedemo.GaodeDemoActivity;
import com.fengpy.myapp.jsonanalysis.AnalyzeJsonActivity;
import com.fengpy.myapp.recyclerviewcheckbox.RecycleviewCheckboxActivity;
import com.fengpy.myapp.save2excel.DataOperateActivity;
import com.fengpy.myapp.save2jsonfile.ReadAndWriteJsonFile;
import com.fengpy.myapp.settingdemo.SettingActivity;
import com.fengpy.myapp.spinnerthreecatalog.ThreeCatalogActivity;
import com.fengpy.myapp.tabdemo.TabDemoActivity;
import com.fengpy.myapp.widget.WidgetActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String EXITACTION = "action.exit";
    private ExitReceiver mExitReceiver = new ExitReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initExitReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销退出广播
        unregisterReceiver(mExitReceiver);
    }

    private void initUI() {
        findViewById(R.id.btn_spinner_three_catalog).setOnClickListener(this);
        findViewById(R.id.btn_read_and_write_json).setOnClickListener(this);
        findViewById(R.id.btn_data_to_excel).setOnClickListener(this);
        findViewById(R.id.btn_json).setOnClickListener(this);
        findViewById(R.id.btn_charts).setOnClickListener(this);
        findViewById(R.id.btn_widget).setOnClickListener(this);
        findViewById(R.id.btn_setting_demo).setOnClickListener(this);
        findViewById(R.id.btn_tab_demo).setOnClickListener(this);
        findViewById(R.id.btn_db_demo).setOnClickListener(this);
        findViewById(R.id.btn_geode_marker).setOnClickListener(this);
        findViewById(R.id.btn_geode_demo).setOnClickListener(this);
        findViewById(R.id.btn_recycle_checkbox).setOnClickListener(this);
    }

    private void initExitReceiver() {
        // 注册退出应用的广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(EXITACTION);
        registerReceiver(mExitReceiver, intentFilter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_spinner_three_catalog:
                Intent threeCatalogIntent = new Intent(MainActivity.this, ThreeCatalogActivity.class);
                startActivity(threeCatalogIntent);
                break;

            case R.id.btn_read_and_write_json:
                Intent readWriteJsonIntent = new Intent(MainActivity.this, ReadAndWriteJsonFile.class);
                startActivity(readWriteJsonIntent);
                break;

            case R.id.btn_data_to_excel:
                Intent excelIntent = new Intent(MainActivity.this, DataOperateActivity.class);
                startActivity(excelIntent);
                break;

            case R.id.btn_json:
                Intent jsonIntent = new Intent(MainActivity.this, AnalyzeJsonActivity.class);
                startActivity(jsonIntent);
                break;

            case R.id.btn_charts:
                Intent chartIntent = new Intent(MainActivity.this, ChartsActivity.class);
                startActivity(chartIntent);
                break;

            case R.id.btn_widget:
                Intent widgetIntent = new Intent(MainActivity.this, WidgetActivity.class);
                startActivity(widgetIntent);
                break;

            case R.id.btn_setting_demo:
                Intent settingIntent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(settingIntent);
                break;

            case R.id.btn_tab_demo:
                Intent tabIntent = new Intent(MainActivity.this, TabDemoActivity.class);
                startActivity(tabIntent);
                break;

            case R.id.btn_db_demo:
                Intent dbIntent = new Intent(MainActivity.this, DBDemoActivity.class);
                startActivity(dbIntent);
                break;

            case R.id.btn_geode_marker:
                Intent markerIntent = new Intent(MainActivity.this, GaodeActivity.class);
                startActivity(markerIntent);
                break;

            case R.id.btn_geode_demo:
                Intent gaodeIntent = new Intent(MainActivity.this, GaodeDemoActivity.class);
                startActivity(gaodeIntent);
                break;

            case R.id.btn_recycle_checkbox:
                Intent recycleIntent = new Intent(MainActivity.this, RecycleviewCheckboxActivity.class);
                startActivity(recycleIntent);
                break;
        }
    }

    /**
     * 退出应用时发送的广播
     *
     */
    class ExitReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //1 主界面的启动模式在配置文件中设置成singleTask
            //2 在我们需要退出的时候startActivity(this,HomeActivity,class)
            //3 发送退出应用的广播：sendBroadcast(new Intent("action.exit"));
            if(intent.getAction().equals(EXITACTION)){
                finish();
            }
        }
    }
}
