package com.himanshu.BusLook.ActivityClasses;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.himanshu.BusLook.Adapters.AdapterBuses1;
import com.himanshu.BusLook.LoginAndSignUp.ChangePassword;
import com.himanshu.BusLook.DataClasses.User;
import com.himanshu.BusLook.LoginAndSignUp.LoginActivity;
import com.himanshu.BusLook.R;
import com.himanshu.BusLook.com.himanshu.BusLook.Direction.BusStopList;
import com.himanshu.BusLook.com.himanshu.BusLook.Direction.BusesOnRoute;
import com.himanshu.BusLook.com.himanshu.BusLook.Direction.DirectionParser;
import com.himanshu.BusLook.com.himanshu.BusLook.Direction.GetBusesList;
import com.himanshu.BusLook.com.himanshu.BusLook.Direction.GetDetails;
import com.himanshu.BusLook.com.himanshu.BusLook.Direction.Route;
import org.w3c.dom.Document;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private final int REQUEST_CODE_FOR_SEARCH_INTENT = 15;
    AutoCompleteTextView search;
    TextView userCurrent, userName, phoneNumber;
    ImageView searchIcon;
    LocationManager locationManager;
    Geocoder geocoder;
    Spinner spinner;
    String origin, destination;
    ArrayList<Route> routes = new ArrayList<>();
    AdapterBuses1 adapterBuses1;
    ProgressDialog progressDialog = null;
    String userName1;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        userName1 = bundle.getString("UserName");
        Log.d("hello", userName1);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://buslook-5966f.firebaseio.com/UserList" + userName1);
        databaseReference.addListenerForSingleValueEvent(valueEventListener);
        progressDialog = new ProgressDialog(UserActivity.this, R.style.MyProgressDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Getting Location");
        progressDialog.show();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        search = findViewById(R.id.search);
        userCurrent = findViewById(R.id.userCurrent);
        spinner = findViewById(R.id.fromBusStop);
        searchIcon = findViewById(R.id.searchIcon);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 2000, locationListener);

        NavigationView navigationView = findViewById(R.id.nav_view);
        userName = navigationView.getHeaderView(0).findViewById(R.id.userName);
        phoneNumber = navigationView.getHeaderView(0).findViewById(R.id.phoneNumber);
        navigationView.setNavigationItemSelectedListener(this);
        search.setOnClickListener(forSearch);
        spinner.setOnItemSelectedListener(stopSelector);

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
                getConfirmation();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        else if(id == R.id.logOut){
            getConfirmation();
        }
        else if(id == R.id.changePassword){
            Intent intent = new Intent(UserActivity.this, ChangePassword.class);
            intent.putExtra("userName1", userName1);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//this part is used to get location updates and near by bus stops
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if(progressDialog != null)
                progressDialog.dismiss();
            geocoder = new Geocoder(UserActivity.this);
            try {
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                userCurrent.setText(addresses.get(0).getAddressLine(0));
                StringBuilder sb = new StringBuilder();
                sb.append("https://maps.googleapis.com/maps/api/place/nearbysearch/xml?location=");
                sb.append(String.valueOf(location.getLatitude()));
                sb.append(",");
                sb.append(String.valueOf(location.getLongitude()));
                sb.append("&radius=1000&type=bus_station&key=AIzaSyB4RI2YTKf19dDjb6Qqoa4F8NGe3WnmCOg");
                Object object[] = new Object[3];
                object[0] = sb.toString();
                object[1] = spinner;
                object[2] = UserActivity.this;
                GetDetails getDetails = new GetDetails();
                getDetails.execute(object);
            } catch (IOException e) {
                e.printStackTrace();
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
    };


    //Here location updating is end
    public void clearText(View view) {
        search.setText("");
        ImageButton clear = findViewById(R.id.clearButton);
        clear.setVisibility(View.GONE);
        searchIcon.setVisibility(View.VISIBLE);
        if(routes != null) {
            routes.clear();
            adapterBuses1.notifyDataSetChanged();
        }
    }

    //This is used to open intent to search for a destination
    View.OnClickListener forSearch = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!routes.isEmpty()){
                routes.clear();
                search.setText("");
                adapterBuses1.notifyDataSetChanged();
            }
            try {
                AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder()
                        .setTypeFilter(Place.TYPE_BUS_STATION)
                        .setCountry("IN")
                        .build();
                Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                        .setFilter(autocompleteFilter)
                        .setBoundsBias(new LatLngBounds(new LatLng(26.0, 75.0), new LatLng(27.0, 76.0)))
                        .build(UserActivity.this);
                startActivityForResult(intent, REQUEST_CODE_FOR_SEARCH_INTENT);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        }
    };

    //This is used to perform further operation when the item is selected from drop down menu

    AdapterView.OnItemSelectedListener stopSelector = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            BusStopList item = (BusStopList) adapterView.getItemAtPosition(i);
            if(item.getPlaceId() != null)
                origin = item.getPlaceId();

            LinearLayout searchLayout = findViewById(R.id.searchLayout);
            if(adapterView.getItemAtPosition(i).toString().equals("No Bus Stop Near You")){
                searchLayout.setVisibility(View.GONE);
            }
            else {
                if (!adapterView.getItemAtPosition(i).toString().equals("Select Your Bus Stop") || !adapterView.getItemAtPosition(i).toString().equals("No Bus Stop Near You")) {
                    searchLayout.setVisibility(View.VISIBLE);
                    search.setText("");
                    routes.clear();
                    if (adapterBuses1 != null) {
                        adapterBuses1.notifyDataSetChanged();
                    }
                } else {
                    searchLayout.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };

    //Now the destination is got and search for buses can be done
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_FOR_SEARCH_INTENT) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                search.setText(place.getName());
                destination = place.getId();
                searchIcon.setVisibility(View.GONE);
                ImageButton clear = findViewById(R.id.clearButton);
                clear.setVisibility(View.VISIBLE);

                String urlForDirection = generateUrl(origin, destination);
                Object[] objects = new Object[1];
                objects[0] = urlForDirection;
                GetBusesList getBusesList = new GetBusesList();
                getBusesList.execute(objects);

                try {
                    Document document = getBusesList.get();
                    DirectionParser directionParser = new DirectionParser(document);
                    BusesOnRoute busesOnRoute = directionParser.getNumberOfRoutes();
                    displayBuses(busesOnRoute);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i("hello", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }

    private void displayBuses(BusesOnRoute busesOnRoute) {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewBuses);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        routes = busesOnRoute.getRoutes();
        adapterBuses1 = new AdapterBuses1(routes, UserActivity.this);
        recyclerView.setAdapter(adapterBuses1);
    }

    @NonNull
    private String generateUrl(String origin, String destination) {
        StringBuilder sb = new StringBuilder();
        sb.append("https://maps.googleapis.com/maps/api/directions/xml?origin=place_id:");
        sb.append(origin);
        sb.append("&destination=place_id:");
        sb.append(destination);
        sb.append("&mode=transit&transit_mode=bus&alternatives=true&transit_routing_preference=less_walking&key=");
        sb.append("AIzaSyB4RI2YTKf19dDjb6Qqoa4F8NGe3WnmCOg");
        return sb.toString();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(LoginActivity.progressDialog != null){
            LoginActivity.progressDialog.dismiss();
            LoginActivity.progressDialog = null;
        }
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            User user = dataSnapshot.getValue(User.class);
            if(user != null) {
                userName.setText(user.getFullName());
                phoneNumber.setText(user.getMobile());
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public void getConfirmation(){
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(UserActivity.this);
        localBuilder.setTitle("Confirm");
        localBuilder.setCancelable(false);
        localBuilder.setMessage("Do you want sign out");
        DialogInterface.OnClickListener local1 = new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {}
        };
        localBuilder.setNegativeButton("No", local1);
        localBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseReference.child("status").setValue(false);
                startActivity(new Intent(UserActivity.this, LoginActivity.class).putExtra("KEY1", 2));
                Toast.makeText(UserActivity.this, "Sign Out Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        localBuilder.show();
    }
}

