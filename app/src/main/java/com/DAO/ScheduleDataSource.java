package com.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.Model.Doctor;
import com.Model.UserSchedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Jeffrey on 4/5/15.
 */
public class ScheduleDataSource extends DataSource {
    private SQLiteDatabase database;

    public ScheduleDataSource(Context context) {
        super(context);
    }

    public List<UserSchedule> getSched(int year, int month, int day, int doctor_id, int u_id) {
        List<UserSchedule> userSched = new ArrayList<>();
        if (database == null) {
            this.database = reopen();
        }
        String selectQuery = "SELECT " + DatabaseHelper.SCHEDULE_TimeID + ", " + DatabaseHelper.USER_SCHEDULE_isConfirmed+ ", " + DatabaseHelper.USER_SCHEDULE_userID + " FROM " + DatabaseHelper.TABLE_USER_SCHEDULE + " inner join " + DatabaseHelper.TABLE_SCHEDULE + " on schedule." + DatabaseHelper.SCHEDULE_ID + " = user_schedule." + DatabaseHelper.USER_SCHEDULE_scheduleID + " where " + DatabaseHelper.USER_SCHEDULE_doctorID + " = " + doctor_id + " AND " + DatabaseHelper.SCHEDULE_Year + " = " + year + " AND " + DatabaseHelper.SCHEDULE_Month + " = " + month + " AND " + DatabaseHelper.SCHEDULE_Date + " = " + day + " ORDER BY "+DatabaseHelper.SCHEDULE_Month+","+DatabaseHelper.SCHEDULE_Date+","+DatabaseHelper.SCHEDULE_TimeID+ " DESC";
        Cursor cursor = database.rawQuery(selectQuery, null);
        //   Log.e("tagged SQL", selectQuery);
        if (cursor.moveToFirst()) {
            do {
                UserSchedule sched = new UserSchedule();
                sched.setTimeID(cursor.getInt(0));
                sched.setIsConfirmed(cursor.getInt(1));
                sched.setUser_ID(cursor.getInt(2));
                //         Log.e("tagged SQL", "may nakuha");

                userSched.add(sched);
            } while (cursor.moveToNext());
        }

        return userSched;
    }

    public void insertSched(int year, int month, int dayOfMonth, int userID, int doc_id, int timeID) {
        if (database == null) {
            this.database = reopen();
        }
        int lastID = 0;
        String selectQuery = "SELECT " + DatabaseHelper.SCHEDULE_ID + " FROM " + DatabaseHelper.TABLE_SCHEDULE + " where " + DatabaseHelper.SCHEDULE_Year + " = " + year + " AND " + DatabaseHelper.SCHEDULE_Month + " = " + month + " AND " + DatabaseHelper.SCHEDULE_Date + " = " + dayOfMonth + " AND " + DatabaseHelper.SCHEDULE_TimeID + " = " + timeID;
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            lastID = cursor.getInt(0);
        } else {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.SCHEDULE_Year, year);
            values.put(DatabaseHelper.SCHEDULE_Month, month);
            values.put(DatabaseHelper.SCHEDULE_Date, dayOfMonth);
            values.put(DatabaseHelper.SCHEDULE_TimeID, timeID);
            database.insert(DatabaseHelper.TABLE_SCHEDULE, null, values);
            selectQuery = "SELECT MAX(" + DatabaseHelper.SCHEDULE_ID + ") " + " FROM " + DatabaseHelper.TABLE_SCHEDULE + "";
            cursor = database.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                lastID = cursor.getInt(0);
            }
        }

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.USER_SCHEDULE_scheduleID, lastID);
        values.put(DatabaseHelper.USER_SCHEDULE_userID, userID);
        values.put(DatabaseHelper.USER_SCHEDULE_doctorID, doc_id);
        values.put(DatabaseHelper.USER_SCHEDULE_isConfirmed, 0);

        database.insert(DatabaseHelper.TABLE_USER_SCHEDULE, null, values);

    }

    public List<UserSchedule> getAllNotif(int docID,int isConfirmed) {
        List<UserSchedule> notifDocs = new ArrayList<>();
        if (database == null) {
            this.database = reopen();
        }
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis() - 1000);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        String selectQuery = "SELECT " + DatabaseHelper.USER_SCHEDULE_ID+", "+DatabaseHelper.USER_SCHEDULE_userID + ", " + DatabaseHelper.USER_SCHEDULE_isConfirmed +", "+DatabaseHelper.SCHEDULE_TimeID+", "+DatabaseHelper.SCHEDULE_Year+", "+DatabaseHelper.SCHEDULE_Month+", "+DatabaseHelper.SCHEDULE_Date+" FROM " + DatabaseHelper.TABLE_USER_SCHEDULE + " inner join " + DatabaseHelper.TABLE_SCHEDULE + " on " + DatabaseHelper.USER_SCHEDULE_scheduleID + " = " + DatabaseHelper.SCHEDULE_ID + " where " + DatabaseHelper.SCHEDULE_Year + " = " + year + " AND " + DatabaseHelper.SCHEDULE_Month + " >= " + month + " AND " + DatabaseHelper.SCHEDULE_Date + " >= " + day + " AND " + DatabaseHelper.USER_SCHEDULE_doctorID + " = " + docID+ " AND " + DatabaseHelper.USER_SCHEDULE_isConfirmed + " = " + isConfirmed + " ORDER BY "+DatabaseHelper.SCHEDULE_Month+","+DatabaseHelper.SCHEDULE_Date+","+DatabaseHelper.SCHEDULE_TimeID+ " ASC";
      //  Log.e("tagg",selectQuery);
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
            UserSchedule user = new UserSchedule();
            user.setId(cursor.getInt(0));
            user.setUser_ID(cursor.getInt(1));
            user.setIsConfirmed(cursor.getInt(2));
            user.setTimeID(cursor.getInt(3));
            user.setYear(cursor.getInt(4));
            user.setMonth(cursor.getInt(5));
            user.setDate(cursor.getInt(6));
            notifDocs.add(user);
            } while (cursor.moveToNext());
        }
        return notifDocs;
    }
    public void confirmAndUpdate(int id)
    {
        if (database == null) {
            this.database = reopen();
        }
     //   String update = "UPDATE " + DatabaseHelper.TABLE_USER_SCHEDULE+" SET "+ DatabaseHelper.USER_SCHEDULE_isConfirmed+ " = 1 "+ " WHERE "+DatabaseHelper.USER_SCHEDULE_ID+ " ="+id;
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.USER_SCHEDULE_isConfirmed, 1);
        String selectQuery = "SELECT " + DatabaseHelper.USER_SCHEDULE_scheduleID +" FROM "+DatabaseHelper.TABLE_USER_SCHEDULE +" where "+DatabaseHelper.USER_SCHEDULE_ID +" = "+id;
        Cursor cursor = database.rawQuery(selectQuery, null);
        int schedID=-1;
        if (cursor.moveToFirst()) {
            schedID=cursor.getInt(0);
        }
        ContentValues values1 = new ContentValues();
        values1.put(DatabaseHelper.USER_SCHEDULE_isConfirmed, -1);
        database.update(DatabaseHelper.TABLE_USER_SCHEDULE,values1,DatabaseHelper.USER_SCHEDULE_scheduleID+ " ="+schedID,null);
        database.update(DatabaseHelper.TABLE_USER_SCHEDULE,values,DatabaseHelper.USER_SCHEDULE_ID+ " ="+id,null);


    }
    public void cancel(int id)
    {
        if (database == null) {
            this.database = reopen();
        }
        //   String update = "UPDATE " + DatabaseHelper.TABLE_USER_SCHEDULE+" SET "+ DatabaseHelper.USER_SCHEDULE_isConfirmed+ " = 1 "+ " WHERE "+DatabaseHelper.USER_SCHEDULE_ID+ " ="+id;
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.USER_SCHEDULE_isConfirmed, -1);
        database.update(DatabaseHelper.TABLE_USER_SCHEDULE,values,DatabaseHelper.USER_SCHEDULE_ID+ " ="+id,null);


    }

}

