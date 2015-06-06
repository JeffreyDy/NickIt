package com.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.Model.Doctor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeffrey on 4/5/15.
 */
public class UserDataSource extends DataSource {
    private SQLiteDatabase database;

    public UserDataSource(Context context) {
        super(context);
    }

    public int getUserID(String name, String pass) {
        int i = 0;
        if (database == null) {
            this.database = reopen();
        }
        String selectQuery = "SELECT " + DatabaseHelper.USER_ID + " FROM " + DatabaseHelper.TABLE_USER + " where " + DatabaseHelper.USER_NAME + " = \"" + name + "\" AND " + DatabaseHelper.USER_PASS + " = \"" + pass + "\"";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            i = cursor.getInt(0);
        }
        cursor.close();
        return i;
    }
    public String getUserName(int id) {
        String userName =null;
        if (database == null) {
            this.database = reopen();
        }
        String selectQuery = "SELECT " + DatabaseHelper.USER_NAME + " FROM " + DatabaseHelper.TABLE_USER + " where " + DatabaseHelper.USER_ID + " = " + id ;
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            userName = cursor.getString(0);
        }
        cursor.close();
        return userName;
    }

    public void insertFave(int u_id, int doc_id) {
        if (database == null) {
            this.database = reopen();
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.USER_FAVEDOC_userID, u_id);
        values.put(DatabaseHelper.USER_FAVEDOC_doctorID, doc_id);
        database.insert(DatabaseHelper.TABLE_USER_FAVEDOC, null, values);
    }

    public Boolean isFave(int u_id, int doc_id) {
        Boolean i = false;
        if (database == null) {
            this.database = reopen();
        }
        String selectQuery = "SELECT *" + " FROM " + DatabaseHelper.TABLE_USER_FAVEDOC + " where " + DatabaseHelper.USER_FAVEDOC_userID + " = \"" + u_id + "\" AND " + DatabaseHelper.USER_FAVEDOC_doctorID + " = \"" + doc_id + "\"";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            i = true;
        }
        cursor.close();
        return i;
    }

    public void DeleteFave(int u_id, int doc_id) {
        database.delete(DatabaseHelper.TABLE_USER_FAVEDOC, DatabaseHelper.USER_FAVEDOC_userID + "=" + u_id + " AND " + DatabaseHelper.USER_FAVEDOC_doctorID + " = " + doc_id, null);
    }

    public List<Integer> getAllFaveDocId(int usID) {
        List<Integer> arrInt = new ArrayList<Integer>();
        if (database == null) {
            this.database = reopen();
        }
        String selectQuery = "SELECT *" + " FROM " + DatabaseHelper.TABLE_USER_FAVEDOC + " WHERE " + DatabaseHelper.USER_FAVEDOC_userID + " = " + usID + "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                arrInt.add(cursor.getInt(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrInt;

    }
}
