package com.himanshu.BusLook.LoginAndSignUp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.*;
import com.himanshu.BusLook.DataClasses.Bus;
import com.himanshu.BusLook.R;

import java.util.Objects;


public class Bus_Signup extends AppCompatActivity {
    public static ProgressDialog progressDialog = null;
    private EditText busNumberSignUp, engineNumber, chassisNumber, busSignUpPassword, busSignUpConfirmPassword;
    private DatabaseReference mDatabaseReference;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.himanshu.BusLook.R.layout.activity_bus_signup);
        Button busSignUpButton;
        Bundle bundle = getIntent().getExtras();
        busNumberSignUp = findViewById(com.himanshu.BusLook.R.id.busNumberSignUp);
        engineNumber = findViewById(com.himanshu.BusLook.R.id.engineNumber);
        chassisNumber = findViewById(com.himanshu.BusLook.R.id.chassisNumber);
        busSignUpPassword = findViewById(com.himanshu.BusLook.R.id.busSignUpPassword);
        busSignUpConfirmPassword = findViewById(com.himanshu.BusLook.R.id.busSignUpConfirmPassword);
        assert bundle != null;
        busNumberSignUp.setText(bundle.getString("KEY5"));
        busSignUpButton = findViewById(com.himanshu.BusLook.R.id.busSignUpButton);
        busSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpBus();
            }
        });
    }


    private void signUpBus() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://buslook-5966f.firebaseio.com/BusList");
        String engineNumber1 = engineNumber.getText().toString();
        String busNumber = busNumberSignUp.getText().toString();
        String chassisNumber1 = chassisNumber.getText().toString();
        String pass = busSignUpPassword.getText().toString();
        busNumber = busNumber.toUpperCase();
        progressDialog = new ProgressDialog(Bus_Signup.this, R.style.MyProgressDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if(!validate()){
            progressDialog.dismiss();
        }
        else{
                final Bus bus = new Bus(busNumber, engineNumber1, chassisNumber1, pass);
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!checkExistence(bus.getBusNumber(), dataSnapshot)){
                            mDatabaseReference.child(bus.getBusNumber()).setValue(bus);
                            startActivity(new Intent(Bus_Signup.this, LoginActivity.class).putExtra("KEY1", 1));
                        }
                        else{
                            progressDialog.dismiss();
                            busNumberSignUp.setError("Already Registered");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }

    private boolean validate() {
        boolean valid = true;
        String pass = busSignUpPassword.getText().toString();
        String pass1 = busSignUpConfirmPassword.getText().toString();
        String engineNumber1 = engineNumber.getText().toString();
        String busNumber = busNumberSignUp.getText().toString();
        String chassisNumber1 = chassisNumber.getText().toString();
        if(!pass.equals(pass1)){
            busSignUpConfirmPassword.setError("password doesn't match");
            valid = false;
        } else {
            busSignUpConfirmPassword.setError(null);
        }
        if (pass.isEmpty() || pass.length() < 4 || pass.length() > 10 || pass1.isEmpty()) {
            busSignUpPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            busSignUpPassword.setError(null);
        }
        if(engineNumber1.isEmpty() || busNumber.isEmpty() || chassisNumber1.isEmpty()){
            Toast.makeText(this, "Please Fill all fields", Toast.LENGTH_LONG).show();
            valid = false;
        }
        return valid;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean checkExistence(String busNumber, DataSnapshot dataSnapshot){
        boolean b = false;
        Bus bus1 = new Bus();
        if(dataSnapshot.hasChildren()) {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                String busNumber1 = (Objects.requireNonNull(ds.getValue(Bus.class))).getBusNumber();
                bus1.setBusNumber(busNumber1);
                if(bus1.getBusNumber().equals(busNumber)){
                    b = true;
                }
            }
        }
        return b;
    }

    public void againLogin(View view) {
        startActivity(new Intent(Bus_Signup.this, LoginActivity.class).putExtra("KEY1", 1));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
