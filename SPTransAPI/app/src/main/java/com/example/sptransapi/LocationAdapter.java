package com.example.sptransapi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Calendar;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyHolder> {
    ArrayList<DtoLocation> dtoLocations;

    public  LocationAdapter(ArrayList<DtoLocation> dtoLocations) {
        this.dtoLocations = dtoLocations;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_adapter, parent, false);
        return new MyHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String accessibility = dtoLocations.get(position).getAccessibility().equals("true") ? "SIM" : "NÃO";

        String hour = dtoLocations.get(position).getHour();
        hour = hour.substring(hour.indexOf("T")+1, hour.lastIndexOf(":"));
        String[] horaSeparada = hour.split(":");
        hour = Calendar.getInstance().get(Calendar.HOUR) + "h" + horaSeparada[1];

        holder.prefix.setText("ÔNIBUS " + dtoLocations.get(position).getPrefix() + " - " + hour);
        holder.address.setText(dtoLocations.get(position).getAddress());
        holder.accessibility.setText("Acessibilidade para PCD: " + accessibility);
    }

    @Override
    public int getItemCount() {
        return dtoLocations.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView prefix, address, accessibility;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            prefix = itemView.findViewById(R.id.prefix);
            address = itemView.findViewById(R.id.address);
            accessibility = itemView.findViewById(R.id.accessibility);
        }
    }
}
