package com.vehicle.mart.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vehicle.mart.BuyNow_Screen;
import com.vehicle.mart.Installment_screen;
import com.vehicle.mart.PaymentDetails;
import com.vehicle.mart.R;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstallmentAdapter extends RecyclerView.Adapter<InstallmentAdapter.ViewHolder> {
    Context context;
    int size;
    String total, installment;
    private int position;
    String storeID,vehicleID;
    List paymentList;



    public InstallmentAdapter(Context context, int size,String total,String installment,String storeID,String vehicleID, List paymentList ) {
        this.context = context;
        this.size = size;
        this.vehicleID = vehicleID;
        this.installment = installment;
        this.total = total;
        this.storeID = storeID;
        this.paymentList = paymentList;
    }

    @NonNull
    @Override
    public InstallmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_installment,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstallmentAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public void bind(int position) {
        this.position = position;
        // Bind data to your views using the position
    }

    public int getPosition1() {
        return position;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private TextView totall;
        private TextView instal;
        private Button btn;
        private TextView status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            totall = itemView.findViewById(R.id.total);
            instal = itemView.findViewById(R.id.instal);
            status = itemView.findViewById(R.id.status);
            btn = itemView.findViewById(R.id.payNowBtn);


        }

        public void bind(int position) {



            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, position); // Add 1 to position to account for 0-indexing of months
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1; // Add 1 to month to display correctly
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            readPayments((day + " - " + month + " - " + year).toString());
            date.setText(day + " - " + month + " - " + year);
            totall.setText(total);
            instal.setText(installment);

            Log.wtf("inside dat ",paymentList.toString());


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.wtf("arham data here also ", storeID + total + size + installment + (day + " - " + month + " - " + year).toString() + " arham");

                    Intent intent = new Intent(context, PaymentDetails.class);
                    intent.putExtra("storeID", storeID);
                    intent.putExtra("total", total);
                    intent.putExtra("type", size);
                    intent.putExtra("amount", installment);
                    intent.putExtra("vehicleID",vehicleID);
                    intent.putExtra("date",(day + " - " + month + " - " + year).toString());
                    context.startActivity(intent);
                }
            });;


        }


        public void readPayments(String dateFomate) {
            FirebaseDatabase firebaseDatabase;
            DatabaseReference databaseReference;
            FirebaseAuth firebaseAuth;

            firebaseAuth = FirebaseAuth.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();


            databaseReference = firebaseDatabase.getReference("payments");
//            paymentList.clear();
//        orderByChild("buyerID").equalTo(firebaseAuth.getUid())
            databaseReference.orderByChild("buyerID").equalTo(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {


                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot paymentSnapshot : dataSnapshot.getChildren()) {

                        // Retrieve the name from the dataSnapshot
                        String paymentId = paymentSnapshot.getKey();
                        String storeId = paymentSnapshot.child("storeID").getValue(String.class);
                        String date1 = paymentSnapshot.child("date").getValue(String.class);
                        Integer type = paymentSnapshot.child("type").getValue(Integer.class);
                        Boolean verify = paymentSnapshot.child("verify").getValue(Boolean.class);
//                    double amount = paymentSnapshot.child("amount").getValue(Double.class);
                        // ... Retrieve other fields as needed
//                        Map<String, Object> dataMap = new HashMap<>();
//                        dataMap.put("date", date1);

                        String myDate = date.getText().toString();
                       Log.wtf("final test",type +  " test" + size);
                        if (myDate.equals(date1.trim()) && type == size ){
                            Log.wtf("final test a",type +  " test" + size + verify);

                            if (verify){
                                status.setText("PAID");
                                status.setTextColor(Color.GREEN);
                                btn.setVisibility(View.GONE);
                                break;
                            }else{
                                status.setText("Pending");
                                status.setTextColor(Color.YELLOW);
                                btn.setVisibility(View.GONE);
                                break;
                            }


                        }else{
                            status.setText("UnPaid");
                            status.setTextColor(Color.RED);
                            if (!myDate.equals(date1)){
//                                btn.setVisibility(View.GONE);
                            }
                        }




//                        dataMap.put("verify", verify);
//                        paymentList.add(dataMap);
                        // Do something with the retrieved data
                        // For example, log the values
                        Log.d("Payment", "Payment ID: " + paymentId);
                        Log.d("Payment", "Store ID: " + storeId);
//                    Log.d("Payment", "Amount: " + amount);
                        Log.wtf("arham installments",myDate.toString() + date1);

                        // Set the name to the nameText component
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }



}
