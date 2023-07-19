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
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.vehicle.mart.AdminStoreVerification;
import com.vehicle.mart.R;
import com.vehicle.mart.Signup;
import com.vehicle.mart.StoreActivity;
import com.vehicle.mart.Store_detail;
import com.vehicle.mart.Vehicle;
import com.vehicle.mart.adapters.VehicleAdapter;
import com.vehicle.mart.adapters.VehicleAdminAdapter;
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

public class AdminActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,refVehicle;
    Store storeData;
    List<Vehicle> vehicleList;
    ImageView iv_addbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        recyclerView = findViewById(R.id.recycler_view);

        vehicleList = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userRef = firebaseDatabase.getReference("users").child(firebaseAuth.getUid());


        databaseReference = firebaseDatabase.getReference("stores").child(firebaseAuth.getUid());
        Log.d("arham",databaseReference.toString() + "store data herer");
        refVehicle = firebaseDatabase.getReference("vehicles");

//        readStoresFromFirebase();
        TextView nameText = findViewById(R.id.tv_username);
        iv_addbtn = findViewById(R.id.iv_addbtn);
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

        // Retrieve the logout ImageButton
        ImageButton logoutButton = findViewById(R.id.iv_logout);

// Set a click listener for the logout ImageButton
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the logout operation
                // Add your logout logic here
                firebaseAuth.signOut();
                // For example, navigate to a login screen
                Intent intent = new Intent(AdminActivity.this, login.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity
            }
        });

        iv_addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.wtf("arham debug","Clickde arham");
//
                    Intent i = new Intent(AdminActivity.this, AdminStoreVerification.class);
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
        refVehicle.orderByChild("storeId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vehicleList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Vehicle vehicle = dataSnapshot.getValue(Vehicle.class);
                    Log.wtf("arham test","a" + vehicle);
                    vehicle.setVehicleId(dataSnapshot.getKey());
                    Log.d("theS", "readVehicleFromFirebase: "+vehicle.getStatus());
                    boolean status = vehicle.getStatus().equals("pending");
                    Log.wtf("arham", String.valueOf(status));

                    if (status){
                    vehicleList.add(vehicle);
                    }
                }

                VehicleAdminAdapter vehicleAdapter = new VehicleAdminAdapter(AdminActivity.this,vehicleList);
                recyclerView.setLayoutManager(new GridLayoutManager(AdminActivity.this,2));
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
