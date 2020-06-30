package com.android.database;

public class CityDB {
    private int _id;
    private String city;
    private String content;

    public CityDB(int _id, String city, String content) {
        this._id = _id;
        this.city = city;
        this.content = content;
    }
    public String getCity() {
        return city;
    }
    public String getContent() {
        return content;
    }

}
