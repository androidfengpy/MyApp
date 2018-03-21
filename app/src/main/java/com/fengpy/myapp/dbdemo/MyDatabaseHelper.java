package com.fengpy.myapp.dbdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @ description:
 * @ time: 2017/8/23.
 * @ author: peiyun.feng
 * @ email: fengpy@aliyun.com
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context mContext;

    public static final String CREATE_TASK = "create table if not exists task("
            +"id integer primary key autoincrement,"
            +"task_name text,"
            +"start_time text,"
            + "end_time text"
            + ")";

    public static String setTaskNameTab(String taskName) {
        String sqlTab = "create table if not exists " + taskName
                +"(id integer primary key autoincrement,"
                +"task_name text,"
                +"data text"
                + ")";
        return sqlTab;
    }

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        mContext = context;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*db.execSQL(CREATE_TASK);
        Toast.makeText(mContext, "任务列表创建成功", Toast.LENGTH_SHORT).show();*/
    }
}
