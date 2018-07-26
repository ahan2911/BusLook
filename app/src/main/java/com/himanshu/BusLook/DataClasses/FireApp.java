package com.himanshu.BusLook.DataClasses;

import android.app.Application;
import com.firebase.client.*;
import com.google.firebase.database.DataSnapshot;
import com.himanshu.BusLook.DataClasses.Bus;

public class FireApp extends Application {

    public boolean checkExistence(String busf, com.google.firebase.database.DataSnapshot dataSnapshot){
        boolean b = false;
        Bus bus1 = new Bus();
        if(dataSnapshot.hasChildren()) {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                bus1.setBusNumber(ds.getValue(Bus.class).getBusNumber());
                if(bus1.getBusNumber().equals(busf)){
                    b = true;
                }
            }
        }
        return b;
    }
    @Override
    public void onCreate() {
        super.onCreate();
      Firebase.setAndroidContext(this);
    }


}
