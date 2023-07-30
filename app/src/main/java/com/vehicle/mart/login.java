package com.vehicle.mart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.vehicle.mart.home.AdminActivity;
import com.vehicle.mart.home.BuyerActivity;
import com.vehicle.mart.home.SellerActivity;
import com.vehicle.mart.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    private ProgressDialog progressDialog;

    ImageView ivlogo;
TextView tvappname, tvlogin, tvforgot, tvnoaccount, tvsignup;
Button btn;
TextInputEditText et_password, et_email;
FirebaseAuth firebaseAuth;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
String TAG= "theS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(login.this);
        progressDialog.setMessage("Logging in...");
        progressDialog.setCancelable(false);
        Log.d(TAG, "onCreate: login");

        ivlogo = findViewById(R.id.iv_logo);
        tvappname = findViewById(R.id.tvappname);
        tvlogin = findViewById(R.id.tvlogin);
        tvforgot = findViewById(R.id.tvforgot);
        tvnoaccount = findViewById(R.id.tvnoaccount);
        tvsignup = findViewById(R.id.tvsignup);
        btn = findViewById(R.id.btn);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");

        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this, Signup.class);
                startActivity(i);
            }
        });
        tvforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(login.this,ForgotPassword.class);
                startActivity(j);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
//                progressDialog.show();
                boolean check = validateinfo(email,password);
                Log.d(TAG, "onClick: check: " + check);
                if(check){
                    progressDialog.show();
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("theS", "onComplete: " + firebaseAuth.getUid());
                                readUserFromFirebase();
                            } else {
                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(login.this, errorMessage, Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                        }
                    });
                }
//                progressDialog.dismiss();

            }
        });

    }

    private boolean validateinfo(String email, String password) {
        if(email.length()==0){
            et_email.requestFocus();
            et_email.setError("FIELD CANNOT BE EMPTY");
            return false;
        }
        else if(!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            et_email.requestFocus();
            et_email.setError("ENTER VALID EMAIL");
            return false;
        }
        else if(password.length()<=7){
            et_password.requestFocus();
            et_password.setError("MINIMUM 8 CHARACTER REQUIRED");
            return false;
        }
        else{
            return true;
        }
    }

    public void readUserFromFirebase(){
        databaseReference.child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Log.d(TAG, "onDataChange: "+user);
                if (user!=null){
                    if (user.getUserType().equals("Admin")){
                        Intent intent = new Intent(login.this, AdminActivity.class);
                        startActivity(intent);
                    }else if (user.getUserType().equals("Buyer")){
                        Intent intent = new Intent(login.this, BuyerActivity.class);
                        startActivity(intent);
                    }else if (user.getUserType().equals("Seller")){
                        Intent intent = new Intent(login.this, SellerActivity.class);
                        startActivity(intent);
                    }
                }
                Log.d("theS", "onDataChange: "+user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}