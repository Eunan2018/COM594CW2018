package com.eunan.tracey.com594cw2018;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LoginDataBaseAdapter {

    public LoginDataBaseAdapter(Context context) {
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Database version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "user.db";

    // Variable to hold database instance
    private static SQLiteDatabase db;
    // Variable to hold database helper instance
    private static DataBaseHelper dbHelper;

    // Method to open the database
    public LoginDataBaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    // Method to close the database
    public void close() {
        db.close();
    }

    // Method returns instance of the database
    public SQLiteDatabase getDatabaseInstance(){
        return db;
    }

    // Method to get the password of username
    public String getSingleEntry(String userName){
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("user",null,"userName=?",
                new String[]{userName},null,null,null);
        if(cursor.getCount()<1)
            return "NOT EXISTS";
        cursor.moveToFirst();
        String getPassword=cursor.getString(cursor.getColumnIndex("userPassword"));
        return getPassword;
    }

    public void insertEntry(String userName,String email,String password,String sex){

        ContentValues newValues = new ContentValues();
        // Assign values for each row
        newValues.put("userName",userName);
        newValues.put("userEmail",email);
        newValues.put("userPassword",password);
        newValues.put("userSex",sex);

        // Insert the row into your table
        db.insert("user",null,newValues);
    }
}
