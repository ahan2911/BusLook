package com.himanshu.BusLook.Adapters;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.RelativeLayout;

import com.himanshu.BusLook.R;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] dataset;

    public MyAdapter(String[] dataSet) {
        this.dataset=dataSet;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.my_text_view, parent, false);
        return new MyViewHolder(view);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(position == dataset.length-1){
            String title = dataset[position];
            holder.bottom.setVisibility(View.VISIBLE);
            holder.middle.setVisibility(View.GONE);
            holder.endStop.setText(title);
        }
        else if(position == 0){
            holder.bottom.setVisibility(View.GONE);
            holder.middle.setVisibility(View.GONE);
        }
        else{
            String title = dataset[position];
            holder.bottom.setVisibility(View.GONE);
            holder.middle.setVisibility(View.VISIBLE);
            holder.stop2.setText(title);
            holder.stopNumber.setText(String.valueOf(position));
        }
    }




    @Override
    public int getItemCount() {
        return dataset.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout bottom;
        RelativeLayout middle;
        TextView stop2, stopNumber, endStop;
        public MyViewHolder(View itemView) {
            super(itemView);
            middle = itemView.findViewById(R.id.layout_middle);
            bottom = itemView.findViewById(R.id.layout_bottom);
            stop2 = itemView.findViewById(R.id.txt_stop);
            stopNumber = itemView.findViewById(R.id.textView3);
            endStop = itemView.findViewById(R.id.txt_stop_end);
        }

    }
}
