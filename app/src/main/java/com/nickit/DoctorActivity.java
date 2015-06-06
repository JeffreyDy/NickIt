package com.nickit;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.DAO.NickITDataSource;


public class DoctorActivity extends FragmentActivity {

    private FragmentTabHost myTabHost;
    private NickITDataSource datasource;

    private static String doctorID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        Intent i=getIntent();
        datasource=new NickITDataSource(this);
        doctorID= i.getStringExtra("doctorID");
        myTabHost =(FragmentTabHost) findViewById(R.id.tabHost);
        myTabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);
        myTabHost.addTab(
                myTabHost.newTabSpec("tab1").setIndicator(null, getResources().getDrawable(R.drawable.ic_tab_confirmed)),
                DoctorConfirmed.class, null);

        if(datasource.getSchedule().getAllNotif(Integer.valueOf(doctorID),0).size()==0) {
            myTabHost.addTab(
                    myTabHost.newTabSpec("tab2").setIndicator(null, getResources().getDrawable(R.drawable.ic_tab_notif)),
                    DocNotifActivity.class, null);
        }
        else
        {
            myTabHost.addTab(
                    myTabHost.newTabSpec("tab2").setIndicator(null, getResources().getDrawable(R.drawable.redexc)),
                    DocNotifActivity.class, null);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_doctor, menu);
        return true;
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
    public static String getUser_id() {
        return doctorID;
    }
}
