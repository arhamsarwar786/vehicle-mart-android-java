package com.vehicle.mart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.material.textfield.TextInputEditText;

public class ResetPassword extends AppCompatActivity {
TextView tv_forgotpassword, tv_resetpassword;
TextInputEditText et_email,et_password;
Button btn_continue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
    }
}