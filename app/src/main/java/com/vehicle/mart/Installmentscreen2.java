package com.vehicle.mart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vehicle.mart.adapters.InstallmentAdapter;
import com.vehicle.mart.home.BuyerActivity;

public class Installmentscreen2 extends AppCompatActivity {

    RecyclerView rvInstallment;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installmentscreen2);

        rvInstallment = findViewById(R.id.rv_installment);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    startActivity(new Intent(Installmentscreen2.this, MainActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.profile) {
                    startActivity(new Intent(Installmentscreen2.this, ProfileActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.history) {
                    startActivity(new Intent(Installmentscreen2.this, History.class));
                    return true;
                }
                return false;
            }
        });
//        InstallmentAdapter adapter = new InstallmentAdapter(Installmentscreen2.this ,6);
//        rvInstallment.setLayoutManager(new GridLayoutManager(Installmentscreen2.this, 2));
//        rvInstallment.setAdapter(adapter);
    }
}