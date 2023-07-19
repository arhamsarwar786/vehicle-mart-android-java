package com.vehicle.mart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.vehicle.mart.model.Store;

import java.net.URLEncoder;

public class VehicleBuyerDetailsActivity extends AppCompatActivity {
    Vehicle vehicle;
    Store storeData;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_vehicle_details);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        vehicle = getIntent().getParcelableExtra("vehicleDetails");
        databaseReference = firebaseDatabase.getReference("stores").child(vehicle.getStoreId());
        DatabaseReference userRef = firebaseDatabase.getReference("users").child(vehicle.getStoreId());


        Log.wtf("arham",vehicle.getBrand());

        // Access the views from the XML layout
        TextView tvVehicleName = findViewById(R.id.tv_Name);
        TextView tvPrice = findViewById(R.id.tv_price);
        TextView tvLocation = findViewById(R.id.tv_location);
        TextView tvMilage = findViewById(R.id.tv_mileage);
        TextView tvEngine = findViewById(R.id.tv_engine);
        TextView tvTransmission = findViewById(R.id.tv_transmission);
        TextView tvFuel = findViewById(R.id.fuel);
        TextView tvSeats = findViewById(R.id.seats);
        TextView tvDescription = findViewById(R.id.tv_description);
        TextView tvAddress = findViewById(R.id.tv_address);
        ImageView tvAWhatsapp = findViewById(R.id.iv_whatsapp);

        tvAWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRef.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Retrieve the name from the dataSnapshot
                        String number = dataSnapshot.child("mobileNumber").getValue(String.class);
                        // Set the name to the nameText component

                        openWhatsApp(number);

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

//                progressDialog.cancel();
            }
        });

        Button btn = findViewById(R.id.buy_now);
//        TextView status = findViewById(R.id.status);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(VehicleBuyerDetailsActivity.this, BuyNow_Screen.class);
                intent.putExtra("vehicleDetail",vehicle);

                startActivity(intent);

//                progressDialog.cancel();
            }
        });

        ImageView image = findViewById(R.id.iv_rec_car_1);


        String imageResourceName = vehicle.getImage(); // Get the image resource name from the vehicle object
        String imageUrl = imageResourceName; // Replace with the image URL from Firebase
        float maxWidthPercentage = 1f; // Desired percentage of the screen width

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int maxWidth = (int) (screenWidth * maxWidthPercentage);
        Picasso.get().load(imageUrl).resize(maxWidth, 400)
                .centerCrop().into(image);
//        image.setMaxHeight(200);
//        TextView tvSeats = findViewById(R.id.seats);


        tvVehicleName.setText(vehicle.getBrand());
        tvPrice.setText(vehicle.getPrice());
        tvLocation.setText(vehicle.getRegisterCity());
        tvMilage.setText(vehicle.getMileage() + "KM/Litre");
        tvEngine.setText(vehicle.getEngineType());
        tvTransmission.setText(vehicle.getTransmission());
        tvFuel.setText(vehicle.getFuelType());
//        tvSeats.setText(vehicle.g);
        tvDescription.setText(vehicle.getDescription());
//        tvShopname.setText(vehicle.get);
        tvFuel.setText(vehicle.getFuelType());
//        status.setText(vehicle.getStatus());

//        tvShopname.setText(vehicle.st);


        // ... Set other values in a similar way ...

//        Toast.makeText(this, vehicle.toString(), Toast.LENGTH_LONG).show();

        readStoresFromFirebase();







    }

    public void readStoresFromFirebase(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Store store = snapshot.getValue(Store.class);
                storeData = store;

                TextView tvShopname = findViewById(R.id.tv_shopname);
                TextView tvAddress = findViewById(R.id.tv_address);

                tvShopname.setText(storeData.getName());
                tvAddress.setText(storeData.getAddress());

                Log.wtf("arham",store.toString() + "arham here for check");
//                if(store == null){
////                    Intent intent = new Intent(SellerActivity.this, StoreActivity.class);
//                    startActivity(intent);
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openWhatsApp(String number) {
        String phoneNumber = number ;// Replace with the phone number of the recipient
        String message = "Salam!\n Buyer from Vehicle Mart. \n I need information about this vehicle " + vehicle.getBrand(); // You can set a custom message to send (optional)

        try {
            Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + URLEncoder.encode(message, "UTF-8"));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (Exception e) {
            // WhatsApp is not installed on the device or other error
            Toast.makeText(this, "WhatsApp is not installed on your device", Toast.LENGTH_SHORT).show();
        }
    }

}