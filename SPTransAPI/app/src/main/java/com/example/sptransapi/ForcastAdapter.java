package com.example.sptransapi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ForcastAdapter extends RecyclerView.Adapter<ForcastAdapter.MyHolder> {
    ArrayList<DtoForecast> dtoForecastArrayList;

    public ForcastAdapter(ArrayList<DtoForecast> dtoForecastArrayList) {
        this.dtoForecastArrayList = dtoForecastArrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_adapter, parent, false);
        return new ForcastAdapter.MyHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.stopName.setText(dtoForecastArrayList.get(position).getStopName() + " - " + dtoForecastArrayList.get(position).getStopCode());
        holder.forecast.setText("Previsão - " + dtoForecastArrayList.get(position).getForecastTime() + " (Ônibus - " + dtoForecastArrayList.get(position).getBusCode() + ")");
        holder.stopAddress.setText(dtoForecastArrayList.get(position).getStopAddress());
    }

    @Override
    public int getItemCount() {
        return dtoForecastArrayList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView stopName, forecast, stopAddress;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            stopName = itemView.findViewById(R.id.stopName);
            stopAddress = itemView.findViewById(R.id.stopAdress);
            forecast = itemView.findViewById(R.id.forecast);
        }
    }
}
