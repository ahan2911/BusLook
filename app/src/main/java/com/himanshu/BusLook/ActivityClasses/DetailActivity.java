package com.himanshu.BusLook.ActivityClasses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.himanshu.BusLook.R;
import com.himanshu.BusLook.com.himanshu.BusLook.Direction.Route;
import com.himanshu.BusLook.Adapters.*;

public class DetailActivity extends AppCompatActivity {
    AdapterBuses2 adapterBuses2;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        Route route = (Route) bundle.getSerializable("RouteDetail");
        recyclerView = findViewById(R.id.recyclerViewDetail);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assert route != null;
        adapterBuses2 = new AdapterBuses2(route.getSteps(), this);
        recyclerView.setAdapter(adapterBuses2);
    }
}
