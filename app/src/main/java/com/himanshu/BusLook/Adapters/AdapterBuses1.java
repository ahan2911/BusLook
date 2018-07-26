package com.himanshu.BusLook.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.himanshu.BusLook.ActivityClasses.DetailActivity;
import com.himanshu.BusLook.R;
import com.himanshu.BusLook.com.himanshu.BusLook.Direction.Route;
import com.himanshu.BusLook.com.himanshu.BusLook.Direction.Steps;
import java.util.ArrayList;

public class AdapterBuses1 extends RecyclerView.Adapter<AdapterBuses1.MyViewHolder> {
    private ArrayList<Route> routes;
    private Context context;

    public AdapterBuses1(ArrayList<Route> routes, Context context) {
        this.routes = routes;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_bus_list1, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ArrayList<Steps> steps = routes.get(position).getSteps();
        Steps steps1 = steps.get(0);
        Steps steps2 = steps.get(steps.size()-1);
        String distance = getTotalDistance(steps);
        String duration = getTotalDuration(steps);
        String busNumber = getBusNumbers(steps);
        holder.routeNumber.append(String.valueOf(position + 1));
        holder.arrivalStopList1.setText(steps2.getArrivalStop());
        holder.staList1.setText(steps2.getArrivalTime());
        holder.busNumberList1.setText(busNumber);
        holder.stopsList1.append(getTotalStops(steps));
        holder.durationList1.setText(duration);
        holder.distanceList1.setText(distance);
        holder.departureStopList1.setText(steps1.getDepartureStop());
        holder.stdList1.setText(steps1.getDepartureTime());
    }

    private String getBusNumbers(ArrayList<Steps> steps) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < steps.size(); i++){
            if(i == 0){
                sb.append(steps.get(i).getBusNumber());
            }
            else{
                sb.append("-->");
                sb.append(steps.get(i).getBusNumber());
            }
        }
        return sb.toString();
    }

    private String getTotalStops(ArrayList<Steps> steps) {
        int stops = 0;
        for(int i = 0; i < steps.size(); i++){
            stops += Integer.parseInt(steps.get(i).getNumStops());
        }
        return String.valueOf(stops);
    }

    private String getTotalDuration(ArrayList<Steps> steps) {
        Duration duration = new Duration(steps.get(0).getDepartureTime(), steps.get(steps.size()-1).getArrivalTime());
        return duration.duration;
    }

    private String getTotalDistance(ArrayList<Steps> steps) {
        double distance = 0;
        for(int i = 0 ; i < steps.size(); i++){
            distance += steps.get(i).getDistance();
        }
        distance = distance / 1000;
        return String.valueOf(distance) + "KM";
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView departureStopList1, stdList1, busNumberList1, stopsList1, durationList1, distanceList1, arrivalStopList1, staList1, routeNumber;
        LinearLayout itemLayout;
        MyViewHolder(View itemView) {
            super(itemView);
            departureStopList1 = itemView.findViewById(R.id.departureStopList1);
            stdList1 = itemView.findViewById(R.id.stdList1);
            busNumberList1 = itemView.findViewById(R.id.busNumberList1);
            stopsList1 = itemView.findViewById(R.id.stopsList1);
            durationList1 = itemView.findViewById(R.id.durationList1);
            distanceList1 = itemView.findViewById(R.id.distanceList1);
            arrivalStopList1 = itemView.findViewById(R.id.arrivalStopList1);
            staList1 = itemView.findViewById(R.id.staList1);
            routeNumber = itemView.findViewById(R.id.routeNumber);
            itemLayout = itemView.findViewById(R.id.itemLayout);
            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("RouteDetail", routes.get(getAdapterPosition()));
                    context.startActivity(intent);


                }
            });
        }
    }

    private class Duration {
        private String departureTime, arrivalTime, duration;
        Duration(String departureTime, String arrivalTime) {
            this.departureTime = departureTime;
            this.arrivalTime = arrivalTime;
            this.duration = getDuration(this.departureTime, this.arrivalTime);
        }
        private String getDuration(String departureTime, String arrivalTime) {
            String[] time1 = timeGet(departureTime);
            String[] time2 = timeGet(arrivalTime);
            int d = 0, h, m;
            int h1 = Integer.parseInt(time1[0]), m1 = Integer.parseInt(time1[1]);
            int h2 = Integer.parseInt(time2[0]), m2 = Integer.parseInt(time2[1]);
            m = m2 - m1;
            if (m < 0) {
                m = 60 - (m1 - m2);
                h2 = h2 - 1;
            }
            h = h2 - h1;
            if(h < 0){
                h = h * -1;
                d++;
            }
            if(d!=0){
                return String.valueOf(d) + "days " + String.valueOf(h) + " hr " + String.valueOf(m) + " min";
            }
            else if(h !=0){
                return String.valueOf(h) + " hr " + String.valueOf(m) + " min";
            }
            else{
                return String.valueOf(m) + " min";
            }
        }
        String[] timeGet(String time) {
            String[] strings;
            if (time.contains("pm")) {
                StringBuilder stringBuilder = new StringBuilder(time);
                stringBuilder.indexOf("pm");
                stringBuilder.delete(stringBuilder.indexOf("pm"), stringBuilder.indexOf("pm") + 2);
                String s = stringBuilder.toString();
                strings = s.split(":");
                int s1 = Integer.parseInt(strings[0]) + 12;
                strings[0] = String.valueOf(s1);
            } else {
                StringBuilder stringBuilder = new StringBuilder(time);
                stringBuilder.indexOf("am");
                stringBuilder.delete(stringBuilder.indexOf("am"), stringBuilder.indexOf("am") + 2);
                String s = stringBuilder.toString();
                strings = s.split(":");
            }
            return strings;
        }
    }
}
