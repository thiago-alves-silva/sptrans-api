package com.example.sptransapi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class StopAdapter extends RecyclerView.Adapter<StopAdapter.MyHolder> {
    ArrayList<DtoStop> dtoStops;

    public StopAdapter(ArrayList<DtoStop> dtoStops) {
        this.dtoStops = dtoStops;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.stop_adapter, parent, false);
        return new MyHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.stopName.setText(dtoStops.get(position).getStopName());
        holder.stopAddress.setText(dtoStops.get(position).getAddressName());
        holder.codeStop.setText(dtoStops.get(position).getCodStop());
    }

    @Override
    public int getItemCount() {
        return dtoStops.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView stopName, stopAddress, codeStop;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            stopName = itemView.findViewById(R.id.stopName);
            stopAddress = itemView.findViewById(R.id.stopAdress);
            codeStop = itemView.findViewById(R.id.codeStop);
        }
    }
}
