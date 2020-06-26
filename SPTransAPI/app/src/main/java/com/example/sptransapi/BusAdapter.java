package com.example.sptransapi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.MyHolder> {
    ArrayList<DtoBus> dtoBusArrayList;

    public BusAdapter(ArrayList<DtoBus> dtoBusArrayList) {
        this.dtoBusArrayList = dtoBusArrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_adapter, parent, false);
        return new MyHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String base = dtoBusArrayList.get(position).getBase();
        base = base.length() >= 2 ? "-" + base : "";
        String way = dtoBusArrayList.get(position).getWay();
        if(way.equals("Ida")) {
            holder.stopStart.setText(dtoBusArrayList.get(position).getStopStart());
            holder.stopEnd.setText(dtoBusArrayList.get(position).getStopEnd());
        } else {
            holder.stopStart.setText(dtoBusArrayList.get(position).getStopEnd());
            holder.stopEnd.setText(dtoBusArrayList.get(position).getStopStart());
        }
        holder.busNumber.setText(dtoBusArrayList.get(position).getNumber() + base + " (" + way + ")");
        holder.codeLine.setText("Linha: " + dtoBusArrayList.get(position).getCodLine());
    }

    @Override
    public int getItemCount() {
        return dtoBusArrayList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView stopStart, stopEnd, busNumber, codeLine;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            stopStart = itemView.findViewById(R.id.partida);
            stopEnd = itemView.findViewById(R.id.saida);
            busNumber = itemView.findViewById(R.id.busNumber);
            codeLine = itemView.findViewById(R.id.codeLine);
        }
    }
}