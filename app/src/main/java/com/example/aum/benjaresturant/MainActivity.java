package com.example.aum.benjaresturant;

import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private MyManage myManage;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Wigdet
        bindWidget();








        //Request Database
        myManage = new MyManage(this);

        //Test add Value
        //testAddValue();

        //Delete All SQLite
        deleteAllSQLite();

        //Synchronize JSON to SQLite
        synJSONtoSQLite();

    }   // Main Method

    public void clickLogin(View view) {
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        if (userString.equals("")  || (passwordString.equals(""))) {
            //Have Space
            MyAlertDialog myAlertDialog = new MyAlertDialog();
            myAlertDialog.myDialog(MainActivity.this, "มีช่องว่าง", "กรุณาฟังให้จบ");
        } else {
            // No Space

        }

    } // ClickLogin


    private void bindWidget() {

        userEditText = (EditText) findViewById(R.id.editText);
        passwordEditText = (EditText) findViewById(R.id.editText2);

    }

    private void synJSONtoSQLite() {

        //Connected http
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        int intTimes = 0;
        while (intTimes <= 1) {

            //1. Create InputStream
            InputStream inputStream = null;
            String[] urlJSON = new String[2];
            urlJSON[0] = "http://swiftcodingthai.com/29feb/php_get_user_master.php";
            urlJSON[1] = "http://swiftcodingthai.com/29feb/php_get_food.php";
            HttpPost httpPost = null;

            try {

                HttpClient httpClient = new DefaultHttpClient();
                httpPost = new HttpPost(urlJSON[intTimes]);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();

            } catch (Exception e) {
                Log.d("banja", "InputStream ==> " + e.toString());
            }

            //2. Create JSON String
            String strJSON = null;
            try {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder stringBuilder = new StringBuilder();
                String strLine = null;

                while ((strLine = bufferedReader.readLine()) != null) {
                    stringBuilder.append(strLine);
                } //while
                inputStream.close();
                strJSON = stringBuilder.toString();

            } catch (Exception e) {
                Log.d("banja", "JSON Strint ==> " + e.toString());
            }

            //3. Update to SQLite
            try {

                JSONArray jsonArray = new JSONArray(strJSON);
                for (int i=0;i<jsonArray.length();i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    switch (intTimes) {
                        case 0:
                            //For userTABLE
                            String strUser = jsonObject.getString(MyManage.column_user);
                            String strPass = jsonObject.getString(MyManage.column_pass);
                            String strName = jsonObject.getString(MyManage.column_name);

                            myManage.addUser(strUser, strPass, strName);

                            break;
                        case 1:
                            //For foodTABLE
                            String strFood = jsonObject.getString(MyManage.column_food);
                            String strPrice = jsonObject.getString(MyManage.column_price);
                            String strSource = jsonObject.getString(MyManage.column_source);

                            myManage.addFood(strFood, strPrice, strSource);

                            break;
                    }   // switch

                }   // for

            } catch (Exception e) {
                Log.d("banja", "Update ==> " + e.toString());
            }


            intTimes += 1;
        }   // while

    }   // synJSONtoSQLite

    private void deleteAllSQLite() {
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabase.delete(MyManage.food_table, null, null);
        sqLiteDatabase.delete(MyManage.user_table, null, null);
    }

    private void testAddValue() {
        myManage.addUser("testUser", "1234", "โดรามอน");
        myManage.addFood("ไข่เจียว", "100", "urlFood");
    }

}   // Main Class