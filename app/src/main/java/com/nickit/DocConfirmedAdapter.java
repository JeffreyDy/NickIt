package com.nickit;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.DAO.NickITDataSource;
import com.Model.UserSchedule;
import com.controllers.FindDoctorController;

import java.util.List;

public class DocConfirmedAdapter extends ArrayAdapter<UserSchedule> {
    List<UserSchedule> users;
    private int user_id;
    private Context con;
    private DoctorConfirmed main=null;
    private faveDoctorActivity main1=null;
    private NickITDataSource nick;
    private View view;
    private FindDoctorController findController;
    ListView  DoctorNotifView;
    private List<UserSchedule> list;

    public DocConfirmedAdapter(Context context, int resource, List<UserSchedule> doctors, int uID, DoctorConfirmed mainActivity, NickITDataSource NickData) {
        super(context, resource, doctors);
        user_id=uID;
        con=context;
        users = doctors;
        main=mainActivity;
        nick=NickData;
        // TODO Auto-generated constructor stub
        DoctorNotifView = null;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        if(convertView == null)
        {
            LayoutInflater infl = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            convertView = infl.inflate(R.layout.doctornotifrow, parent, false);
        }

        TextView userName= (TextView) convertView.findViewById(R.id.userName);
        TextView userDate= (TextView) convertView.findViewById(R.id.userDate);


        UserSchedule schedule=users.get(position);
        Resources res = con.getResources();
        String[] time = res.getStringArray(R.array.timeSchedule);
        userName.setText(nick.getUser().getUserName(schedule.getUser_ID()));
        userDate.setText(schedule.getMonth()+"/"+schedule.getDate()+"/"+schedule.getYear()+" \n@ "+time[schedule.getTimeID()-1]);
        ImageButton confirmBtn=(ImageButton) convertView.findViewById(R.id.confirmBtn);
        ImageButton denyBtn=(ImageButton)convertView.findViewById(R.id.denyBtn);
        confirmBtn.setVisibility(View.GONE);
        denyBtn.setVisibility(View.GONE);
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




    public void setDocView(ListView docView) {
        this.DoctorNotifView = docView;
    }

    public void setList(List<UserSchedule> list) {
        this.list = list;
    }
}
