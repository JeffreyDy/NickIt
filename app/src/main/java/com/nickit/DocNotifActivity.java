package com.nickit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.DAO.NickITDataSource;
import com.Model.UserSchedule;

import java.util.List;


public class DocNotifActivity extends Fragment {
    private NickITDataSource datasource;
    private ListView DoctorNotifView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View V = inflater.inflate(R.layout.activity_doc_notif, container, false);
        datasource=new NickITDataSource(inflater.getContext());
        List<UserSchedule> notifs=datasource.getSchedule().getAllNotif(Integer.valueOf(DoctorActivity.getUser_id()),0);
       // Log.e("TAGG",notifs.size()+"HEHE");
        DoctorNotifView=(ListView)V.findViewById(R.id.listView2);
        DocNotifAdapter adapter1 = new DocNotifAdapter(V.getContext(),
                R.layout.doctorlistview, notifs,Integer.valueOf(DoctorActivity.getUser_id()),this, datasource);
        adapter1.setDocView(DoctorNotifView);
        adapter1.setList(notifs);
        DoctorNotifView.setAdapter(adapter1);

        return V;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
