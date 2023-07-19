package com.vehicle.mart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vehicle.mart.adapters.InstallmentAdapter;
import com.vehicle.mart.adapters.StoreAdapter;
import com.vehicle.mart.adapters.VehicleAdapter;
import com.vehicle.mart.home.SellerActivity;

import java.util.ArrayList;
import java.util.List;

public class AdminStoreVerification extends AppCompatActivity {

    List<StoreModel> storeList;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_approve);

        storeList = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("stores");

        recyclerView = findViewById(R.id.recycler_view);
//        recyclerView = findViewById(R.id.accept);

        readPendingStores();
    }
    public void readPendingStores(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                vehicleList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Log.wtf("arham data here", String.valueOf(storeList.size()));

                    StoreModel store =  dataSnapshot.getValue(StoreModel.class);
                    if (!store.isVerified()){
                    storeList.add(store);
                    }
                }

                StoreAdapter storeAdapter = new StoreAdapter(AdminStoreVerification.this,storeList);
                recyclerView.setLayoutManager(new GridLayoutManager(AdminStoreVerification.this,1));
                recyclerView.setAdapter(storeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}