package com.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.Model.Doctor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeffrey on 4/5/15.
 */
public class DoctorDataSource extends DataSource {
    private SQLiteDatabase database;

    public DoctorDataSource(Context context) {
        super(context);
    }

    public int getUserID(String name, String pass) {
        int i = 0;
        if (database == null) {
            this.database = reopen();
        }
        String selectQuery = "SELECT " + DatabaseHelper.DOCTOR_ID + " FROM " + DatabaseHelper.TABLE_DOCTOR + " where " + DatabaseHelper.DOCTOR_NAME + " = \"" + name + "\" AND " + DatabaseHelper.DOCTOR_PASS + " = \"" + pass + "\"";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            i = cursor.getInt(0);
        }
        cursor.close();
        return i;
    }

    public int getUserID(String name) {
        int i = 0;
        if (database == null) {
            this.database = reopen();
        }
        String selectQuery = "SELECT " + DatabaseHelper.DOCTOR_ID + " FROM " + DatabaseHelper.TABLE_DOCTOR + " where " + DatabaseHelper.DOCTOR_NAME + " = \"" + name + "\"";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            i = cursor.getInt(0);
        }
        cursor.close();
        return i;
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<Doctor>();
        if (database == null) {
            this.database = reopen();
        }
        String selectQuery = "SELECT *" + " FROM " + DatabaseHelper.TABLE_DOCTOR + " ";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Doctor doc = new Doctor();
                doc.setId(cursor.getInt(0));
                doc.setName(cursor.getString(1));
                doc.setSpecID(cursor.getInt(3));
                doc.setOfficeHours(cursor.getString(4));
                doc.setLocation(cursor.getString(5));
                doctors.add(doc);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return doctors;
    }

    public List<Doctor> getDoctorsbySpecID(int Specid) {
        List<Doctor> doctors = new ArrayList<Doctor>();
        if (database == null) {
            this.database = reopen();
        }
        String selectQuery = "SELECT *" + " FROM " + DatabaseHelper.TABLE_DOCTOR + " WHERE " + DatabaseHelper.DOCTOR_SPECID + " = " + Specid + "";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Doctor doc = new Doctor();
                doc.setId(cursor.getInt(0));
                doc.setName(cursor.getString(1));
                doc.setSpecID(cursor.getInt(3));
                doc.setOfficeHours(cursor.getString(4));
                doc.setLocation(cursor.getString(5));
                doctors.add(doc);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return doctors;
    }

    public Doctor getDoctorDetail(String name) {
        if (database == null) {
            this.database = reopen();
        }
        Doctor doc = new Doctor();
        String selectQuery = "SELECT *" + " FROM " + DatabaseHelper.TABLE_DOCTOR + " WHERE " + DatabaseHelper.DOCTOR_NAME + " = \"" + name + "\"";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

            doc.setId(cursor.getInt(0));
            doc.setName(cursor.getString(1));
            doc.setSpecID(cursor.getInt(3));
            doc.setOfficeHours(cursor.getString(4));
            doc.setLocation(cursor.getString(5));

        }
        cursor.close();
        return doc;
    }
    public Doctor getDoctorDetail(int id) {
        if (database == null) {
            this.database = reopen();
        }
        Doctor doc = new Doctor();
        String selectQuery = "SELECT *" + " FROM " + DatabaseHelper.TABLE_DOCTOR + " WHERE " + DatabaseHelper.DOCTOR_ID + " = \"" + id + "\"";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

            doc.setId(cursor.getInt(0));
            doc.setName(cursor.getString(1));
            doc.setSpecID(cursor.getInt(3));
            doc.setOfficeHours(cursor.getString(4));
            doc.setLocation(cursor.getString(5));

        }
        cursor.close();
        return doc;
    }
}
