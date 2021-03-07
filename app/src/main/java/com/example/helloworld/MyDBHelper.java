package com.example.helloworld;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDBHelper extends SQLiteOpenHelper {
    private Context context;
    private SQLiteDatabase db;
    public MyDBHelper(Context context,String name,int version) {
        super(context, name, null, version);
        this.context=context;
        db=getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table IF NOT EXISTS personwendu(id integer primary key autoincrement, address varchar, name varchar, wendu varchar, dateandtime varchar,more varchar)";
        db.execSQL(sql);
        Toast.makeText(context,"数据加入成功",Toast.LENGTH_LONG).show();
    }
    /*
     private String name;
    private String dateandtime;
    private String address;
    private String wendu;
    private String more;
     */
    public ArrayList<WenDate> getAllData(){
        ArrayList<WenDate> list = new ArrayList<WenDate>();
        Cursor cursor = db.query("personwendu",null,null,null,null,null,"dateandtime DESC");
        cursor.moveToFirst();
        while(cursor.moveToNext()){
            String address = cursor.getString(cursor.getColumnIndex("address"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String wendu = cursor.getString(cursor.getColumnIndex("wendu"));
            String dateandtime = cursor.getString(cursor.getColumnIndex("dateandtime"));
            String more = cursor.getString(cursor.getColumnIndex("more"));
            list.add(new WenDate(address,name,wendu,dateandtime,more));
        }
        Log.v("输出报表数据库查询结果：",list.toString());
        return list;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
