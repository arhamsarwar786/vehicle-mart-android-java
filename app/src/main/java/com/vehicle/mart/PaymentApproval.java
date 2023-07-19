package com.vehicle.mart;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vehicle.mart.adapters.PaymentAdapter;
import com.vehicle.mart.adapters.StoreAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentApproval extends AppCompatActivity {

    List paymentsList;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_approve);
         firebaseAuth = FirebaseAuth.getInstance();

        paymentsList = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("payments");

        recyclerView = findViewById(R.id.recycler_view);
//        recyclerView = findViewById(R.id.accept);

        readPayments();
    }
    public void readPayments(){
        databaseReference.orderByChild("storeID").equalTo(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                vehicleList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    String storeId = dataSnapshot.child("storeID").getValue(String.class);
                    String date1 = dataSnapshot.child("date").getValue(String.class);
                    String total = dataSnapshot.child("total").getValue(String.class);
                    String amount = dataSnapshot.child("amount").getValue(String.class);
                    String url = dataSnapshot.child("url").getValue(String.class);
                    Integer type = dataSnapshot.child("type").getValue(Integer.class);
                    Boolean verify = dataSnapshot.child("verify").getValue(Boolean.class);
                    String paymentID = dataSnapshot.getKey();
                    Map<String, Object> dataMap = new HashMap<>();
                    dataMap.put("url", url);
                    dataMap.put("storeID", storeId);
                    dataMap.put("date", date1);
                    dataMap.put("paymentID", paymentID);
                    dataMap.put("type", type);
                    dataMap.put("total", total);
                    dataMap.put("amount", amount);
                    if (!verify){
                        paymentsList.add(dataMap);
                    }
                }

                PaymentAdapter paymentAdapter = new PaymentAdapter(PaymentApproval.this,paymentsList);
                recyclerView.setLayoutManager(new GridLayoutManager(PaymentApproval.this,1));
                recyclerView.setAdapter(paymentAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}