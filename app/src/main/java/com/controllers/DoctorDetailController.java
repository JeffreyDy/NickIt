package com.controllers;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.DAO.NickITDataSource;
import com.nickit.DoctorDetailActivity;
import com.nickit.findDoctorActivity;

import java.util.ArrayList;

/**
 * Created by Jeffrey on 4/5/15.
 */
public class DoctorDetailController {
    private DoctorDetailActivity main;
    private NickITDataSource nickData;
    private ListView DoctorListView;
    private int user_id;
    private LocalActivityManager localActivity;

    private ArrayList<String> mIdList;

    public DoctorDetailController(DoctorDetailActivity mainActivity, NickITDataSource NickData, int uID) {
        nickData = NickData;
        main = mainActivity;
        user_id = uID;
    }

    public View.OnClickListener getOnBackClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), findDoctorActivity.class);
                intent.putExtra("user",String.valueOf(user_id));

            }
        };
    }


}
