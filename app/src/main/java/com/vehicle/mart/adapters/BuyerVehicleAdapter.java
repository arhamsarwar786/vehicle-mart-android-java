package com.vehicle.mart.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vehicle.mart.R;
import com.vehicle.mart.Vehicle;
import com.vehicle.mart.VehicleBuyerDetailsActivity;

import java.util.List;

public class BuyerVehicleAdapter extends RecyclerView.Adapter<BuyerVehicleAdapter.ViewHolder> {
    Context context;
    List<Vehicle> vehicleList;

    public BuyerVehicleAdapter(Context context, List<Vehicle> vehicleList) {
        this.context = context;
        this.vehicleList = vehicleList;
    }

    @NonNull
    @Override
    public BuyerVehicleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyerVehicleAdapter.ViewHolder holder, int position) {

        holder.tvName.setText(vehicleList.get(position).getBrand());
        holder.tvPrice.setText(vehicleList.get(position).getPrice());
        Glide.with(context).load(vehicleList.get(position).getImage()).placeholder(R.drawable.logo).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.wtf("arham now",vehicleList.get(position) + "my data" + position);



                Intent intent = new Intent(context, VehicleBuyerDetailsActivity.class);
                intent.putExtra("vehicleDetails",vehicleList.get(position));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvPrice,status;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            imageView = itemView.findViewById(R.id.image_view);

        }
    }
}

