package com.controllers;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.DAO.DataSource;
import com.DAO.NickITDataSource;
import com.nickit.DoctorActivity;
import com.nickit.MainActivity;
import com.nickit.R;
import com.nickit.UserActivity;

/**
 * Created by Jeffrey on 4/3/15.
 */
public class LoginController {
    private EditText username, password;
    private MainActivity main;
    private NickITDataSource nickData;

    public LoginController(MainActivity mainActivity, NickITDataSource NickData) {
        nickData = NickData;
        main = mainActivity;
        username = (EditText) mainActivity.findViewById(R.id.userNameField);
        password = (EditText) mainActivity.findViewById(R.id.passwordField);
    }

    public OnClickListener getLoginOnClickListener() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNameText = username.getText().toString();
                String passwordText = password.getText().toString();
                //userNameText=userNameText.toUpperCase();
                if(nickData.getUser().getUserID(userNameText,passwordText)!=0) {
                    username.setText("");
                    password.setText("");
                    username.requestFocus();
                    Intent myIntent = new Intent(main, UserActivity.class);
                    myIntent.putExtra("user",String.valueOf(nickData.getUser().getUserID(userNameText,passwordText)));
                    main.startActivity(myIntent);
                }
                else if(nickData.getDoctor().getUserID(userNameText,passwordText)!=0) {
                    username.setText("");
                    password.setText("");
                    Intent myIntent = new Intent(main, DoctorActivity.class);//DOCTOR
                    myIntent.putExtra("doctorID",String.valueOf(nickData.getDoctor().getUserID(userNameText,passwordText)));
                    main.startActivity(myIntent);
                }
                else
                    Toast.makeText(main,"Username does not exist \n OR Incorrect Password",Toast.LENGTH_SHORT).show();


            }
        };
    }
}
