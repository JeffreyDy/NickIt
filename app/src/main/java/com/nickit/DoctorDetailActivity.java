package com.nickit;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.DAO.NickITDataSource;
import com.Model.Doctor;
import com.controllers.DoctorDetailController;
import com.controllers.FindDoctorController;


public class DoctorDetailActivity extends Activity {
    DoctorDetailController docDetailController;
    private NickITDataSource datasource = new NickITDataSource(this);
    private String doctor_name;
    private int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_details);
        user_id = Integer.valueOf(UserActivity.getUser_id());
        Intent i = getIntent();
        doctor_name = i.getStringExtra("doctor");
        Doctor doc = datasource.getDoctor().getDoctorDetail(doctor_name);
        TextView textViewDocName = (TextView) findViewById(R.id.DrNameLargeText);
        textViewDocName.setText(doc.getName());
        TextView textViewDocSpec = (TextView) findViewById(R.id.DrSpecDetails);
        textViewDocSpec.setText(datasource.getSpecialization().getSpecName(doc.getSpecID()));
        TextView textViewOfficeHours = (TextView) findViewById(R.id.textView5);
        textViewOfficeHours.setText("Monday to Friday: 7:30am to 5:30pm");
        TextView textViewLocation = (TextView) findViewById(R.id.textView6);
        textViewLocation.setText(doc.getLocation());

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BookNow.class);
                intent.putExtra("doctor_name",doctor_name);
                finish();
                startActivity(intent);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
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
}
