package com.vehicle.mart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


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

import java.util.UUID;

public class Store_detail extends AppCompatActivity {


    TextInputEditText et_Vehiclename,et_vehicletype,et_transmission,et_description,et_mileage,et_brand,et_model,et_year,et_fueltype,et_enginetype,et_enginecapacity,et_registercity,et_type,et_transactiontype,et_price;
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

        setContentView(R.layout.activity_store_detail);
        imageView = findViewById(R.id.image_view);
        et_mileage = findViewById(R.id.et_mileage);
        et_description = findViewById(R.id.et_description);
        et_Vehiclename = findViewById(R.id.et_Vehiclename);
        et_vehicletype = findViewById(R.id.et_vehicletype);
        et_brand = findViewById(R.id.et_brand);
        et_model = findViewById(R.id.et_model);
        et_year = findViewById(R.id.et_year);
        et_fueltype = findViewById(R.id.et_fueltype);
        et_enginecapacity = findViewById(R.id.et_enginecapacity);
        et_enginetype = findViewById(R.id.et_enginetype);
        et_registercity = findViewById(R.id.et_registercity);
        et_type = findViewById(R.id.et_type);
        et_transmission = findViewById(R.id.et_transmission);
        et_transactiontype = findViewById(R.id.et_transactiontype);
        et_price = findViewById(R.id.et_price);
        ivAdd = findViewById(R.id.iv_add);
        btn_save = findViewById(R.id.btn_save);
        Log.d("btn_save","btn_save");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("vehicles");

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        storeReference = firebaseDatabase.getReference("stores").child(firebaseAuth.getUid());

        readStoresFromFirebase();
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String vehicleType = et_vehicletype.getText().toString();
                String type = et_type.getText().toString();
                String brand = et_brand.getText().toString();
                String model = et_model.getText().toString();
                String year = et_year.getText().toString();
                String mileage = et_mileage.getText().toString();
                String transmission = et_transmission.getText().toString();
                String fuelType = et_fueltype.getText().toString();
                String engineType = et_enginetype.getText().toString();
                String engineCapacity = et_enginecapacity.getText().toString();
                String registerCity = et_registercity.getText().toString();
                String transactionType = et_transactiontype.getText().toString();
                String price = et_price.getText().toString();
                String description = et_description.getText().toString();


                boolean res = validateinfo(vehicleType,type,brand,model,year,mileage,transmission,fuelType,engineType,engineCapacity,registerCity,transactionType,price,description,price);
                Log.wtf("arham", String.valueOf(res));
                if (res) {
                    progressDialog = ProgressDialog.show(Store_detail.this, "Vehical Adding!", "Please wait", false);

                    String id = UUID.randomUUID().toString();
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("vehicle_images/" + id);
                    UploadTask uploadTask = storageRef.putFile(uri);
uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
    @Override
    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        // Once the image is uploaded successfully, get the download URL of the image
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Create a new Vehicle object with the image URL and other details
                Vehicle vehicle = new Vehicle(firebaseAuth.getUid(), vehicleType, type, brand, model, year, mileage, transmission, fuelType, engineType, engineCapacity, registerCity, transactionType, price, description, uri.toString(), "pending");
                // Save the new Vehicle object to Firebase Realtime Database
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("vehicles");
                databaseRef.child(id).setValue(vehicle)
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
                            Toast.makeText(Store_detail.this, "Failed to save vehicle details", Toast.LENGTH_SHORT).show();
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

                }
else{
//                    Toast.makeText("Please, Fill all details").show();
                    Toast.makeText(Store_detail.this,"Please, Fill all details",Toast.LENGTH_SHORT).show();

                }
            }

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

    private boolean validateinfo(String Vehiclename,String Vehicletype,String Transmission,String Description,String Mileage,String Brand,String Model,String Year,String Fueltype,String Enginetype,String Enginecapacity,String Registercity,String Type,String Transactiontype,String Price) {
        if (Vehiclename.length() == 0) {
            et_Vehiclename.requestFocus();
            et_Vehiclename.setError("THIS FIELD IS REQUIRED");
            Toast.makeText(this, "Vehical Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (Vehicletype.length() == 0) {
            et_vehicletype.requestFocus();
            et_vehicletype.setError("THIS FIELD IS REQUIRED");
            return false;
        }
        else if (Transmission.length() == 0) {
            et_transmission.requestFocus();
            et_transmission.setError("THIS FIELD IS REQUIRED");
            return false;
        }
        else if (Description.length() == 0) {
            et_description.requestFocus();
            et_description.setError("THIS FIELD IS REQUIRED");
            return false;
        }
        else if (Mileage.length() == 0) {
            et_mileage.requestFocus();
            et_mileage.setError("THIS FIELD IS REQUIRED");
            return false;
        }
        else if (Brand.length() == 0) {
            et_brand.requestFocus();
            et_brand.setError("THIS FIELD IS REQUIRED");
            return false;
        }
        else if (Model.length() == 0) {
            et_model.requestFocus();
            et_model.setError("THIS FIELD IS REQUIRED");
            return false;
        }
        else if (Year.length() == 0) {
            et_year.requestFocus();
            et_year.setError("THIS FIELD IS REQUIRED");
            return false;
        }
        else if (Fueltype.length() == 0) {
            et_fueltype.requestFocus();
            et_fueltype.setError("THIS FIELD IS REQUIRED");
            return false;
        }
        else if (Enginecapacity.length() == 0) {
            et_enginecapacity.requestFocus();
            et_enginecapacity.setError("THIS FIELD IS REQUIRED");
            return false;
        }
        else if (Enginetype.length() == 0) {
            et_enginetype.requestFocus();
            et_enginetype.setError("THIS FIELD IS REQUIRED Arham");
            return false;
        }
        else if (Registercity.length() == 0) {
            et_registercity.requestFocus();
            et_registercity.setError("THIS FIELD IS REQUIRED");
            return false;
        }
        else if (Type.length() == 0) {
            et_type.requestFocus();
            et_type.setError("THIS FIELD IS REQUIRED");
            return false;
        }
        else if (Transactiontype.length() == 0) {
            et_transactiontype.requestFocus();
            et_transactiontype.setError("THIS FIELD IS REQUIRED");
            return false;
        }
        else if (Price.length() == 0) {
            et_price.requestFocus();
            et_price.setError("THIS FIELD IS REQUIRED");
            return false;
        }
        else if (uri == null) {
            Toast.makeText(this, "Please insert image", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Get data from Gallery
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                uri = data.getData();
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