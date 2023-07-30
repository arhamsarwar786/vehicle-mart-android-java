package com.vehicle.mart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;
import com.vehicle.mart.home.BuyerActivity;

public class BuyNow_Screen extends AppCompatActivity {

    Vehicle vehicle;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_now_screen);

        vehicle = getIntent().getParcelableExtra("vehicleDetail");


        TextView carName = findViewById(R.id.tv_carname);
        TextView tvCC = findViewById(R.id.tv_cc);
        TextView type = findViewById(R.id.tv_type);
        TextView liter = findViewById(R.id.tv_litre);
        TextView price = findViewById(R.id.tv_rupees);
        TextView tokenPrice = findViewById(R.id.tv_tokenrupees);
        TextView totalPrice = findViewById(R.id.tv_remainingrupees);
        ImageView image = findViewById(R.id.iv_car);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    startActivity(new Intent(BuyNow_Screen.this, MainActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.profile) {
                    startActivity(new Intent(BuyNow_Screen.this, ProfileActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.history) {
                    startActivity(new Intent(BuyNow_Screen.this, History.class));
                    return true;
                }
                return false;
            }
        });

        carName.setText(vehicle.getBrand());
        tvCC.setText(vehicle.getMileage());
        type.setText(vehicle.getType());
        liter.setText(vehicle.getFuelType());

        price.setText(vehicle.getPrice() + " PKR");
        tokenPrice.setText(20000 + " PKR");
        long longValue = Long.parseLong(vehicle.getPrice());
        longValue =  longValue + 20000;
        totalPrice.setText( longValue + " PKR");


        String imageResourceName = vehicle.getImage(); // Get the image resource name from the vehicle object
        String imageUrl = imageResourceName; // Replace with the image URL from Firebase
        float maxWidthPercentage = 1f; // Desired percentage of the screen width

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int maxWidth = (int) (screenWidth * maxWidthPercentage);
        Picasso.get().load(imageUrl).resize(maxWidth, 400)
                .centerCrop().into(image);




        ImageView ivTwomonth = findViewById(R.id.iv_twomonth);
        ImageView ivTwelvemonth = findViewById(R.id.iv_twelvemonth);
        ImageView ivSixmonth = findViewById(R.id.iv_sixmonth);
        ImageView ivInstantpayment = findViewById(R.id.iv_instantpayment);


        ivTwomonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = vehicle.getPrice();
                Log.wtf("before click",price);
                Intent intent = new Intent(BuyNow_Screen.this, Installment_screen.class);
                intent.putExtra("priceTotal", price);
                intent.putExtra("month", 2);
                intent.putExtra("storeID",vehicle.getStoreId());
                intent.putExtra("vehicleID",vehicle.getVehicleId());
                startActivity(intent);
            }
        });

        ivTwelvemonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = vehicle.getPrice();
                Log.wtf("before click",price);
                Intent intent = new Intent(BuyNow_Screen.this, Installment_screen.class);
                intent.putExtra("priceTotal", price);
                intent.putExtra("month", 12);
                intent.putExtra("storeID",vehicle.getStoreId());
                intent.putExtra("vehicleID",vehicle.getVehicleId());
                startActivity(intent);
            }
        });

        ivSixmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = vehicle.getPrice();
                Log.wtf("before click",price);
                Intent intent = new Intent(BuyNow_Screen.this, Installment_screen.class);
                intent.putExtra("priceTotal", price);
                intent.putExtra("month", 6);
                intent.putExtra("storeID",vehicle.getStoreId());
                intent.putExtra("vehicleID",vehicle.getVehicleId());
                startActivity(intent);
            }
        });

        ivInstantpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = vehicle.getPrice();
                Log.wtf("before click",price);
                Intent intent = new Intent(BuyNow_Screen.this, Installment_screen.class);
                intent.putExtra("priceTotal", price);
                intent.putExtra("month", 1);
                intent.putExtra("storeID",vehicle.getStoreId());
                intent.putExtra("vehicleID",vehicle.getVehicleId());
                startActivity(intent);
            }
        });




    }
}