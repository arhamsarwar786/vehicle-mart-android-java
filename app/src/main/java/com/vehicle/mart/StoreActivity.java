package com.vehicle.mart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.vehicle.mart.home.SellerActivity;
import com.vehicle.mart.model.Store;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StoreActivity extends AppCompatActivity {
    TextInputEditText et_storename, et_storeaddress, et_ntn, et_termsandcondition;
    Button btn_save;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        progressDialog = new ProgressDialog(StoreActivity.this);
        progressDialog.setMessage("Please, wait for Loading!");
        progressDialog.setCancelable(false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        et_storename = findViewById(R.id.et_storename);
        et_storeaddress = findViewById(R.id.et_storeaddress);
        et_ntn = findViewById(R.id.et_ntn);
        et_termsandcondition = findViewById(R.id.et_termsandcondition);
        btn_save = findViewById(R.id.btn_save);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("stores").child(firebaseAuth.getUid());

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = et_storename.getText().toString();
                String address = et_storeaddress.getText().toString();
                String ntn = et_ntn.getText().toString();
                String termsAndCondition = et_termsandcondition.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(StoreActivity.this, "Please enter name.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(address)) {
                    Toast.makeText(StoreActivity.this, "Please enter address.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(ntn)) {
                    Toast.makeText(StoreActivity.this, "Please enter ntn.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(termsAndCondition)) {
                    Toast.makeText(StoreActivity.this, "Please enter StoreActivity.", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.show();
                    Store store = new Store(firebaseAuth.getUid(), name, address, ntn, termsAndCondition, false);
                    databaseReference.setValue(store);
                    Intent i = new Intent(StoreActivity.this,SellerActivity.class);
                    startActivity(i);
                }

            }

        });
    }
}
