package com.himanshu.BusLook.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.himanshu.BusLook.DataClasses.Bus_Journey;
import com.himanshu.BusLook.ActivityClasses.ListOfBuses;
import com.himanshu.BusLook.R;
import com.himanshu.BusLook.com.himanshu.BusLook.Direction.Steps;

import java.util.ArrayList;

public class AdapterBuses2 extends RecyclerView.Adapter<AdapterBuses2.MyViewHolder> {
    private ArrayList<Steps> steps;
    private Context context;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://buslook-5966f.firebaseio.com/BusList");

    public AdapterBuses2(ArrayList<Steps> steps, Context context) {
        this.steps = steps;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_bus_list2, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Steps steps1 = steps.get(position);
        holder.stepNumber.append(String.valueOf(position + 1));
        holder.departureStopList2.setText(steps1.getDepartureStop());
        holder.stdList2.setText(steps1.getDepartureTime());
        holder.busNumberList2.setText(steps1.getBusNumber());
        holder.stopsList2.append(steps1.getNumStops());
        holder.durationList2.setText(String.valueOf(steps1.getDuration()));
        holder.distanceList2.setText(String.valueOf(steps1.getDistance() / 1000) + " Km");
        holder.arrivalStopList2.setText(steps1.getArrivalStop());
        holder.staList2.setText(steps1.getArrivalTime());
        getActiveBuses(steps1.getBusNumber(), holder);
    }

    private void getActiveBuses(final String busNumber, final MyViewHolder myViewHolder) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count = 0;
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Bus_Journey bus_journey = dataSnapshot1.child("Bus_Journey").getValue(Bus_Journey.class);
                    if(bus_journey != null){
                        if(bus_journey.getLocal_Bus_Number().equalsIgnoreCase(busNumber) && bus_journey.isStatus()){
                            count = count + 1;
                        }
                    }
                }
                myViewHolder.noba.setText(String.valueOf(count));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView departureStopList2, stdList2, busNumberList2, stopsList2, durationList2, distanceList2, arrivalStopList2, staList2, noba, stepNumber;
        LinearLayout linearLayout;
        MyViewHolder(View itemView) {
            super(itemView);
            departureStopList2 = itemView.findViewById(R.id.departureStopList2);
            stdList2 = itemView.findViewById(R.id.stdList2);
            busNumberList2 = itemView.findViewById(R.id.busNumberList2);
            stopsList2 = itemView.findViewById(R.id.stopsList2);
            durationList2 = itemView.findViewById(R.id.durationList2);
            distanceList2 = itemView.findViewById(R.id.distanceList2);
            arrivalStopList2 = itemView.findViewById(R.id.arrivalStopList2);
            staList2 = itemView.findViewById(R.id.staList2);
            noba = itemView.findViewById(R.id.noabList2);
            stepNumber = itemView.findViewById(R.id.stepNumber);
            linearLayout = itemView.findViewById(R.id.itemLayout1);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Integer.parseInt((String) noba.getText()) > 0) {
                        Intent intent = new Intent(context, ListOfBuses.class);
                        intent.putExtra("BusNumberLocal", steps.get(getAdapterPosition()).getBusNumber());
                        intent.putExtra("StopName", steps.get(getAdapterPosition()).getDepartureStop());
                        intent.putExtra("StopLat", steps.get(getAdapterPosition()).getLat());
                        intent.putExtra("StopLang", steps.get(getAdapterPosition()).getLang());
                        context.startActivity(intent);
                    }
                }
            });

        }
    }
}
