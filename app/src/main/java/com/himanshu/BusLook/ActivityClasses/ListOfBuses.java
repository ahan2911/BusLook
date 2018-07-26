package com.himanshu.BusLook.ActivityClasses;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.himanshu.BusLook.Adapters.AdapterOnlineBuses;
import com.himanshu.BusLook.DataClasses.Bus_Journey;
import com.himanshu.BusLook.DataClasses.TableData;
import com.himanshu.BusLook.R;
import com.himanshu.BusLook.com.himanshu.BusLook.Direction.DistanceParser;
import com.himanshu.BusLook.com.himanshu.BusLook.Direction.GetBusesList;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ListOfBuses extends AppCompatActivity {
    String busNumber, arrivalStop, distance1;
    double lat, lang;
    TextView busNumber1, arrivalStop1;
    RecyclerView recyclerView;
    ArrayList<TableData> arrayList = new ArrayList<>();
    AdapterOnlineBuses adapterOnlineBuses;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://buslook-5966f.firebaseio.com/BusList");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_buses);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        busNumber = bundle.getString("BusNumberLocal");
        arrivalStop = bundle.getString("StopName");
        lat = bundle.getDouble("StopLat");
        lang = bundle.getDouble("StopLang");
        busNumber1 = findViewById(R.id.busNumberOnlineBuses);
        arrivalStop1 = findViewById(R.id.arrivalStopOnlineBuses);
        busNumber1.append(busNumber);
        arrivalStop1.append(arrivalStop);
        recyclerView = findViewById(R.id.recyclerViewOnlineBuses);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Bus_Journey bus_journey = dataSnapshot1.child("Bus_Journey").getValue(Bus_Journey.class);
                    if(bus_journey != null){
                        if(bus_journey.getLocal_Bus_Number().equalsIgnoreCase(busNumber) && bus_journey.isStatus()){
                            TableData tableData = new TableData(bus_journey.getBusnumber(), bus_journey.getLat(), bus_journey.getLang());
                            tableData.setDistance(calculateDistance(tableData.getLat(), tableData.getLang()));
                            arrayList.add(tableData);
                        }
                    }
                }
                adapterOnlineBuses.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        adapterOnlineBuses = new AdapterOnlineBuses(ListOfBuses.this, arrayList);
        recyclerView.setAdapter(adapterOnlineBuses);
    }

    private String calculateDistance(double lat, double lang) {
        Object[] objects = new Object[1];
        StringBuilder sb = new StringBuilder();
        sb.append("https://maps.googleapis.com/maps/api/distancematrix/xml?&origins=");
        sb.append(String.valueOf(lat));
        sb.append(",");
        sb.append(String.valueOf(lang));
        sb.append("&destinations=");
        sb.append(String.valueOf(this.lat));
        sb.append(",");
        sb.append(String.valueOf(this.lang));
        sb.append("&key=AIzaSyB4RI2YTKf19dDjb6Qqoa4F8NGe3WnmCOg");
        objects[0] = sb.toString();
        GetBusesList getBusesList = new GetBusesList();
        getBusesList.execute(objects);
        try {
            Document document = getBusesList.get();
            DistanceParser distanceParser = new DistanceParser(document);
            distance1 = distanceParser.getDistance();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return distance1;
    }
}
