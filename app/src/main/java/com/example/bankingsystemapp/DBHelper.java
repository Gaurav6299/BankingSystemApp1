package com.example.bankingsystemapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    final static String DBNAME = "banking.db";
    private String TABLE_NAME = "user_table";
    private String TABLE_NAME1 = "transfer_table";

    final static int DBVERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(PHONENUMBER INTEGER PRIMARY KEY AUTOINCREMENT ,Name Text,Balance Decimal,Email varchar,Account_No varchar)");
        db.execSQL("create table " + TABLE_NAME1 + "(TRANSACIONID INTEGER PRIMARY KEY AUTOINCREMENT,Date text,FROMNAME text,TONAME text,Amount Decimal,Status text)");
        db.execSQL("insert into user_table values(6299164950,'Gaurav',760.70,'gaurav@gmail.com','XXXXXXXXXXX231')");
        db.execSQL("insert into user_table values(7667268671,'Aman',360.78,'aprna@gmail.com','XXXXXXXXXXX123')");
        db.execSQL("insert into user_table values(1239898792,'Sujit',560.60,'gaurav@gmail.com','XXXXXXXXXXX799')");
        db.execSQL("insert into user_table values(2334456621,'Ankit',760.00,'Ankit@gmail.com','XXXXXXXXXXX988')");
        db.execSQL("insert into user_table values(1232134567,'Vishal',460.34,'vishal@gmail.com','XXXXXXXXXXX989')");
        db.execSQL("insert into user_table values(9572834228,'Vikash',960.23,'vikash@gmail.com','XXXXXXXXXXX654')");
        db.execSQL("insert into user_table values(7258692899,'Kunal',860.52,'kunal@gmail.com','XXXXXXXXXXX431')");
        db.execSQL("insert into user_table values(9122130449,'Kushal',1060.67,'kushal@gmail.com','XXXXXXXXXXX289')");
        db.execSQL("insert into user_table values(9577929200,'Payal',960.87,'payal@gmail.com','XXXXXXXXXXX634')");
        db.execSQL("insert into user_table values(9797790990,'Ahijeet',1160.12,'sachin@gmail.com','XXXXXXXXXXX831')");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP Table if Exists "+TABLE_NAME);
        db.execSQL("DROP Table if Exists "+TABLE_NAME1);
        onCreate(db);

    }
    public Cursor readalldata()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from user_table",null);
        return cursor;
    }

    public Cursor getPhone(String phonenumber)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from user_table where phonenumber=" +phonenumber,null);
        return cursor;
    }

    public Cursor readselectalluser(String phonenumber)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from user_table except select * from user_table where phonenumber="+phonenumber,null);
        return cursor;
    }
    public Cursor readTransferData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select * from transfer_table",null);
        return cursor;
    }

    public void updateAmount(String phonenumber,String amount)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("update user_table set balance=" +amount+ " where phonenumber=" +phonenumber);
    }

    public boolean transferData(String date,String fromName,String toName,String amount,String status)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("Date",date);
        values.put("FROMNAME",fromName);
        values.put("TONAME",toName);
        values.put("Amount",amount);
        values.put("Status",status);
        long id=db.insert(TABLE_NAME1,null,values);
        if(id<0){
            return false;
        }
        else {
            return true;
        }
    }

}
