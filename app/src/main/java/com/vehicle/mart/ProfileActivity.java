package com.vehicle.mart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vehicle.mart.home.BuyerActivity;
import com.vehicle.mart.model.User;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users").child(firebaseAuth.getUid());

        TextView name = findViewById(R.id.tv_name);
        TextView cnic = findViewById(R.id.tv_cnic_num);
        TextView contactNumber = findViewById(R.id.tv_contact_num);
        TextView email = findViewById(R.id.tv_email_id);
        TextView type = findViewById(R.id.tv_user_catogery);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.profile) {
                    startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
                } else if (item.getItemId() == R.id.history) {
                    startActivity(new Intent(ProfileActivity.this, History.class));
                    return true;
                }
                return false;
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                name.setText(user.getFullName());
                cnic.setText(user.getCNIC());
                email.setText(user.getEmail());
                contactNumber.setText(user.getMobileNumber());
                type.setText(user.getUserType());
                Log.d("theS", "onDataChange: "+user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("theS", "onCancelled: "+error);

            }
        });
    }
}