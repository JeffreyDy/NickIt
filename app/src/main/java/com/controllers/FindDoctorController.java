package com.controllers;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.DAO.NickITDataSource;
import com.Model.Doctor;
import com.nickit.CustomItemAdapter;
import com.nickit.DoctorDetailActivity;
import com.nickit.faveDoctorActivity;
import com.nickit.R;
import com.nickit.findDoctorActivity;

import java.util.List;

/**
 * Created by Jeffrey on 4/5/15.
 */
public class FindDoctorController {
    private View main;
    private NickITDataSource nickData;
    private ListView DoctorListView;
    private int user_id;
    private findDoctorActivity findActivity=null;
    private faveDoctorActivity faveActivity=null;
    public FindDoctorController(View mainActivity, NickITDataSource NickData, int uID,findDoctorActivity find) {
        nickData = NickData;
        main = mainActivity;
        findActivity=find;
        user_id = uID;
    }
    public FindDoctorController(View mainActivity, NickITDataSource NickData, int uID,faveDoctorActivity fave) {
        nickData = NickData;
        main = mainActivity;
        faveActivity=fave;
        user_id = uID;
    }


    public AdapterView.OnItemSelectedListener getSpinnerOnItemSelected() {
        return new AdapterView.OnItemSelectedListener() {
            Spinner spin = (Spinner) main.findViewById(R.id.spinner);
            List<Doctor> doctorList;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int id1 = nickData.getSpecialization().getSpecID(spin.getSelectedItem().toString());
                if (id1 != 0) {
                    doctorList = nickData.getDoctor().getDoctorsbySpecID(id1);


                } else {
                    doctorList = nickData.getDoctor().getAllDoctors();


                }
                DoctorListView = (ListView) main.findViewById(R.id.listView);
                CustomItemAdapter adapter1 = new CustomItemAdapter(main.getContext(),
                        R.layout.doctorlistview, doctorList,user_id,findActivity,nickData,main);
                DoctorListView.setAdapter(adapter1);
                Toast.makeText(main.getContext(), spin.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

    }

    public View.OnClickListener getFaveOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parentRow = (View) v.getParent();
                ImageView faveBtn=(ImageView)parentRow.findViewById(R.id.faveBtn);
                ListView listView = (ListView) parentRow.getParent();
                final int position = listView.getPositionForView(parentRow);
                TextView text= (TextView) parentRow.findViewById(R.id.DrName);
                if(nickData.getUser().isFave(user_id,nickData.getDoctor().getUserID(text.getText().toString())))
                {
                    //delete
                    nickData.getUser().DeleteFave(user_id,nickData.getDoctor().getUserID(text.getText().toString()));
                   faveBtn.setImageResource(R.drawable.star178);
                }
                else {
                    nickData.getUser().insertFave(user_id, nickData.getDoctor().getUserID(text.getText().toString()));
                    faveBtn.setImageResource(R.drawable.goldstar);
                }
                // Toast.makeText(main, "Favorite "+text.getText(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    public AdapterView.OnItemClickListener getDoctorListListener() {
       return new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent;
               if(findActivity!=null) {
                   TextView text= (TextView) view.findViewById(R.id.DrName);
                   intent = new Intent(findActivity.getActivity(), DoctorDetailActivity.class);
                   intent.putExtra("doctor", text.getText());
                   intent.putExtra("user_id", user_id);
                   findActivity.startActivity(intent);
               }
               else {
                   TextView text= (TextView) view.findViewById(R.id.DrName);
                   intent = new Intent(faveActivity.getActivity(), DoctorDetailActivity.class);
                   intent.putExtra("doctor", text.getText());
                   intent.putExtra("user_id", user_id);
                   faveActivity.startActivity(intent);
               }



              // Toast.makeText(main.getContext(), "HELLO "+text.getText().toString(), Toast.LENGTH_SHORT).show();
           }
       };
    }

}
