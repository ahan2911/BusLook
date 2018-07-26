package com.himanshu.BusLook.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.himanshu.BusLook.ActivityClasses.ListOfBuses;
import com.himanshu.BusLook.DataClasses.TableData;
import com.himanshu.BusLook.R;

import java.util.ArrayList;

public class AdapterOnlineBuses extends RecyclerView.Adapter<AdapterOnlineBuses.MyViewHolder> {
    private Context context;
    private ArrayList<TableData> arrayList;
    public AdapterOnlineBuses(Context context, ArrayList<TableData> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.table_of_buses, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.busNumberTable.setText(arrayList.get(position).getBusNumber());
        holder.positionTable.setText(arrayList.get(position).getPlace());
        holder.distanceTable.setText(arrayList.get(position).getDistance());
        holder.etaTable.setText("NA");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView busNumberTable, positionTable, distanceTable, etaTable;
        MyViewHolder(View itemView) {
            super(itemView);
            busNumberTable = itemView.findViewById(R.id.busNumberTable);
            positionTable = itemView.findViewById(R.id.positionTable);
            distanceTable = itemView.findViewById(R.id.distanceTable);
            etaTable = itemView.findViewById(R.id.etaTable);
        }
    }
}
