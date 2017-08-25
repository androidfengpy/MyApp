package com.fengpy.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.fengpy.myapp.dbdemo.DBDemoActivity;
import com.fengpy.myapp.tabdemo.TabDemoActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button tabBtn;
    private Button dbBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    private void initUI() {
        tabBtn = (Button) findViewById(R.id.btn_tab_demo);
        tabBtn.setOnClickListener(this);
        dbBtn = (Button) findViewById(R.id.btn_db_demo);
        dbBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tab_demo:
                Intent tabIntent = new Intent(MainActivity.this, TabDemoActivity.class);
                startActivity(tabIntent);
                break;
            case R.id.btn_db_demo:
                Intent dbIntent = new Intent(MainActivity.this, DBDemoActivity.class);
                startActivity(dbIntent);
                break;
        }
    }
}
