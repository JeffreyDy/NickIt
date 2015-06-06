package com.nickit;

import java.util.List;
import android.app.Service;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.DAO.NickITDataSource;
import com.Model.Doctor;
import com.controllers.FindDoctorController;

public class CustomItemAdapter extends ArrayAdapter<Doctor> {
    List<Doctor> users;
    private int user_id;
    private Context con;
    private findDoctorActivity main=null;
    private faveDoctorActivity main1=null;
    private NickITDataSource nick;
    private View view;
     private FindDoctorController findController;
  	public CustomItemAdapter(Context context, int resource, List<Doctor> doctors,int uID,findDoctorActivity mainActivity, NickITDataSource NickData,View V) {
        super(context, resource, doctors);
        user_id=uID;
        con=context;
		users = doctors;
        main=mainActivity;
        nick=NickData;
        view=V;
       	// TODO Auto-generated constructor stub
	}
    public CustomItemAdapter(Context context, int resource, List<Doctor> doctors,int uID,faveDoctorActivity mainActivity, NickITDataSource NickData,View V) {
        super(context, resource, doctors);
        user_id=uID;
        con=context;
        users = doctors;
        main1=mainActivity;
        nick=NickData;
        view=V;
        // TODO Auto-generated constructor stub
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		if(convertView == null)
		{
			LayoutInflater infl = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
			convertView = infl.inflate(R.layout.doctorlistview, parent, false);
		}
		
		ImageView doctor_image = (ImageView) convertView.findViewById((R.id.doctorImage));
	    ImageView faveBtn = (ImageView) convertView.findViewById((R.id.faveBtn));
		TextView doctor_name = (TextView) convertView.findViewById((R.id.DrName));
		TextView doctor_spec = (TextView) convertView.findViewById((R.id.specialization));
        Resources res = con.getResources();
        String[] Specialization = res.getStringArray(R.array.doctor_specializations);
		Doctor temp = users.get(position);
        if(main!=null) {
            FindDoctorController findController = new FindDoctorController(view, nick, user_id, main);
            faveBtn.setOnClickListener(findController.getFaveOnClickListener());
        }
//		professor_image.setImageResource(temp.get());
            doctor_name.setText(temp.getName());
            doctor_spec.setText(Specialization[temp.getSpecID()]);
            //Log.e("DOCTOR ID", String.valueOf(temp.getId()));
            if (nick.getUser().isFave(user_id, temp.getId())) {
                faveBtn.setImageResource(R.drawable.goldstar);
            } else {
                faveBtn.setImageResource(R.drawable.star178);

            }

		return convertView;
	}

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public FindDoctorController getFindController() {
        return findController;
    }

    public void setFindController(FindDoctorController findController) {
        this.findController = findController;
    }
	

}
