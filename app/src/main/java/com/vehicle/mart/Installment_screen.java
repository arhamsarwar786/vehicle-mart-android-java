package com.vehicle.mart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vehicle.mart.adapters.InstallmentAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Installment_screen extends AppCompatActivity {

    RecyclerView rvInstallment;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    List paymentList = new ArrayList<>(); // Create an ArrayList to store the payments


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_installment_screen);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        databaseReference = firebaseDatabase.getReference("payments");

        String totalPrice = getIntent().getStringExtra("priceTotal");
        String storeID = getIntent().getStringExtra("storeID");

        int month = getIntent().getIntExtra("month",-1);

        long longValue = Long.parseLong(totalPrice);

        longValue = longValue + 20000;

        Log.wtf("totalPrice parse", totalPrice + "data");

        rvInstallment = findViewById(R.id.rv_installment);
              TextView  monthIns = findViewById(R.id.month_inst);

        monthIns.setText(month + " - Months Installment");



        double installmentAmount = longValue / month;

        String installmentAmountString = String.valueOf(installmentAmount);
        totalPrice = String.valueOf(longValue);

        InstallmentAdapter adapter = new InstallmentAdapter(Installment_screen.this ,month, totalPrice,installmentAmountString,storeID,paymentList );
        rvInstallment.setLayoutManager(new GridLayoutManager(Installment_screen.this, 2));
        rvInstallment.setAdapter(adapter);





        TextView tokenPrice = findViewById(R.id.token_price);
        TextView totalPriceItem = findViewById(R.id.totalPrice);

        tokenPrice.setText(String.valueOf(2000) + " PKR");
        totalPriceItem.setText(longValue + " PKR" );





    }




}