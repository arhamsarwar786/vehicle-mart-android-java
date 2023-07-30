package com.vehicle.mart.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vehicle.mart.History;
import com.vehicle.mart.MainActivity;
import com.vehicle.mart.PaymentApproval;
import com.vehicle.mart.ProfileActivity;
import com.vehicle.mart.R;
import com.vehicle.mart.Signup;
import com.vehicle.mart.StoreActivity;
import com.vehicle.mart.Store_detail;
import com.vehicle.mart.Vehicle;
import com.vehicle.mart.adapters.VehicleAdapter;
import com.vehicle.mart.login;
import com.vehicle.mart.model.Store;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SellerActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView iv_addbtn;
    ImageView payments_approval;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,refVehicle;
    Store storeData;
    BottomNavigationView bottomNavigationView;
List<Vehicle> vehicleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);

        recyclerView = findViewById(R.id.recycler_view);
        iv_addbtn = findViewById(R.id.iv_addbtn);
        payments_approval = findViewById(R.id.payments_approval);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    startActivity(new Intent(SellerActivity.this, MainActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.profile) {
                    startActivity(new Intent(SellerActivity.this, ProfileActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.history) {
                    startActivity(new Intent(SellerActivity.this, History.class));
                    return true;
                }
                return false;
            }
        });

        vehicleList = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userRef = firebaseDatabase.getReference("users").child(firebaseAuth.getUid());


        databaseReference = firebaseDatabase.getReference("stores").child(firebaseAuth.getUid());
        Log.d("arham",databaseReference.toString() + "store data herer");
        refVehicle = firebaseDatabase.getReference("vehicles");

        readStoresFromFirebase();
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

        // Retrieve the logout ImageButtonPaymentApproval
        ImageButton logoutButton = findViewById(R.id.iv_logout);

// Set a click listener for the logout ImageButton
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the logout operation
                // Add your logout logic here
                        firebaseAuth.signOut();
                // For example, navigate to a login screen
                Intent intent = new Intent(SellerActivity.this, login.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        iv_addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("arham","Clickde");
                if (storeData.isVerified()){
                    Intent i = new Intent(SellerActivity.this, Store_detail.class);
                    startActivity(i);
                }else{
                    showPopup("Please, wait for store verification! Thanks :)");

                }

            }
        });

        payments_approval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Intent i = new Intent(SellerActivity.this, PaymentApproval.class);
                    startActivity(i);


            }
        });
    }

    public void readStoresFromFirebase(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Store store = snapshot.getValue(Store.class);
                storeData = store;
                Log.wtf("arham",store + "arham here for check");
//                if(store == null){
////                    Intent intent = new Intent(SellerActivity.this, StoreActivity.class);
//                    startActivity(intent);
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void readVehicleFromFirebase(){
        refVehicle.orderByChild("storeId").equalTo(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vehicleList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Vehicle vehicle = dataSnapshot.getValue(Vehicle.class);
                    Log.wtf("arham test","a" + vehicle);
                    vehicle.setVehicleId(dataSnapshot.getKey());
                    Log.d("theS", "readVehicleFromFirebase: "+vehicle.getStatus());
                    boolean status = vehicle.getStatus() == "pending" ? false : true;
                    Log.wtf("arham", String.valueOf(status));

//                    if (status){
                        vehicleList.add(vehicle);
//                    }
                }

                VehicleAdapter vehicleAdapter = new VehicleAdapter(SellerActivity.this,vehicleList);
                recyclerView.setLayoutManager(new GridLayoutManager(SellerActivity.this,2));
                recyclerView.setAdapter(vehicleAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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