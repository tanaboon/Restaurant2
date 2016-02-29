package com.example.aum.benjaresturant;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    //Explicit
    private MyManage myManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Request Database
        myManage = new MyManage(this);
        //Test add Value
        //testAddValue();

        //Delete All SQLite
        deleteAllSQLite();

    }   // Main Method

    private void deleteAllSQLite() {
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_APPEND, null);
        sqLiteDatabase.delete(MyManage.food_table, null, null);
        sqLiteDatabase.delete(MyManage.user_table, null, null);
    }

    private void testAddValue() {
        myManage.addUser("testuser1", "1234", "โดรามอน");
        myManage.addFood("ไข่เจียว", "100", "urlFood");
    }
} // Main Class
