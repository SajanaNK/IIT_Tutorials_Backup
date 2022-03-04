package com.example.databaseapplicationtutorial5;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.databaseapplicationtutorial5.Constant.EMPLOYEE_ADDRESS;
import static com.example.databaseapplicationtutorial5.Constant.EMPLOYEE_AGE;
import static com.example.databaseapplicationtutorial5.Constant.EMPLOYEE_ID;
import static com.example.databaseapplicationtutorial5.Constant.EMPLOYEE_NAME;
import static com.example.databaseapplicationtutorial5.Constant.EMPLOYEE_POSITION;
import static com.example.databaseapplicationtutorial5.Constant.TABLE_NAME;

public class EmployeeData extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "employees.db";
    public static final int DATABASE_VERSION = 1;

    public EmployeeData(Context ctx){
        super(ctx,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EMPLOYEE_ID + " INTEGER,"
                + EMPLOYEE_NAME + " TEXT,"
                + EMPLOYEE_ADDRESS + " TEXT,"
                + EMPLOYEE_AGE + " INTEGER,"
                + EMPLOYEE_POSITION + " TEXT);" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
