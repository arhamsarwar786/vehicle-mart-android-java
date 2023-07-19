package com.vehicle.mart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    Context context;
    List<Vehicle> vehicles;

    public HomeAdapter(Context context, List<Vehicle> vehicles) {
        this.context = context;
        this.vehicles = vehicles;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
//        holder.tvName.setText(vehicles.get(position.getName());
        holder.tvPrice.setText(vehicles.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvPrice,status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            status = itemView.findViewById(R.id.status);

        }
    }
}
