package com.example.aum.benjaresturant;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by masterUNG on 2/29/16 AD.
 */
public class MyManage {


    //Explicit
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;

    public static final String user_table = "userTABLE";
    public static final String column_id = "_id";
    public static final String column_user = "User";
    public static final String column_pass = "Password";
    public static final String column_name = "Name";

    public static final String food_table = "foodTABlE";
    public static final String column_food = "Food";
    public static final String column_price = "Price";
    public static final String column_source = "Source";

    public MyManage(Context context) {

        //Create Database
        myOpenHelper = new MyOpenHelper(context);
        writeSqLiteDatabase = myOpenHelper.getWritableDatabase();
        readSqLiteDatabase = myOpenHelper.getReadableDatabase();

    }   // Constructor

    public long addFood(String strFood,
                        String strPrice,
                        String strSource) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(column_food, strFood);
        contentValues.put(column_price, strPrice);
        contentValues.put(column_source, strSource);

        return writeSqLiteDatabase.insert(food_table, null, contentValues);
    }



    //Add Value to userTABLE
    public long addUser(String strUser,
                        String strPassword,
                        String strName) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(column_user, strUser);
        contentValues.put(column_pass, strPassword);
        contentValues.put(column_name, strName);

        return writeSqLiteDatabase.insert(user_table, null, contentValues);
    }


}   // Main Class