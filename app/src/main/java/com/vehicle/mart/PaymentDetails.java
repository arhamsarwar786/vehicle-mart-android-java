package com.vehicle.mart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vehicle.mart.model.Store;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PaymentDetails extends AppCompatActivity {


    Button btn_save;
    ImageView imageView,ivAdd;
    Uri uri =null;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Store storeData;

    DatabaseReference storeReference;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_upload_payment_proof);
        imageView = findViewById(R.id.image_view);

        ivAdd = findViewById(R.id.iv_add);
        btn_save = findViewById(R.id.btn_save);
        Log.d("btn_save","btn_save");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("payments");

        String storeID = getIntent().getStringExtra("storeID");

        String total = getIntent().getStringExtra("total");

        int type = getIntent().getIntExtra("type",1);
        String amount = getIntent().getStringExtra("amount");
        String date = getIntent().getStringExtra("date");


        Log.wtf("arham data here also", storeID + total + type + amount + date + " arham");



        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
//        storeReference = firebaseDatabase.getReference("stores").child(storeID);

//        readStoresFromFirebase();
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


        if (uri == null){
            Toast.makeText(PaymentDetails.this, "Please, select Image first!", Toast.LENGTH_SHORT).show();
        }else{


                    progressDialog = ProgressDialog.show(PaymentDetails.this, " Payment Slip Uploading...", "Please wait", false);

                    String id = UUID.randomUUID().toString();
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("payment-proof/" + id);
                    UploadTask uploadTask = storageRef.putFile(uri);
uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
    @Override
    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        // Once the image is uploaded successfully, get the download URL of the image
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Create a new Vehicle object with the image URL and other details
//                Vehicle vehicle = new Vehicle(firebaseAuth.getUid(), vehicleType, type, brand, model, year, mileage, transmission, fuelType, engineType, engineCapacity, registerCity, transactionType, price, description, uri.toString(), "pending");
                // Save the new Vehicle object to Firebase Realtime Database
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("payments");
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("url", uri.toString());
                dataMap.put("buyerID", firebaseAuth.getUid());
                dataMap.put("storeID", storeID);
                dataMap.put("date", date);
                dataMap.put("type", type);
                dataMap.put("total", total);
                dataMap.put("amount", amount);
                dataMap.put("verify", false);

                databaseRef.child(id).setValue(dataMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Vehicle object saved successfully to Firebase Realtime Database
                            progressDialog.dismiss();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Failed to save Vehicle object to Firebase Realtime Database
                            progressDialog.dismiss();
                            Toast.makeText(PaymentDetails.this, "Failed to save vehicle details", Toast.LENGTH_SHORT).show();
                        }
                    });
            }

        });
    }
});
// .addOnFailureListener(new OnFailureListener() {
//    @Override
//    public void onFailure(@NonNull Exception e) {
//        // Failed to upload image to Firebase Storage
//        progressDialog.dismiss();
//        Toast.makeText(Store_detail.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
//    }
//});
//                    String id = UUID.randomUUID().toString();

//                    progressDialog.dismiss();
//                    Vehicle obj = new Vehicle(firebaseAuth.getUid(),vehicleType,type,brand,model,year,mileage,transmission,fuelType,engineType,engineCapacity,registerCity,transactionType,price,description,uri.toString(),"Pending");
//                    Log.d("theS", "onSuccess: "+obj);
//                    databaseReference.push().setValue(obj);
//                    finish();
//// Show a progress dialog while the image is being uploaded and the Vehicle object is being saved to Firebase Realtime Database
//                    storageReference.child(id).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            progressDialog = ProgressDialog.show(Store_detail.this, "", "Please wait", false);
//
//                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                @Override
//                                public void onSuccess(Uri uri) {
//                                    progressDialog.dismiss();
//                                    Vehicle obj = new Vehicle(firebaseAuth.getUid(),vehicleType,type,brand,model,year,mileage,transmission,fuelType,engineType,engineCapacity,registerCity,transactionType,price,description,uri.toString(),"Pending");
//                                    Log.d("theS", "onSuccess: "+obj);
//                                    databaseReference.push().setValue(obj);
//                                    finish();
//                                }
//                            });
//                        }
//                    });



            }};

        });
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Get data from Gallery
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                uri = data.getData();
                Log.wtf("data here of uri",uri.toString());

                imageView.setImageURI(uri);
            }
        }
    }


    public void readStoresFromFirebase(){
        storeReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Store store = snapshot.getValue(Store.class);
                storeData = store;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




}