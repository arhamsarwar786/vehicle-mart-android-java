package com.vehicle.mart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.vehicle.mart.adapters.InstallmentAdapter;
import com.vehicle.mart.home.BuyerActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Installment_screen extends AppCompatActivity {

    RecyclerView rvInstallment;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    BottomNavigationView bottomNavigationView;
    List paymentList = new ArrayList<>(); // Create an ArrayList to store the payments


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_installment_screen);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        databaseReference = firebaseDatabase.getReference("payments");


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    startActivity(new Intent(Installment_screen.this, MainActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.profile) {
                    startActivity(new Intent(Installment_screen.this, ProfileActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.history) {
                    startActivity(new Intent(Installment_screen.this, History.class));
                    return true;
                }
                return false;
            }
        });


        String totalPrice = getIntent().getStringExtra("priceTotal");
        String storeID = getIntent().getStringExtra("storeID");
        String vehicleID = getIntent().getStringExtra("vehicleID");


        int month = getIntent().getIntExtra("month",-1);

        Double longValue = Double.parseDouble(totalPrice);

        longValue = longValue + 20000;

        Log.wtf("totalPrice parse", totalPrice + "data");

        rvInstallment = findViewById(R.id.rv_installment);
              TextView  monthIns = findViewById(R.id.month_inst);

        monthIns.setText(month + " - Months Installment");



        double installmentAmount = longValue / month;

        String installmentAmountString = String.valueOf(installmentAmount);
        totalPrice = String.valueOf(longValue);

        InstallmentAdapter adapter = new InstallmentAdapter(Installment_screen.this ,month, totalPrice,installmentAmountString,storeID,vehicleID,paymentList );
        rvInstallment.setLayoutManager(new GridLayoutManager(Installment_screen.this, 2));
        rvInstallment.setAdapter(adapter);





        TextView tokenPrice = findViewById(R.id.token_price);
        TextView totalPriceItem = findViewById(R.id.totalPrice);

        tokenPrice.setText(String.valueOf(2000) + " PKR");
        totalPriceItem.setText(longValue + " PKR" );





    }




}