package com.vehicle.mart;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.DisplayMetrics;
import android.util.Log;
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

public class VehicleDetailsActivity extends AppCompatActivity {
    Vehicle vehicle;
    Store storeData;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();



        vehicle = getIntent().getParcelableExtra("vehicle");

        databaseReference = firebaseDatabase.getReference("stores").child(        vehicle.getStoreId());

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
        TextView status = findViewById(R.id.status);

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
        status.setText(vehicle.getStatus());

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


}