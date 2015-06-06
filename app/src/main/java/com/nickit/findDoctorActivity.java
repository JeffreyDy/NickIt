package com.nickit;

import android.app.LocalActivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.DAO.NickITDataSource;
import com.Model.Doctor;
import com.controllers.FindDoctorController;

import java.util.List;


public class findDoctorActivity extends Fragment {
    private ListView DoctorListView;
    private NickITDataSource datasource;
    private String user_id;
    FindDoctorController findController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        user_id=UserActivity.getUser_id();
        datasource=new NickITDataSource(inflater.getContext());
        datasource.open();
        View V = inflater.inflate(R.layout.finddoctor, container, false);
        findController=new FindDoctorController(V,datasource,Integer.valueOf(user_id),this);
        Spinner spinner = (Spinner) V.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(inflater.getContext(),
                R.array.doctor_specializations, R.layout.spinner_layout_style);
        adapter.setDropDownViewResource(R.layout.spinner_layout_style);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(findController.getSpinnerOnItemSelected());


        List<Doctor> doctorList=datasource.getDoctor().getAllDoctors();
        DoctorListView=(ListView)V.findViewById(R.id.listView);
        DoctorListView.setOnItemClickListener(findController.getDoctorListListener());
        CustomItemAdapter adapter1 = new CustomItemAdapter(V.getContext(),
                R.layout.doctorlistview, doctorList,Integer.valueOf(user_id),this, datasource,V);
        adapter1.setFindController(findController);
        DoctorListView.setAdapter(adapter1);
        return V;
    }
}
    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finddoctor);
        Intent i=getIntent();
        user_id= i.getStringExtra("user");
        findController=new FindDoctorController(this, datasource,Integer.valueOf(user_id));
        findController.setLocalActivity( getLocalActivityManager());
        datasource.open();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.doctor_specializations, R.layout.spinner_layout_style);
        adapter.setDropDownViewResource(R.layout.spinner_layout_style);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(findController.getSpinnerOnItemSelected());

        List<Doctor> doctorList=datasource.getDoctor().getAllDoctors();
        DoctorListView=(ListView)findViewById(R.id.listView);
        DoctorListView.setOnItemClickListener(

                findController.getDoctorListListener());
        CustomItemAdapter adapter1 = new CustomItemAdapter(getBaseContext(),
                R.layout.doctorlistview, doctorList,Integer.valueOf(user_id),this, datasource);
        //adapter1.setFindController(findController);
        DoctorListView.setAdapter(adapter1);
    }
        //setonitemselected;


    @Override
    public void onBackPressed () {
        Toast.makeText(this, "Back Button Disabled", Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find_doctor, menu);
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
}*/
