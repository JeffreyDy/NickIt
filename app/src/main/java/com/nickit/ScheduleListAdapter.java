package com.nickit;

import java.util.List;
import android.app.Service;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
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
import com.Model.UserSchedule;
import com.controllers.FindDoctorController;

public class ScheduleListAdapter extends ArrayAdapter<String> {
    List<UserSchedule> schedules;
    private int user_id;
    private Context con;
    private NickITDataSource nick;
    List<String>scheduleName;
    public ScheduleListAdapter(Context context, int resource, List<UserSchedule> schedule,int uID, NickITDataSource NickData,List<String> scheduleNames) {
        super(context, resource, scheduleNames);
        user_id=uID;
        con=context;
        schedules = schedule;
        nick=NickData;
        scheduleName=scheduleNames;
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        if(convertView == null)
        {
            LayoutInflater infl = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            convertView = infl.inflate(R.layout.schedulelistview, parent, false);
        }
        TextView schedText= (TextView) convertView.findViewById(R.id.timeTextView);
        schedText.setText(scheduleName.get(position));
        for(int i=0; i<schedules.size();i++)
        {
            if(schedules.get(i).getTimeID()==position+1)
            {

                if(schedules.get(i).getUser_ID()==user_id&&schedules.get(i).getIsConfirmed()==0)
                { schedText.setTag("Blue");
                    schedText.setBackgroundColor(Color.BLUE);

                }
                else if(schedules.get(i).getIsConfirmed()==0&&schedules.get(i).getUser_ID()==user_id)
                {
                    schedText.setTag("Magenta");
                    schedText.setBackgroundColor(Color.MAGENTA);
                }
                else if(schedules.get(i).getIsConfirmed()==0)
                {
                    schedText.setTag("Gray");
                    schedText.setBackgroundColor(Color.GRAY);
                }
                else if(schedules.get(i).getUser_ID()==user_id&&schedules.get(i).getIsConfirmed()==1)
                { schedText.setTag("Yellow");
                    schedText.setBackgroundColor(Color.YELLOW);

                }

                else if(schedules.get(i).getIsConfirmed()==1)
                {
                    schedText.setTag("Red");
                    schedText.setBackgroundColor(Color.RED);
                }

            }
        }

        return convertView;
    }



}
