package com.stt.fengdatastorage.uavosdemo.uavosdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @ description:
 * @ time: 2017/11/30.
 * @ author: peiyun.feng
 * @ email: fengpy@aliyun.com
 */

/*
    建表规则：
    业务名称_表的名称
    alipay_task
    force_project
    trade_config
    */

public class UAVOSDBHelper extends SQLiteOpenHelper {

    // uavos数据库 uavos.db

    /* 表1：探头信息表probe_config（id-主键, 字符表示，名称，探测数据类型(多值字段)）*/
    /*表2：任务表 uavos_task（id-主键, 任务id(根据时间来定义的id)，任务名称，任务飞行模式，探头类型，开始时间，结束时间，地址）
        任务显示列表：任务名称--飞行模式--探头类型--开始时间--结束时间--任务地址
    */

    /* 表3：任务数据模板表data_template（id-主键, 探头型号probe， 数据1value-xx, 数据2value-xx,...,时间time，纬度lat，经度lng，高度high，飞行速度speed）;

       表n:复制模板表，表名data_t+task.id();
       任务数据显示列表：时间--数据1--数据2--...--高度--速度
     */

    private static final String CREATE_PROBE_CONFIG = "create table probe_config ("
            + "id integer primary key autoincrement,"
            + "char_name text,"
            + "name real,"
            + "pages integer,"
            + "name text )";

    private static final String CREATE_TASK = "create table task ("
            + "id integer primary key autoincrement,"
            + "task_id text,"
            + "task_name text,"
            + "task_mode text,"
            + "probe text,"
            + "start_time text,"
            + "end_time text,"
            + "address text )";

    private static final String CREATE_DATA_TEMPLATE = "create table data_template ("
            + "id integer primary key autoincrement,"
            + "probe text,"
            + "time text,"
            + "lat real,"
            + "lng real,"
            + "high real,"
            + "speed real,"
            + "value_rf real,"
            + "value_lh real,"
            + "value_le real,"
            + "value_dr real,"
            + "name text,"
            + "level real,"
            + "class integer )";



    private Context mContext;

    public UAVOSDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROBE_CONFIG);
        db.execSQL(CREATE_TASK);
        db.execSQL(CREATE_DATA_TEMPLATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists probe_config");
        db.execSQL("drop table if exists task");
        db.execSQL("drop table if exists data_template");

        onCreate(db);
    }
}
