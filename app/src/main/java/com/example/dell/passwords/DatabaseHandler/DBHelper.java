package com.example.dell.passwords.DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.dell.passwords.ApplicationClass;
import com.example.dell.passwords.Pojo.RecordsPojo;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDBTwentine.db";
    public static final String CONTACTS_TABLE_NAME = "passwordList";
    public static final String CONTACTS_TABLE_LOGIN_CREDENTIALS = "loginCredentials";
    public static final String CONTACTS_TABLE_MASTER_PASSWORD = "loginCredentials";


    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_USER_ID = "userId";
    public static final String CONTACTS_COLUMN_USER_PASSWORD = "userPassword";
    public static final String CONTACTS_COLUMN_USER_DESCRIPTION = "description";

    public static final String CONTACTS_COLUMN_LOGIN = "login";
    public static final String CONTACTS_COLUMN_PASSWORD = "password";
    public static final String CONTACTS_COLUMN_PIN = "masterPassword";


    ArrayList<RecordsPojo> recordsPojos ;
    int i = 0;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + CONTACTS_TABLE_NAME +
                        "( " + CONTACTS_COLUMN_ID + " integer primary key autoincrement , " + CONTACTS_COLUMN_NAME + " text," + CONTACTS_COLUMN_USER_ID + " text," + CONTACTS_COLUMN_USER_PASSWORD + " text," + CONTACTS_COLUMN_USER_DESCRIPTION + " text," + CONTACTS_COLUMN_LOGIN + " text)"
        );
        db.execSQL(
                "create table " + CONTACTS_TABLE_LOGIN_CREDENTIALS +
                        "( " + CONTACTS_COLUMN_ID + " integer primary key autoincrement , " + CONTACTS_COLUMN_LOGIN + " text," + CONTACTS_COLUMN_PASSWORD + " text," + CONTACTS_COLUMN_PIN + " text)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_LOGIN_CREDENTIALS);
        onCreate(db);
    }

    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + CONTACTS_TABLE_LOGIN_CREDENTIALS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }


    public boolean insertSignUpData(String sUser, String sPassword, String sPin) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CONTACTS_COLUMN_LOGIN, sUser.trim());
        values.put(CONTACTS_COLUMN_PASSWORD, sPassword);
        values.put(CONTACTS_COLUMN_PIN, sPin);

        db.insert(CONTACTS_TABLE_LOGIN_CREDENTIALS, null, values);

            db.close();
        return true;
    }

    public int CheckLogin(String username, String password) {
        Log.d("VT", "CheckLogin() called with: username = [" + username + "], password = [" + password + "]");
        SQLiteDatabase db = this.getWritableDatabase();
        String usernam = username.trim();
        Log.d("VT","username in side login : "+usernam);
        String query = "SELECT * FROM " + CONTACTS_TABLE_LOGIN_CREDENTIALS + " WHERE " + CONTACTS_COLUMN_LOGIN + " = '" + usernam + "'";
        Cursor cursor = db.rawQuery(query, null);
        Log.d("VT", "value of cursor " + cursor.getCount());
        if (cursor.equals(0)) {
            i = 0;
        }
        else  if (cursor.moveToFirst()) {

                    String pass = cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_PASSWORD));
                    Log.d("VT", "password " + pass);
                    if (pass.equals(password)) {
                        return 1;
                    }
                    return 2;
                    }
        db.close();
        return 0;
    }
    public boolean ChangePassword(String username , String oldPassword, String newPassword){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + CONTACTS_TABLE_LOGIN_CREDENTIALS + " WHERE " + CONTACTS_COLUMN_LOGIN + " = '" + username.trim() + "'";
        Cursor cursor = db.rawQuery(query, null);
        Log.d("VT", "value of cursor " + cursor.getCount());
        if (cursor.equals(0)) {
            return false;
        }
        else  if (cursor.moveToFirst()) {

            String pass = cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_PASSWORD));
            Log.d("VT", "password " + pass);
            if (pass.equals(oldPassword)) {

                Log.d("VT","old password found : "+oldPassword);

                ContentValues data=new ContentValues();
                data.put("password",newPassword);

                int u = db.update(CONTACTS_TABLE_LOGIN_CREDENTIALS, data, CONTACTS_COLUMN_LOGIN +" = '" + username +"'", null);

                if(u>0)
                {
                    return true;
                }
                else
                    return false;
            }

        }
            return false;
    }
    public boolean updateTableQuan(String title, String name, String password,String note) {
        int i=0;

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(CONTACTS_COLUMN_USER_ID, name);
        cv.put(CONTACTS_COLUMN_USER_PASSWORD,password);
        cv.put(CONTACTS_COLUMN_USER_DESCRIPTION, note);

        i =db.update(CONTACTS_TABLE_NAME,cv,CONTACTS_COLUMN_NAME + " = '"+ title+ "'",null);
        Log.d("My Query", String.valueOf(i));
        if(i>0) {
            return true;
        }
        return false;


    }


    public boolean InsertRecord(String urlname, String userId, String urlpass, String des, String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CONTACTS_COLUMN_NAME, urlname.trim());
        values.put(CONTACTS_COLUMN_USER_ID, userId);
        values.put(CONTACTS_COLUMN_USER_PASSWORD, urlpass);
        values.put(CONTACTS_COLUMN_USER_DESCRIPTION, des);
        values.put(CONTACTS_COLUMN_LOGIN, username);

        long i = db.insert(CONTACTS_TABLE_NAME, null, values);
        if (i > 0) {
            db.close();
            return true;
        }
        db.close();
        return false;


    }

    public int getAllRecord(String userName) {
//        if(ApplicationClass.recordsPojoArrayList.size()>0) {
//            ApplicationClass.recordsPojoArrayList.clear();
//        }
        recordsPojos = new ArrayList<RecordsPojo>();
        i = 0;
        Log.d("VT", "getAllRecords() called with: username = [" + userName + "]");
        SQLiteDatabase db = this.getWritableDatabase();
        String usernam = userName.trim();
        String query = "SELECT * FROM " + CONTACTS_TABLE_NAME + " WHERE " + CONTACTS_COLUMN_LOGIN + " = '" + usernam + "'";
        Log.d("VT", "Query " + query);
        Cursor cursor = db.rawQuery(query, null);
        Log.d("VT", "value of cursor 1 " + cursor.getCount());
        if (cursor.equals(0)) {
            i = 0;
        } else {

            if (cursor.moveToFirst()) {
                do {

                    String name = cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_NAME));
                    String userID = cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_USER_ID));
                    String password = cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_USER_PASSWORD));
                    String description = cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_USER_DESCRIPTION));

                    recordsPojos.add(new RecordsPojo(name, userID, password, description));
                    i++;
                } while (cursor.moveToNext());

            }
            ApplicationClass.setRecordsPojoArrayList(null);
            Log.d("VT","POJO Arraylist size :" +recordsPojos.size());
            ApplicationClass.setRecordsPojoArrayList(recordsPojos);
        }

        Log.d("VT", "value of i " + i);
        db.close();
        return i;
    }

    public String  getPinForPassword(String args1) {
        SQLiteDatabase db = this.getWritableDatabase();
        String password = null;
        String usernam = args1.trim();
        String query = "SELECT * FROM " + CONTACTS_TABLE_LOGIN_CREDENTIALS + " WHERE " + CONTACTS_COLUMN_LOGIN + " = '" + usernam + "'";
        Log.d("VT", "Query " + query);
        Cursor cursor = db.rawQuery(query, null);
        Log.d("VT", "value of cursor 1 " + cursor.getCount());
        if (cursor.equals(0)) {
            i = 0;
        } else {

            if (cursor.moveToFirst()) {
                do {

                    password = cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_PIN));
                    i++;
                } while (cursor.moveToNext());

            }

        }

        db.close();
        return password;
    }

    public String getDataToDelete(String title, String username, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query =   "SELECT * FROM " + CONTACTS_TABLE_NAME + " WHERE " + CONTACTS_COLUMN_NAME + " = '" + title + "' AND " + CONTACTS_COLUMN_USER_ID + " = '" + username + "'  AND " + CONTACTS_COLUMN_USER_PASSWORD + " = '" + password + "' ";
        Log.d("VT", "Query " + query);
        Cursor cursor = db.rawQuery(query, null);
        Log.d("VT", "value of cursor 1 " + cursor.getCount());
        if (cursor.equals(0)) {
            i = 0;
        } else {

            if (cursor.moveToFirst()) {
                do {

                    password = cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_ID));
                    i++;
                } while (cursor.moveToNext());

            }

        }

        db.close();
        return password;
    }
    //Delete data from database
    public boolean deleteRow(String id)
    {
        int a=0;
        SQLiteDatabase db = this.getWritableDatabase();
        a= db.delete(CONTACTS_TABLE_NAME , CONTACTS_COLUMN_ID + " = '" + id + "'", null);
        Log.d("Value of a",String.valueOf(a));
        db.close();
        if(a>0)
        {
            return true;
        }
        return false;
    }

    public boolean ChangePin(String username, String oldPin, String newPin) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + CONTACTS_TABLE_LOGIN_CREDENTIALS + " WHERE " + CONTACTS_COLUMN_LOGIN + " = '" + username.trim() + "'";
        Cursor cursor = db.rawQuery(query, null);
        Log.d("VT", "value of cursor " + cursor.getCount());
        if (cursor.equals(0)) {
            return false;
        }
        else  if (cursor.moveToFirst()) {

            String pin = cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_PIN));
            Log.d("VT", "password " + pin);
            if (pin.equals(oldPin)) {

                Log.d("VT","old password found : "+oldPin);

                ContentValues data=new ContentValues();
                data.put("masterPassword",newPin);

                int u = db.update(CONTACTS_TABLE_LOGIN_CREDENTIALS, data, CONTACTS_COLUMN_LOGIN +" = '" + username +"'", null);

                if(u>0)
                {
                    return true;
                }
                else
                    return false;
            }

        }

        return false;
    }


}
