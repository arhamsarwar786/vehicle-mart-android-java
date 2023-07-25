package com.vehicle.mart;

import android.icu.text.SimpleDateFormat;
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
import com.vehicle.mart.adapters.NotificationAdapter;
import com.vehicle.mart.adapters.StoreAdapter;

import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class BuyerNotification extends AppCompatActivity {

    List notifyList;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_notification);

        notifyList = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("payments");
        firebaseAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recycler_view);
//        recyclerView = findViewById(R.id.accept);

        readPendingStores();
    }

    public void readPendingStores() {
        databaseReference.orderByChild("buyerID").equalTo(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                vehicleList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    String date = dataSnapshot.child("date").getValue(String.class);
                    Boolean verify = dataSnapshot.child("verify").getValue(Boolean.class);

                    Boolean isNotifiy = separateDate(date);
                    if (isNotifiy) {
                        notifyList.add(isNotifiy);
                    }

//                    break;

                }

                NotificationAdapter storeAdapter = new NotificationAdapter(BuyerNotification.this, notifyList);
                recyclerView.setLayoutManager(new GridLayoutManager(BuyerNotification.this, 1));
                recyclerView.setAdapter(storeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static Boolean separateDate(String dateStr) {
        String[] parts = dateStr.split("-");

        if (parts.length >= 3) {
            int day = Integer.valueOf(parts[0].trim());
            int month = Integer.valueOf(parts[1].trim());
            int year = Integer.valueOf(parts[2].trim());

            Calendar calendar = Calendar.getInstance();
            int cyear = calendar.get(Calendar.YEAR);
            int cmonth = calendar.get(Calendar.MONTH) + 1; // Add 1 to month to display correctly
            int cday = calendar.get(Calendar.DAY_OF_MONTH);


            if (year == cyear && month >= cmonth && day > cday) {
                return true;
            }


        }
        return false;
    }

}