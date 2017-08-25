package com.fengpy.myapp.dbdemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fengpy.myapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fengpy.myapp.dbdemo.MyDatabaseHelper.CREATE_TASK;
import static com.fengpy.myapp.dbdemo.MyDatabaseHelper.setTaskNameTab;

/**
 * @ description:
 * @ time: 2017/8/23.
 * @ author: peiyun.feng
 * @ email: fengpy@aliyun.com
 */

public class DBDemoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_create_db, btn_create_task_tab, btn_insert_task_tab, btn_insert_task_data,
            btn_query_task, btn_query_task_data, btn_query_all_task;
    private EditText et_task_name, et_insert_task_data, et_query_task_name;
    private ListView lv_task_tab, lv_task_data_tab;
    private TextView tv_all_task_name;

    private MyDatabaseHelper dbHelper;

    private List<TaskInfo> taskList = new ArrayList<TaskInfo>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_demo);
        initDB();
        initUI();
    }

    private void initDB() {
        dbHelper = new MyDatabaseHelper(DBDemoActivity.this, "MyAppDB.db", null, 2);
    }

    private void initUI() {
        btn_create_db = (Button) findViewById(R.id.btn_create_db);
        btn_create_db.setOnClickListener(this);
        btn_create_task_tab = (Button) findViewById(R.id.btn_create_task_tab);
        btn_create_task_tab.setOnClickListener(this);
        btn_insert_task_tab = (Button) findViewById(R.id.btn_insert_task_tab);
        btn_insert_task_tab.setOnClickListener(this);
        btn_insert_task_data = (Button) findViewById(R.id.btn_insert_task_data);
        btn_insert_task_data.setOnClickListener(this);
        btn_query_task = (Button) findViewById(R.id.btn_query_task);
        btn_query_task.setOnClickListener(this);
        btn_query_all_task = (Button) findViewById(R.id.btn_query_all_task);
        btn_query_all_task.setOnClickListener(this);
        btn_query_task_data = (Button) findViewById(R.id.btn_query_task_data);
        btn_query_task_data.setOnClickListener(this);
        et_task_name = (EditText) findViewById(R.id.et_task_name);
        et_insert_task_data = (EditText) findViewById(R.id.et_insert_task_data);
        lv_task_tab = (ListView) findViewById(R.id.lv_task_tab);
        lv_task_data_tab = (ListView) findViewById(R.id.lv_task_data_tab);

        tv_all_task_name = (TextView) findViewById(R.id.tv_all_task_name);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create_db://创建数据库
                dbHelper.getWritableDatabase();
                break;

            case R.id.btn_create_task_tab://创建任务表
                createTaskTab();
                break;

            case R.id.btn_insert_task_tab://添加任务
                String taskName = et_task_name.getText().toString().trim();
                if (TextUtils.isEmpty(taskName)) {
                    Toast.makeText(this, "任务名称不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                insertTaskTab(taskName);
                createTaskDataTab(taskName);
                break;

            case R.id.btn_insert_task_data://添加任务数据
                insertTaskDataTab();
                break;

            case R.id.btn_query_task://查询任务表
                queryTaskTab();
                displayTaskTab();
                lv_task_tab.setVisibility(View.VISIBLE);
                lv_task_data_tab.setVisibility(View.INVISIBLE);
                break;

            case R.id.btn_query_task_data://查询指定任务数据
                break;

            case R.id.btn_query_all_task:
                queryAllTaskName();
                break;
        }
    }

    /**
     * 给指定添加任务数据
     */
    private void insertTaskDataTab() {

    }


    private void createTaskDataTab(String taskName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sqlTab = setTaskNameTab(taskName);
        db.execSQL(sqlTab);
        Toast.makeText(DBDemoActivity.this, taskName + "任务数据创建表成功", Toast.LENGTH_SHORT).show();
    }

    private void queryAllTaskName() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        StringBuffer sb = new StringBuffer();
        Cursor cursor = db.rawQuery("select name from sqlite_master where type='table' order by name", null);
        while (cursor.moveToNext()) {
            //遍历出表名
            sb.append(cursor.getString(0)).append(";;;");
        }
        tv_all_task_name.setText(sb.toString());
    }

    /**
     * 向任务表中添加任务
     */
    private void insertTaskTab(String taskName) {
        SQLiteDatabase db;
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = df.format(new Date());
        values.put("task_name", taskName);
        values.put("start_time", startTime);
        long insertLong = db.insert("task", null, values);
        if (insertLong > 0) {
            Toast.makeText(DBDemoActivity.this, "任务添加成功", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayTaskTab() {
        TaskAdapter taskAdapter = new TaskAdapter(DBDemoActivity.this, R.layout.task_item, taskList);
        lv_task_tab.setAdapter(taskAdapter);
        lv_task_tab.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                TaskInfo taskInfo = taskList.get(position);
                Toast.makeText(DBDemoActivity.this, taskInfo.getTaskName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 查询任务表
     */
    private void queryTaskTab() {
        if(taskList.size() > 0) {
            taskList.clear();
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("task", null, null, null, null, null, null);
        cursor.getCount();
        while(cursor.moveToNext()){
            TaskInfo taskInfo = new TaskInfo();
            taskInfo.setTaskName(cursor.getString(cursor.getColumnIndex("name")));
            taskInfo.setStartTime(cursor.getString(cursor.getColumnIndex("time")));
            taskList.add(taskInfo);
        }
        cursor.close();
    }

    private void createTaskTab() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(CREATE_TASK);
        Toast.makeText(DBDemoActivity.this, "任务列表创建成功", Toast.LENGTH_SHORT).show();
    }
}
