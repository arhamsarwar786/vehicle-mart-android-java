package com.vehicle.mart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.graphics.Color;
import android.view.View;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ForgotPassword extends AppCompatActivity {
    TextView tv_forgotpassword, tv_resetpassword;
    TextInputEditText et_email;
    Button btn_continue;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        et_email = findViewById(R.id.et_email);
        btn_continue = findViewById(R.id.btn_continue);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("password");

        Button forgotPasswordButton = findViewById(R.id.btn_continue);
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText emailEditText = findViewById(R.id.et_email); // Replace with your email EditText's ID
                String email = emailEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    // Email field is empty
                    Log.wtf("hello","Email is empty");
                    showPopup("Please, Enter Email address!");
                } else if (!isValidEmail(email)) {
                    // Invalid email format
                    showPopup("Please, Enter Valid Email address!");
                } else {
                    Log.wtf("hello","arham");
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Password reset email sent successfully
                                        showPopup("Password reset email sent.");
                                        finish();

                                    } else {
                                        // Failed to send password reset email
                                        showPopup("Failed to send password reset email.");
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.wtf("hello",e.getMessage());
                                    // Handle failure to send password reset email
                                    showPopup("Failed to send password reset email: " + e.getMessage());

                                }
                            });
                }
            }
        });





    }
    private boolean isValidEmail(String email) {
        // Regex pattern for email validation
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        // Check if the email matches the pattern
        return email.matches(emailPattern);
    }



    private void showPopup(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(text)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle cancel button click
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}