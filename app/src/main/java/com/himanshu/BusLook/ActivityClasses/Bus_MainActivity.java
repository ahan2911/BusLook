package com.himanshu.BusLook.ActivityClasses;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.himanshu.BusLook.Adapters.MyAdapter;
import com.himanshu.BusLook.DataClasses.Bus;
import com.himanshu.BusLook.DataClasses.Bus_Journey;
import com.himanshu.BusLook.LoginAndSignUp.LoginActivity;
import com.himanshu.BusLook.R;

import java.util.Objects;

public class Bus_MainActivity extends AppCompatActivity {
    TextView journeyBusNumber1, journeyEngineNumber1, journeyChassisNumber1, local_Bus, fromJourney1, current, toJourney1, driverName, conductorName;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    LinearLayout firstPage, secondPage;
    LocationManager locationManager;
    Boolean status = false;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus__main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String[] bus1 = Objects.requireNonNull(getIntent().getExtras()).getStringArray("KEY9");
        String bb = getIntent().getExtras().getString("KEY10");
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        journeyBusNumber1 = findViewById(R.id.journeyBusNumber1);
        journeyEngineNumber1 = findViewById(R.id.journeyEngineNumber1);
        journeyChassisNumber1 = findViewById(R.id.journeyChassisNumber1);
        local_Bus = findViewById(R.id.local_Bus);
        fromJourney1 = findViewById(R.id.fromJourney1);
        toJourney1 = findViewById(R.id.toJourney1);
        driverName = findViewById(R.id.driverName1);
        conductorName = findViewById(R.id.conductorName1);
        firstPage = findViewById(R.id.initialPage);
        secondPage = findViewById(R.id.secondPage);
        current = findViewById(R.id.current);
        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(bus1));
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://buslook-5966f.firebaseio.com/BusList/" + bb);
        final DatabaseReference databaseReference1 = databaseReference.child("Bus_Journey");
        databaseReference.child("Bus_Journey").child("status").setValue(true);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Bus bus = dataSnapshot.getValue(Bus.class);
                if (bus != null) {
                    journeyBusNumber1.append(bus.getBusNumber());
                    journeyEngineNumber1.append(bus.getEngineNumber());
                    journeyChassisNumber1.append(bus.getChassisNumber());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Bus_Journey bus_journey = dataSnapshot.getValue(Bus_Journey.class);
                if (bus_journey != null) {
                    local_Bus.append(bus_journey.getLocal_Bus_Number());
                    fromJourney1.append(bus_journey.getFrom());
                    toJourney1.append(bus_journey.getTo());
                    driverName.append(bus_journey.getDriverName());
                    conductorName.append(bus_journey.getConductorName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Bus_Journey bus_journey = dataSnapshot.getValue(Bus_Journey.class);
                if (bus_journey != null) {
                    current.setText("Current Stop : " + bus_journey.getLat() + " and " + bus_journey.getLang());
                    status = bus_journey.isStatus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.INTERNET, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            }, 10);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(status) {
                    databaseReference1.child("lat").setValue(location.getLatitude());
                    databaseReference1.child("lang").setValue(location.getLongitude());
                }
                else{
                    databaseReference1.child("lat").setValue(0);
                    databaseReference1.child("lang").setValue(0);
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.bus_signOut){
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(Bus_MainActivity.this);
            localBuilder.setTitle("Confirm");
            localBuilder.setMessage("Do you want sign out");
            DialogInterface.OnClickListener local1 = new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {}
            };
            localBuilder.setNegativeButton("No", local1);
            localBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    databaseReference.child("Bus_Journey").child("status").setValue(false);
                    startActivity(new Intent(Bus_MainActivity.this, LoginActivity.class).putExtra("KEY1", 1));
                    Toast.makeText(Bus_MainActivity.this, "Sign Out Successfully", Toast.LENGTH_SHORT).show();
                }
            });
            localBuilder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void getDetails(View view) {
        if(firstPage.getVisibility() == View.VISIBLE){
            firstPage.setVisibility(View.GONE);
            secondPage.setVisibility(View.VISIBLE);
        }
        else if(secondPage.getVisibility() == View.VISIBLE){
            secondPage.setVisibility(View.GONE);
            firstPage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {

        if(secondPage.getVisibility() == View.VISIBLE){
            secondPage.setVisibility(View.GONE);
            firstPage.setVisibility(View.VISIBLE);
        }
        else{
            super.onBackPressed();
            databaseReference.child("Bus_Journey").child("status").setValue(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.child("Bus_Journey").child("status").setValue(false);
        databaseReference.child("Bus_Journey").child("lat").setValue(0);
        databaseReference.child("Bus_Journey").child("lang").setValue(0);
    }
}
