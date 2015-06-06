package com.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.nickit.R;

/**
 * Created by Jeffrey on 4/4/15.
 */


public class DatabaseHelper extends SQLiteOpenHelper {
    // tables name
    public static final String TABLE_USER = "user";
    public static final String TABLE_DOCTOR = "doctor";
    public static final String TABLE_DOCTOR_SPECIALIZATION = "doctor_specialization";
    public static final String TABLE_USER_FAVEDOC = "user_favedoc";
    public static final String TABLE_DOCTOR_SCHEDULE = "doctor_schedule";
    public static final String TABLE_USER_SCHEDULE = "user_schedule";
    public static final String TABLE_SCHEDULE = "schedule";
    public static final String TABLE_TIME = "time";
    public static final String TABLE_ALERT = "alert";
    // USER Table Columns names
    public static final String USER_ID = "userID";
    public static final String USER_NAME = "userName";
    public static final String USER_PASS = "userPassword";
    // DOCTOR Table Column Names
    public static final String DOCTOR_ID = "doctorID";
    public static final String DOCTOR_NAME = "doctorName";
    public static final String DOCTOR_PASS = "doctorPassword";
    public static final String DOCTOR_SPECID = "doctorSpecID";
    public static final String DOCTOR_OFFICEHOURS = "doctorOfficeHours";
    public static final String DOCTOR_LOCATION = "doctorOfficeLocation";

    // DOCTOR_SPECIALIZATION Table Column Names
    public static final String DOCTOR_SPECIALIZATION_ID = "Doctor_SpecializationID";
    public static final String DOCTOR_SPECIALIZATION_Name = "Doctor_SpecializationName";

    // USER_FAVEDOC Table Column Names
    public static final String USER_FAVEDOC_userID = "User_Favedoc_userID";
    public static final String USER_FAVEDOC_doctorID = "User_Favedoc_doctorID";

    // DOCTOR_SCHEDULE Table Column Names
    public static final String DOCTOR_SCHEDULE_DoctorID = "doctor_schedule_DoctorID";
    public static final String DOCTOR_SCHEDULE_ScheduleID = "doctor_schedule_ScheduleID";

    // USER_SCHEDULE  Table Column Names
    public static final String USER_SCHEDULE_userID = "user_schedule_userID";
    public static final String USER_SCHEDULE_doctorID = "user_schedule_doctorID";
    public static final String USER_SCHEDULE_scheduleID = "user_schedule_scheduleID";
    public static final String USER_SCHEDULE_isConfirmed = "user_schedule_isConfirmed";
    public static final String USER_SCHEDULE_addMessage = "user_schedule_addMessage";
    public static final String USER_SCHEDULE_ID="user_schedule_ID";

    // SCHEDULE Table Column Names
    public static final String SCHEDULE_ID = "Schedule_ID";
    public static final String SCHEDULE_Year = "Schedule_Year";
    public static final String SCHEDULE_Month = "Schedule_Month";
    public static final String SCHEDULE_Date = "Schedule_Date";
    public static final String SCHEDULE_TimeID = "Schedule_TimeID";

    // TIME Table Column Names
    public static final String TIME_ID = "time_ID";
    public static final String TIME_SELECTION = "time_Selection";


    //create statements
    private static String createUserTable = "CREATE TABLE IF NOT EXISTS " + TABLE_USER
            + "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_NAME + " TEXT, " + USER_PASS
            + " TEXT " + ")";
    private static String createDoctorTable = "CREATE TABLE IF NOT EXISTS " + TABLE_DOCTOR
            + "(" + DOCTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DOCTOR_NAME + " TEXT, " + DOCTOR_PASS
            + " TEXT, " + DOCTOR_SPECID + " INTEGER, "+ DOCTOR_OFFICEHOURS
            + " TEXT, " + DOCTOR_LOCATION
            + " TEXT "  + ")";
    private static String createDoctor_SpecializationTable = "CREATE TABLE IF NOT EXISTS " + TABLE_DOCTOR_SPECIALIZATION
            + "(" + DOCTOR_SPECIALIZATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DOCTOR_SPECIALIZATION_Name + " TEXT " + ")";
    private static String createUSER_FAVEDOCTable = "CREATE TABLE IF NOT EXISTS " + TABLE_USER_FAVEDOC
            + "(" + USER_FAVEDOC_userID + " INTEGER, "
            + USER_FAVEDOC_doctorID + " INTEGER " + ")";
    private static String createDOCTOR_SCHEDULETable = "CREATE TABLE IF NOT EXISTS " + TABLE_DOCTOR_SCHEDULE
            + "(" + DOCTOR_SCHEDULE_DoctorID + " INTEGER, "
            + DOCTOR_SCHEDULE_ScheduleID + " INTEGER " + ")";
    private static String createUSER_SCHEDULETable = "CREATE TABLE IF NOT EXISTS " + TABLE_USER_SCHEDULE
            + "(" + USER_SCHEDULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+USER_SCHEDULE_userID + " INTEGER, "
            + USER_SCHEDULE_doctorID + " INTEGER, " + USER_SCHEDULE_scheduleID + " INTEGER, "
            + USER_SCHEDULE_isConfirmed + " INTEGER, "
            + USER_SCHEDULE_addMessage + " TEXT " + ")";
    private static String createSCHEDULETable = "CREATE TABLE IF NOT EXISTS " + TABLE_SCHEDULE
            + "(" + SCHEDULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SCHEDULE_Year + " INTEGER, " + SCHEDULE_Month + " INTEGER, "
            + SCHEDULE_Date + " INTEGER, "
            + SCHEDULE_TimeID + " INTEGER " + ")";
    private static String createTimeTable = "CREATE TABLE IF NOT EXISTS " + TABLE_TIME
            + "(" + TIME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TIME_SELECTION + " TEXT " + ")";


    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "NickIT";
    private Context con;
    private SQLiteDatabase database;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        con = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        database = db;
        db.execSQL(createUserTable);
        db.execSQL(createDoctorTable);
        db.execSQL(createDoctor_SpecializationTable);
        db.execSQL(createUSER_FAVEDOCTable);
        db.execSQL(createDOCTOR_SCHEDULETable);
        db.execSQL(createUSER_SCHEDULETable);
        db.execSQL(createSCHEDULETable);
        db.execSQL(createTimeTable);
        populateDoctorDB();
        populateDoctorSpecializationDB();
        populateUserDB();
        populateTimeDB();
    }

    private void populateDoctorDB() {
        Resources res = con.getResources();
        String[] DoctorNames = res.getStringArray(R.array.doctor_name);
        String[] DoctorPasswords = res.getStringArray(R.array.doctor_password);
        String[] DoctorSpecIDs = res.getStringArray(R.array.doctor_specID);
        String[] Doctor_OfficeHours = res.getStringArray(R.array.doctor_officehours);
        String[] DoctorLocation = res.getStringArray(R.array.doctor_location);

        for (int i = 0; i < DoctorNames.length; i++) {
            String DoctorName = DoctorNames[i];
            String DoctorPass = DoctorPasswords[i];
            String DoctorSpecID=DoctorSpecIDs[i];
            String DoctorOffice=Doctor_OfficeHours[i];
            String DoctorLoc=DoctorLocation[i];
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.DOCTOR_NAME, DoctorName);
            values.put(DatabaseHelper.DOCTOR_PASS, DoctorPass);
            values.put(DatabaseHelper.DOCTOR_SPECID, Integer.valueOf(DoctorSpecID));
            values.put(DatabaseHelper.DOCTOR_OFFICEHOURS, DoctorOffice);
            values.put(DatabaseHelper.DOCTOR_LOCATION, DoctorLoc);

            database.insert(DatabaseHelper.TABLE_DOCTOR, null, values);
        }
        Log.e("POPULATED DOCTOR", "SUCCESS");

    }

    private void populateDoctorSpecializationDB() {
        Resources res = con.getResources();
        String[] DoctorSpecializations = res.getStringArray(R.array.doctor_specializations);

        for (int i = 1; i < DoctorSpecializations.length; i++) {
            String Specialization = DoctorSpecializations[i];
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.DOCTOR_SPECIALIZATION_Name, Specialization);
            database.insert(DatabaseHelper.TABLE_DOCTOR_SPECIALIZATION, null, values);
        }
        Log.e("POPULATED DOCTOR_SPECIALIZATION", "SUCCESS");

    }
    private void populateUserDB() {
        Resources res = con.getResources();
        String[] UserNames = res.getStringArray(R.array.user_name);
        String[] UserPasswords = res.getStringArray(R.array.user_password);

        for (int i = 0; i < UserNames.length; i++) {
            String UserName = UserNames[i];
            String UserPass = UserPasswords[i];
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.USER_NAME, UserName);
            values.put(DatabaseHelper.USER_PASS, UserPass);
            database.insert(DatabaseHelper.TABLE_USER, null, values);
        }
        Log.e("POPULATED USER", "SUCCESS");

    }
    private void populateTimeDB() {
        Resources res = con.getResources();
        String[] TimeSchedules = res.getStringArray(R.array.timeSchedule);

        for (int i = 0; i < TimeSchedules.length; i++) {
            String Time = TimeSchedules[i];
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.TIME_SELECTION, Time);
            database.insert(DatabaseHelper.TABLE_TIME, null, values);
        }
        Log.e("POPULATED TIME", "SUCCESS");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Steps to upgrade the database for the new version ...
    }
}


