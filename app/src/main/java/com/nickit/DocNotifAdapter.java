package com.nickit;

import java.util.List;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.DAO.NickITDataSource;
import com.Model.Doctor;
import com.Model.UserSchedule;
import com.controllers.FindDoctorController;

public class DocNotifAdapter extends ArrayAdapter<UserSchedule> {
    List<UserSchedule> users;
    private int user_id;
    private Context con;
    private DocNotifActivity main=null;
    private faveDoctorActivity main1=null;
    private NickITDataSource nick;
    private View view;
    private FindDoctorController findController;
    ListView  DoctorNotifView;
    private List<UserSchedule> list;

    public DocNotifAdapter(Context context, int resource, List<UserSchedule> doctors,int uID,DocNotifActivity mainActivity, NickITDataSource NickData) {
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
        denyBtn.setContentDescription(String.valueOf(schedule.getId()));
        confirmBtn.setContentDescription(String.valueOf(schedule.getId()));
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String id1 = (String) v.getContentDescription();
               // Log.e("TAGG",id);
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Are you sure you want to confirm?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                             //   finish();
                                //Log.e("TAGG",String.valueOf(id1));
                                nick.getSchedule().confirmAndUpdate(Integer.valueOf(id1));
                                DocNotifAdapter adapter1= (DocNotifAdapter) DoctorNotifView.getAdapter();
                                adapter1.clear();
                                adapter1.addAll(nick.getSchedule().getAllNotif(Integer.valueOf(DoctorActivity.getUser_id()), 0));
                               // list.remove(position);
                                adapter1.notifyDataSetChanged();
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
        });
        denyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id1 = (String) v.getContentDescription();
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Are you sure you want to cancel?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //   finish();
                                //Log.e("TAGG",String.valueOf(id1));
                                nick.getSchedule().cancel(Integer.valueOf(id1));
                                DocNotifAdapter adapter1= (DocNotifAdapter) DoctorNotifView.getAdapter();
                                adapter1.clear();
                                adapter1.addAll(nick.getSchedule().getAllNotif(Integer.valueOf(DoctorActivity.getUser_id()), 0));
                                // list.remove(position);
                                adapter1.notifyDataSetChanged();
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
        });
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
