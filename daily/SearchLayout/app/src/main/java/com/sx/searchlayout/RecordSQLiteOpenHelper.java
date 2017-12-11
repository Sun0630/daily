package com.sx.searchlayout;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author sunxin
 * @Date 2017/12/11 0011 下午 2:03
 * @Description
 */

public class RecordSQLiteOpenHelper extends SQLiteOpenHelper {

    public RecordSQLiteOpenHelper(Context context) {
        super(context, "history.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 建表
        db.execSQL("create table records(id integer primary key autoincrement,name varchar(200))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
