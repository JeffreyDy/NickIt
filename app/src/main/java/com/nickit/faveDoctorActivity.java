package com.nickit;

import android.app.LocalActivityManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.DAO.NickITDataSource;
import com.Model.Doctor;
import com.controllers.FindDoctorController;

import java.util.ArrayList;
import java.util.List;


public class faveDoctorActivity extends Fragment {
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
        View V = inflater.inflate(R.layout.favedoctor, container, false);
        findController=new FindDoctorController(V,datasource,Integer.valueOf(user_id),this);

        List<Doctor> doctorList=new ArrayList<Doctor>();
        List<Integer> intArr=datasource.getUser().getAllFaveDocId(Integer.valueOf(user_id));
        int i=0;
        while(i<intArr.size())
        {
            doctorList.add(datasource.getDoctor().getDoctorDetail(intArr.get(i)));
            i++;
        }
        DoctorListView=(ListView)V.findViewById(R.id.favelistView);
        CustomItemAdapter adapter1 = new CustomItemAdapter(V.getContext(),
                R.layout.doctorlistview, doctorList,Integer.valueOf(user_id),this, datasource,V);
        adapter1.setFindController(findController);
        DoctorListView.setOnItemClickListener(findController.getDoctorListListener());
        DoctorListView.setAdapter(adapter1);

        return V;
    }
}


