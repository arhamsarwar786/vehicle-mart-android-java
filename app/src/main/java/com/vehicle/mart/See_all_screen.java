package com.vehicle.mart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
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
    private List filteredCars;

    String selectedType = "New Cars";


@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_see_all_screen);
    filteredCars = new ArrayList<>(); // Start with all cars
    selectedType = getIntent().getStringExtra("type");



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


    if(selectedType.equals("New Cars")){
        rb_new_car.setChecked(true);
    }else if(selectedType.equals("Used Cars")){
        rb_used_car.setChecked(true);
    }else if(selectedType.equals("New Riksha")){
        rb_new_rickshaw.setChecked(true);
    }else if(selectedType.equals("Used Riksha")){
        rb_used_rickshaw.setChecked(true);
    }

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



    rb_new_car.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                String selectedOption = rb_new_car.getText().toString();
                selectedType = selectedOption;
                Log.wtf("data options",selectedOption);
                readVehicleFromFirebase();
//                filterList(selectedOption);
            }
        }
    });
    rb_new_rickshaw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                String selectedOption = rb_new_rickshaw.getText().toString();
                selectedType = selectedOption;
                readVehicleFromFirebase();
//                filterList(selectedOption);
            }
        }
    });
    rb_used_car.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                String selectedOption = rb_used_car.getText().toString();
                selectedType = selectedOption;
                readVehicleFromFirebase();
//                filterList(selectedOption);
            }
        }
    });
    rb_used_rickshaw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                String selectedOption = rb_used_rickshaw.getText().toString();
                selectedType = selectedOption;
                readVehicleFromFirebase();
//                filterList(selectedOption);
            }
        }
    });



}



    public void filterList(String selectedOption) {
        filteredCars.clear();

        if (selectedOption.equals("Used Cars")) {
            // Filter for used cars
            for (Vehicle car : vehicleList) {
                Log.wtf("data option",car.getVehicleType());
                if (car.getVehicleType().contains("Used Cars")) {
                    filteredCars.add(car);
                }
            }
        } else if (selectedOption.equals("New Cars")) {
            // Filter for new cars
            for (Vehicle car : vehicleList) {
                Log.wtf("data option",car.getVehicleType());

                if (car.getVehicleType().equals("New Cars")) {
                    filteredCars.add(car);
                }
            }
        }

       else if (selectedOption.equals("Used Riksha")) {
            // Filter for used cars
            for (Vehicle car : vehicleList) {
                if (car.getVehicleType().contains("Used Riksha")) {
                    filteredCars.add(car);
                }
            }
        } else if (selectedOption.equals("New Riksha")) {
            // Filter for new cars
            for (Vehicle car : vehicleList) {
                if (car.getVehicleType().contains("New Riksha")) {
                    filteredCars.add(car);
                }
            }
        }
//
//        // Update your UI or do something with the filtered list
//        Toast.makeText(this, "Filtered list: " + filteredCars.toString(), Toast.LENGTH_SHORT).show();
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

                    if (status && selectedType.equals(vehicle.getVehicleType())){
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