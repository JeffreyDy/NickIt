package com.nickit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.DAO.NickITDataSource;
import com.Model.UserSchedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class BookNow extends ActionBarActivity {
    DatePicker datePicker;
    private ListView ScheduleListView;
    private String doctor_name;
    private NickITDataSource datasource;
    static int pos=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_book_now);
        datasource=new NickITDataSource(this);
        Intent i=getIntent();
        doctor_name=i.getStringExtra("doctor_name");
         datePicker= (DatePicker) findViewById(R.id.datePicker);
        Calendar c = Calendar.getInstance();
        c.set(2015, 11, 31);
        datePicker.setMaxDate(c.getTimeInMillis());
        datePicker.setMinDate(System.currentTimeMillis() - 1000);
        ScheduleListView=(ListView)findViewById(R.id.timeList);
        ScheduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if(pos!=-1) {
                    LinearLayout textTODeselect = (LinearLayout) parent.getChildAt(pos);
                    TextView textToDes = (TextView) textTODeselect.findViewById(R.id.timeTextView);
                    textToDes.setSelected(false);
                    ScheduleListView.setItemChecked(pos,false);

                }

               final TextView schedText= (TextView) view.findViewById(R.id.timeTextView);
                String tag= (String) schedText.getTag();
                if(tag.equals("green")) {
                    schedText.setSelected(true);
                    pos=position;
                    ScheduleListView.setItemChecked(position,true);

                }
                else if(tag.equals("Gray"))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Other people have scheduled on this time already, but the Doctor hasn't confirmed yet. \nWould you still want to continue scheduling it?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    schedText.setSelected(true);
                                    pos=position;
                                    ScheduleListView.setItemChecked(position,true);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    schedText.setSelected(false);
                                    pos=position;
                                    ScheduleListView.setItemChecked(position,false);
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else if(tag.equals("Blue"))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("You've scheduled on this time already!\n(Pending)")
                            .setCancelable(false)
                            .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    schedText.setSelected(false);
                                    pos=position;
                                    ScheduleListView.setItemChecked(position,false);
                                }
                            })
                            ;
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else if(tag.equals("Yellow"))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("You've been scheduled on this time!\n(Approved)")
                            .setCancelable(false)
                            .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    schedText.setSelected(false);
                                    pos=position;
                                    ScheduleListView.setItemChecked(position,false);
                                }
                            })
                    ;
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else if(tag.equals("Magenta"))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("You've been denied on this time!")
                            .setCancelable(false)
                            .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    schedText.setSelected(false);
                                    pos=position;
                                    ScheduleListView.setItemChecked(position,false);
                                }
                            })
                    ;
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Sorry someone is already scheduled here!")
                            .setCancelable(false)
                            .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    schedText.setSelected(false);
                                    pos=position;
                                    ScheduleListView.setItemChecked(position,false);
                                }
                            })
                    ;
                    AlertDialog alert = builder.create();
                    alert.show();

                }

            }
        });
        datePicker.getCalendarView().setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                List<UserSchedule> scheduleList=datasource.getSchedule().getSched(datePicker.getYear(),datePicker.getMonth()+1,datePicker.getDayOfMonth(),datasource.getDoctor().getUserID(doctor_name),Integer.valueOf(UserActivity.getUser_id()));
                Resources res = getResources();
                String[] Schedules = res.getStringArray(R.array.timeSchedule);
                List<String> scheduleNames = new ArrayList<>();
                for(int j=0;j<Schedules.length;j++)
                    scheduleNames.add(Schedules[j]);
                ScheduleListAdapter adapter1 = new ScheduleListAdapter(getBaseContext(),R.layout.schedulelistview,scheduleList,Integer.valueOf(UserActivity.getUser_id()),datasource,scheduleNames);
                ScheduleListView.setAdapter(adapter1);
                setListViewHeightBasedOnChildren(ScheduleListView);
            }
        });
        List<UserSchedule> scheduleList=datasource.getSchedule().getSched(datePicker.getYear(),datePicker.getMonth()+1,datePicker.getDayOfMonth(),datasource.getDoctor().getUserID(doctor_name),Integer.valueOf(UserActivity.getUser_id()));
        Resources res = getResources();
        String[] Schedules = res.getStringArray(R.array.timeSchedule);
        List<String> scheduleNames = new ArrayList<>();
        for(int j=0;j<Schedules.length;j++)
            scheduleNames.add(Schedules[j]);
        ScheduleListAdapter adapter1 = new ScheduleListAdapter(getBaseContext(),R.layout.schedulelistview,scheduleList,Integer.valueOf(UserActivity.getUser_id()),datasource,scheduleNames);
        ScheduleListView.setAdapter(adapter1);
        setListViewHeightBasedOnChildren(ScheduleListView);
        Button ConfirmBtn= (Button) findViewById(R.id.button2);
        ConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ScheduleListView.getCheckedItemCount()==0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("Please select a time")
                            .setCancelable(false)
                            .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else{
                    datasource.getSchedule().insertSched(datePicker.getYear(),datePicker.getMonth()+1,datePicker.getDayOfMonth(),Integer.valueOf(UserActivity.getUser_id()),datasource.getDoctor().getUserID(doctor_name),ScheduleListView.getCheckedItemPosition()+1);
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("Request now being processed!")
                            .setCancelable(false)
                            .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();

                                    finish();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
                  //  Log.e("NOT NULL", String.valueOf(ScheduleListView.getCheckedItemPosition()));
            }
        });

    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, AbsListView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_now, menu);
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
