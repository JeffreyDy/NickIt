package com.DAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class NickITDataSource extends DataSource {
    private SQLiteDatabase database;
    private UserDataSource user;
    private DoctorDataSource doctor;
    private ScheduleDataSource schedule;
    private SpecializationSource specialization;

    public NickITDataSource(Context context) {
        super(context);
        user = new UserDataSource(context);
        doctor = new DoctorDataSource(context);
        schedule = new ScheduleDataSource(context);
        specialization=new SpecializationSource(context);

    }

    public SpecializationSource getSpecialization() {
        return specialization;
    }

    public void setSpecialization(SpecializationSource specialization) {
        this.specialization = specialization;
    }

    public UserDataSource getUser() {
        return user;
    }

    public void setUser(UserDataSource user) {
        this.user = user;
    }

    public DoctorDataSource getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorDataSource doctor) {
        this.doctor = doctor;
    }

    public ScheduleDataSource getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleDataSource schedule) {
        this.schedule = schedule;
    }


}
