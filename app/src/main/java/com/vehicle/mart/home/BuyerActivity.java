package com.vehicle.mart.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vehicle.mart.BuyerNotification;
import com.vehicle.mart.History;
import com.vehicle.mart.HomeAdapter;
import com.vehicle.mart.Installment_screen;
import com.vehicle.mart.MainActivity;
import com.vehicle.mart.ProfileActivity;
import com.vehicle.mart.R;
import com.vehicle.mart.See_all_screen;
import com.vehicle.mart.Vehicle;
import com.vehicle.mart.VehicleBuyerDetailsActivity;
import com.vehicle.mart.adapters.BuyerVehicleAdapter;
import com.vehicle.mart.login;

import java.util.ArrayList;
import java.util.List;

public class BuyerActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    HomeAdapter homeAdapter;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, refVehicle;
    ImageView iv_newcar, iv_usedcar, iv_newrickshaw, iv_usedrickshaw;
    List<Vehicle> vehicleList;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userRef = firebaseDatabase.getReference("users").child(firebaseAuth.getUid());
        refVehicle = firebaseDatabase.getReference("vehicles");


        // Retrieve the logout ImageButton
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    startActivity(new Intent(BuyerActivity.this, MainActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.profile) {
                    startActivity(new Intent(BuyerActivity.this, ProfileActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.history) {
                    startActivity(new Intent(BuyerActivity.this, History.class));
                    return true;
                }
                return false;
            }
        });
        ImageButton logoutButton = findViewById(R.id.iv_logout);
        ImageView notificationButton = findViewById(R.id.iv_notification);

        TextView nameText = findViewById(R.id.tv_username);
        readVehicleFromFirebase();

        userRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Retrieve the name from the dataSnapshot
                String name = dataSnapshot.child("fullName").getValue(String.class);

                // Set the name to the nameText component
                nameText.setText(name);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

// Set a click listener for the logout ImageButton
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the logout operation
                // Add your logout logic here
                firebaseAuth.signOut();
                // For example, navigate to a login screen
                Intent intent = new Intent(BuyerActivity.this, login.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the logout operation

                // For example, navigate to a login screen
                Intent intent = new Intent(BuyerActivity.this, BuyerNotification.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        iv_usedrickshaw = findViewById(R.id.iv_usedrickshaw);
        iv_newrickshaw = findViewById(R.id.iv_newrickshaw);
        iv_newcar = findViewById(R.id.iv_newcar);
        iv_usedcar = findViewById(R.id.iv_usedcar);

        iv_newcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BuyerActivity.this, See_all_screen.class);
                i.putExtra("type", "New Cars");
                startActivity(i);
            }
        });
        iv_usedcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BuyerActivity.this, See_all_screen.class);
                i.putExtra("type", "Used Cars");
                startActivity(i);
            }
        });
        iv_newrickshaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BuyerActivity.this, See_all_screen.class);
                i.putExtra("type", "New Riksha");
                startActivity(i);
            }
        });
        iv_usedrickshaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BuyerActivity.this, See_all_screen.class);
                i.putExtra("type", "Used Riksha");
                startActivity(i);
            }
        });

        vehicleList = new ArrayList<>();
        homeAdapter = new HomeAdapter(BuyerActivity.this, vehicleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(BuyerActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(homeAdapter);
    }

    public void readVehicleFromFirebase() {


        refVehicle.orderByChild("storeId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                vehicleList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Vehicle vehicle = dataSnapshot.getValue(Vehicle.class);

                    vehicle.setVehicleId(dataSnapshot.getKey());
                    boolean status = !vehicle.getStatus().equals("pending");

                    if (status) {
                        vehicleList.add(vehicle);
                        Log.wtf("arham", vehicleList + "final data ");
                    }
                }
                List<Vehicle> firstFiveVehicles = new ArrayList<>();

// Limit the number of items to 5 or the size of the original list if it's less than 5.
                int limit = Math.min(5, vehicleList.size());

// Add the first 5 items to the new list.
                for (int i = 0; i < limit; i++) {
                    firstFiveVehicles.add(vehicleList.get(i));
                }
                BuyerVehicleAdapter vehicleAdapter = new BuyerVehicleAdapter(BuyerActivity.this, firstFiveVehicles);
                recyclerView.setLayoutManager(new GridLayoutManager(BuyerActivity.this, 5));
                recyclerView.setAdapter(vehicleAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}