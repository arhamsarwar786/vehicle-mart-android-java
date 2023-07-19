package com.vehicle.mart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.vehicle.mart.adapters.InstallmentAdapter;

public class Installmentscreen2 extends AppCompatActivity {

    RecyclerView rvInstallment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installmentscreen2);

        rvInstallment = findViewById(R.id.rv_installment);

//        InstallmentAdapter adapter = new InstallmentAdapter(Installmentscreen2.this ,6);
//        rvInstallment.setLayoutManager(new GridLayoutManager(Installmentscreen2.this, 2));
//        rvInstallment.setAdapter(adapter);
    }
}