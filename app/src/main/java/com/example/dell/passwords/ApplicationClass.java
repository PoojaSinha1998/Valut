package com.example.dell.passwords;

import android.app.Application;
import android.content.Context;

import com.example.dell.passwords.Pojo.RecordsPojo;

import java.util.ArrayList;

public class ApplicationClass extends Application {

    public static String userName;
    private static ApplicationClass instance;
    private static String title;
    private static String userTitle;
    private static String password;
    private static String note;


    public static ArrayList<RecordsPojo> recordsPojoArrayList;
    private static boolean yesEdit;

    public static ApplicationClass getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance.getApplicationContext();
    }

    public static void setNote(String note1) {

        note = note1;
    }

    public static void setPassword(String password1) {
        password = password1;
    }

    public static void setUserTitle(String username1) {
        userTitle = username1;
    }

    public static void setTitle(String title1) {
        title = title1;
    }

    public static String getTitle() {
        return title;
    }

    public static String getUserTitle() {
        return userTitle;
    }

    public static String getPassword() {
        return password;
    }

    public static String getNote() {
        return note;
    }

    public static void setEdit(boolean  b) {
        yesEdit = b;
    }

    public static boolean isYesEdit() {
        return yesEdit;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        ApplicationClass.userName = userName;
    }

    public static ArrayList<RecordsPojo> getRecordsPojoArrayList() {
        return recordsPojoArrayList;
    }

    public static void setRecordsPojoArrayList(ArrayList<RecordsPojo> recordsPojoArrayLis) {

        ApplicationClass.recordsPojoArrayList = recordsPojoArrayLis;
    }
}
