package com.vehicle.mart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.vehicle.mart.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    TextInputEditText et_fname, et_lname, et_cnic, et_contact, et_email, et_password, et_confirmpassword, et_address;
    Button btn_register;
    TextView tv_account, tv_signin;
    RadioGroup rgType;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String TAG = "theS";

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        progressDialog = new ProgressDialog(Signup.this);
        progressDialog.setMessage("Please! wait for Loading!");
        progressDialog.setCancelable(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        et_fname = findViewById(R.id.et_fname);
        et_lname = findViewById(R.id.et_lname);
        et_cnic = findViewById(R.id.et_cnic);
        et_contact = findViewById(R.id.et_contact);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_confirmpassword = findViewById(R.id.et_confirmpassword);
        et_address = findViewById(R.id.et_address);
        btn_register = findViewById(R.id.btn_register);
        tv_account = findViewById(R.id.tv_account);
        tv_signin = findViewById(R.id.tv_signin);
        rgType = findViewById(R.id.rg_type);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = et_fname.getText().toString();
                String lname = et_lname.getText().toString();
                String cnic = et_cnic.getText().toString();
                String contact = et_contact.getText().toString();
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                String confirmpassword = et_confirmpassword.getText().toString();
                String address = et_address.getText().toString();
                int id=rgType.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(id);


                boolean check = validateinfo(fname, lname, cnic, contact, email, password, confirmpassword, address);
                if (check) {
//
                    progressDialog.show();

                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "onComplete: "+task.isSuccessful());
                            Log.d(TAG, "onComplete: "+task.getResult().toString());
                            if (task.isSuccessful()) {
                                User user = new User(firebaseAuth.getUid(),radioButton.getText().toString(),fname+" "+lname,email,contact,password,cnic);
                                Log.wtf("arham",user.getUserType() + radioButton.getText());
                                databaseReference.child(firebaseAuth.getUid()).setValue(user);
                                if (user.getUserType().equals("Seller")){
                                    Intent i = new Intent(Signup.this, StoreActivity.class);
                                startActivity(i);
                                Toast.makeText(Signup.this, "Account Successfully Created!", Toast.LENGTH_SHORT).show();
                                finish();
                                }else{
                                    Intent i = new Intent(Signup.this, login.class);
                                    startActivity(i);
                                    Toast.makeText(Signup.this, "Account Successfully Created!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
//
                            }else{
                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(Signup.this, errorMessage, Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                        }
                    });
                }
            }
        });
        tv_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Signup.this,login.class);
                startActivity(i);
            }
        });

    }

    private boolean validateinfo(String fname, String lname, String cnic, String contact, String email, String password, String confirmpassword, String address) {
        if (fname.length() == 0) {
            et_fname.requestFocus();
            et_fname.setError("THIS FIELD IS REQUIRED");
            return false;
        }
        else if (lname.length() == 0) {
            et_lname.requestFocus();
            et_lname.setError("THIS FIELD IS REQUIRED");
            return false;
        }
        else if (cnic.length() == 0) {
            et_cnic.requestFocus();
            et_cnic.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!cnic.matches("^[0-9+]{5}-[0-9+]{7}-[0-9]{1}$")) {
            et_cnic.requestFocus();
            et_cnic.setError("ENTER VALID CNIC");
            return false;
        } else if (contact.length() == 0) {
            et_contact.requestFocus();
            et_contact.setError("THIS FIELD IS REQUIRED");
            return false;
        } else if (!contact.startsWith("+92")&&(contact.length() < 11 || contact.length() > 11)) {

            et_contact.requestFocus();
            et_contact.setError("ENTER VALID NUMBER");
            return false;
        } else if (email.length() == 0) {
            et_email.requestFocus();
            et_email.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            et_email.requestFocus();
            et_email.setError("ENTER VALID EMAIL");
            return false;
        } else if (password.length() <= 7) {
            et_password.requestFocus();
            et_password.setError("MINIMUM 8 CHARACTER REQUIRED");
            return false;
        } else if (confirmpassword.length() == 0) {
            et_confirmpassword.requestFocus();
            et_confirmpassword.setError("PLEASE CONFIRM PASSWORD");
            return false;
        } else if (!confirmpassword.equals(password)) {
            et_confirmpassword.requestFocus();
            et_confirmpassword.setError("CHECK YOUR PASSWORD AGAIN");
            return false;
        } else if (address.length() == 0) {
            et_address.requestFocus();
            et_address.setError("THIS FIELD IS REQUIRED");
            return false;
        }
        return true;
    }

}