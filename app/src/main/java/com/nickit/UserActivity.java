package com.nickit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;


public class UserActivity extends FragmentActivity {
    private FragmentTabHost myTabHost;


    private static String user_id;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlayout);
        Intent i=getIntent();
        user_id= i.getStringExtra("user");
        myTabHost =(FragmentTabHost) findViewById(R.id.tabHost);
        myTabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);
        myTabHost.addTab(
                myTabHost.newTabSpec("tab1").setIndicator(null, getResources().getDrawable(R.drawable.ic_tab_find)),
                findDoctorActivity.class, null);
        myTabHost.addTab(
                myTabHost.newTabSpec("tab2").setIndicator(null, getResources().getDrawable(R.drawable.ic_tab_fave)),
                faveDoctorActivity.class, null);
        myTabHost.addTab(
                myTabHost.newTabSpec("tab3").setIndicator(null, getResources().getDrawable(R.drawable.ic_tab_settings)),
                setting_userActivity.class, null);


    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to log-out?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
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
        return user_id;
    }



}
