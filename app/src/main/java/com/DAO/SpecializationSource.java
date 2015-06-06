package com.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Jeffrey on 4/5/15.
 */
public class SpecializationSource extends DataSource  {
    private SQLiteDatabase database;
    public SpecializationSource(Context context) {
        super(context);
    }
    public int getSpecID(String name)
    {
        int id=0;
        if(database==null)
        {
            this.database=reopen();
        }
        String selectQuery="SELECT "+ DatabaseHelper.DOCTOR_SPECIALIZATION_ID+" FROM "+DatabaseHelper.TABLE_DOCTOR_SPECIALIZATION+" where "+DatabaseHelper.DOCTOR_SPECIALIZATION_Name+ " = \""+name+ "\"";
        Cursor cursor= database.rawQuery(selectQuery,null);
        if(cursor.moveToFirst())
        {
            id=cursor.getInt(0);
        }
        cursor.close();
        return id;

    }

    public String getSpecName(int id)
    {
        String name=null;
        if(database==null)
        {
            this.database=reopen();
        }
        String selectQuery="SELECT "+ DatabaseHelper.DOCTOR_SPECIALIZATION_Name+" FROM "+DatabaseHelper.TABLE_DOCTOR_SPECIALIZATION+" where "+DatabaseHelper.DOCTOR_SPECIALIZATION_ID+ " = "+id+ " ";
        Cursor cursor= database.rawQuery(selectQuery,null);
        if(cursor.moveToFirst())
        {
            name=cursor.getString(0);
        }
        cursor.close();
        return name;

    }
}
