package com.vehicle.mart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.vehicle.mart.adapters.BuyerVehicleAdapter;
import com.vehicle.mart.adapters.VehicleAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vehicle.mart.home.SellerActivity;
import com.vehicle.mart.model.Store;

import java.util.ArrayList;
import java.util.List;

public class See_all_screen extends AppCompatActivity {
        RecyclerView recyclerView;
RadioGroup rg_vehicle;
RadioButton rb_new_car,rb_used_car,rb_new_rickshaw,rb_used_rickshaw;
FirebaseAuth firebaseAuth;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference,refVehicle;
List<Vehicle> vehicleList;


@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_screen);
        
        rg_vehicle = findViewById(R.id.rg_vehicle);
        rb_new_car = findViewById(R.id.rb_new_car);
        rb_used_car = findViewById(R.id.rb_used_car);
        rb_new_rickshaw = findViewById(R.id.rb_new_rickshaw);
        rb_used_rickshaw = findViewById(R.id.rb_used_rickshaw);
        recyclerView = findViewById(R.id.recycler_view);
        vehicleList = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("vehicles").child(firebaseAuth.getUid());
        refVehicle = firebaseDatabase.getReference("vehicles");


        readVehicleFromFirebase();
//        rg_vehicle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(RadioGroup group, int checkedId) {
//                        if (checkedId == R.id.rb_new_car){
//
//                        }else if (checkedId == R.id.rb_used_car){
//
//                        }else if (checkedId == R.id.rb_new_rickshaw){
//
//                        }else if (checkedId == R.id.rb_used_rickshaw){
//
//                        }
//                        RadioButton radio = findViewById(checkedId);
//                        Toast.makeText(See_all_screen.this,radio.getText().toString() , Toast.LENGTH_SHORT).show();
//                }
//        });
   }

        public void readVehicleFromFirebase(){


                refVehicle.orderByChild("storeId").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                vehicleList.clear();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                                        Vehicle vehicle = dataSnapshot.getValue(Vehicle.class);

                                        vehicle.setVehicleId(dataSnapshot.getKey());
                                        boolean status = !vehicle.getStatus().equals("pending");

                    if (status){
                                        vehicleList.add(vehicle);
                    }
                                }

                            BuyerVehicleAdapter vehicleAdapter = new BuyerVehicleAdapter(See_all_screen.this,vehicleList);
                                recyclerView.setLayoutManager(new GridLayoutManager(See_all_screen.this,2));
                                recyclerView.setAdapter(vehicleAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                });
        }


}