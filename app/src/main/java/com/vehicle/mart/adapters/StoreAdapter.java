package com.vehicle.mart.adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vehicle.mart.AdminStoreVerification;
import com.vehicle.mart.R;
import com.vehicle.mart.StoreModel;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {
    private List<StoreModel> storeList;

    public StoreAdapter(AdminStoreVerification adminStoreVerification, List<StoreModel> storeList) {
        this.storeList = storeList;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tile, parent, false);
        return new StoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        StoreModel store = storeList.get(position);
        holder.bind(store);
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    // ViewHolder class
    public class StoreViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView ntnNumber;
        private TextView address;
        private ImageView ivAccept;

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.username);
            ntnNumber = itemView.findViewById(R.id.ntnNumber);
            address = itemView.findViewById(R.id.address);

        }

        public void bind(StoreModel store) {
            nameTextView.setText(store.getName());
            address.setText("Address: " + store.getAddress());
            ntnNumber.setText("NTN: "  + store.getNtn());

            ivAccept = itemView.findViewById(R.id.iv_accept);


            ivAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.wtf("data here","Cliccked");
                    // Update the status of the corresponding StoreModel in Firebase to true
                    DatabaseReference storeRef = FirebaseDatabase.getInstance().getReference("stores")
                            .child(store.getUserId());
                    storeRef.child("verified").setValue(true);
//                    Toast.makeText(context,"Your message here", Toast.LENGTH_SHORT).show();

                    ((Activity) v.getContext()).finish();


                }
            });

            // You can bind other views as needed


        }


    }


}
