package com.android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    public static SQLiteDatabase database;


    public static void initDB(Context context){
        DBcreate dbHelper = new DBcreate(context);
        database = dbHelper.getWritableDatabase();
    }

    public static int updateInfoByCity(String city, String content){
        ContentValues values = new ContentValues();
        values.put("content",content);
        return database.update("info",values,"city=?",new String[]{city});
    }

    //增加一个新城市
    public static long addCityInfo(String city, String content){
        ContentValues values = new ContentValues();
        values.put("city",city);
        values.put("content",content);
        return database.insert("info",null,values);
    }

    //查找数据库中所有的城市，返回一个列表
    public static List<String> queryAllCityName(){
        Cursor cursor = database.query("info", null, null, null, null, null,null);
        List<String> cityList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String city = cursor.getString(cursor.getColumnIndex("city"));
            cityList.add(city);
        }
        return cityList;
    }

    // 查询数据库全部信息
    public static List<CityDB> queryAllInfo(){
        Cursor cursor = database.query("info", null, null, null, null, null, null);
        List<CityDB> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String city = cursor.getString(cursor.getColumnIndex("city"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            CityDB bean = new CityDB(id, city, content);
            list.add(bean);
        }
        return list;
    }

    //查询数据库中城市信息
    public static String queryInfoByCity(String city){
        Cursor cursor = database.query("info", null, "city=?", new String[]{city}, null, null, null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            String content = cursor.getString(cursor.getColumnIndex("content"));
            return content;
        }
        return null;
    }

    //删除一个城市
    public static int deleteInfoByCity(String city){
        return database.delete("info","city=?",new String[]{city});
    }

    //删除全部数据信息
    public static void deleteAllInfo(){
        String sql = "delete from info";
        database.execSQL(sql);
    }

    //最多存储5个城市的信息
    public static int getCityCount(){
        Cursor cursor = database.query("info", null, null, null, null, null, null);
        int count = cursor.getCount();
        return count;
    }


}
