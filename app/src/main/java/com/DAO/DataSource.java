package com.DAO;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataSource {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    //private String[] allColumns = { DatabaseHelper.KEY_ID,DatabaseHelper.KEY_NAME};
    private Context con;

    public DataSource(Context context) {
        this.con = context;
        this.dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        this.database = dbHelper.getWritableDatabase();

    }

    public void close() {
        dbHelper.close();
    }

    public SQLiteDatabase reopen() {
        // close();
        DatabaseHelper dbOpenHelper = new DatabaseHelper(this.con);
        database = dbOpenHelper.getWritableDatabase();
        return database;
    }

}
