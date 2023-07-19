package com.vehicle.mart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.vehicle.mart.home.AdminActivity;
import com.vehicle.mart.home.BuyerActivity;
import com.vehicle.mart.home.SellerActivity;
import com.vehicle.mart.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button btn_started;
    TextView tv_vehiclemart;
    ImageView iv_logo;
    private ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please wait!");
        progressDialog.setCancelable(false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");

        btn_started = findViewById(R.id.btn_started);
        tv_vehiclemart = findViewById(R.id.tv_vehiclemart);
        iv_logo = findViewById(R.id.iv_logo);

        btn_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                if (firebaseAuth.getUid() != null){
                    readUserFromFirebase();
                }else{
                    Intent i = new Intent(MainActivity.this, login.class);
                    startActivity(i);
                }
//                progressDialog.cancel();
            }
        });
    }



    public void readUserFromFirebase() {
        databaseReference.child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Log.d("arham","clicking here" + user.toString());

                if (user != null) {
                    if (user.getUserType().equals("Admin")) {
                        Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                        startActivity(intent);
                    } else if (user.getUserType().equals("Buyer")) {
                        Intent intent = new Intent(MainActivity.this, BuyerActivity.class);
                        startActivity(intent);
                    } else if (user.getUserType().equals("Seller")) {
                        Intent intent = new Intent(MainActivity.this, SellerActivity.class);
                        startActivity(intent);
                    }
                }
                Log.d("theS", "onDataChange: " + user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}