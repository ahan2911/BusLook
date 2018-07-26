package com.himanshu.BusLook.LoginAndSignUp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.*;
import com.himanshu.BusLook.DataClasses.Bus;
import com.himanshu.BusLook.ActivityClasses.BusActivity;
import com.himanshu.BusLook.ActivityClasses.MainActivity;
import com.himanshu.BusLook.R;
import com.himanshu.BusLook.DataClasses.User;
import com.himanshu.BusLook.ActivityClasses.UserActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    public static ProgressDialog progressDialog;
    EditText loginCredential1;
    TextInputEditText loginCredential;
    TextInputLayout loginCredentialLayout ;
    Bus bus;
    User user;
    int flag;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.himanshu.BusLook.R.layout.activity_login);
        Bundle bundle = getIntent().getExtras();
        loginCredential = findViewById(com.himanshu.BusLook.R.id.loginCredential);
        loginCredential1 = findViewById(com.himanshu.BusLook.R.id.loginCredential1);
        loginCredentialLayout = findViewById(com.himanshu.BusLook.R.id.loginCredentialLayout);
        assert bundle != null;
        flag = bundle.getInt("KEY1");
        if(flag==1){
            loginCredentialLayout.setHint("Bus Number");
        }
        Button button = findViewById(com.himanshu.BusLook.R.id.signInButton);
        TextView button1 = findViewById(com.himanshu.BusLook.R.id.signUpButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    private void signIn() {
        final String username1 = loginCredential.getText().toString();
        final String password = loginCredential1.getText().toString();
        loginCredentialLayout.setErrorEnabled(true);
        progressDialog = new ProgressDialog(LoginActivity.this, R.style.MyProgressDialog);
        progressDialog.setMessage("Authenticating....");
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if (TextUtils.isEmpty(username1)) {
            progressDialog.dismiss();
            Toast.makeText(LoginActivity.this, "Please enter a valid input", Toast.LENGTH_SHORT).show();
        } else {
            if (flag == 1) {
                final String username = username1.toUpperCase();
                mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://buslook-5966f.firebaseio.com/BusList");
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (checkExistence(username, dataSnapshot)) {
                            if (checkPassword(password)) {
                                Intent intent = new Intent(LoginActivity.this, BusActivity.class);
                                /*intent.putExtra("KEY2", bus.getOwnerName());*/
                                intent.putExtra("KEY3", bus.getBusNumber());
                                startActivity(intent);
                            } else {
                                progressDialog.dismiss();
                                loginCredential1.setError("password is wrong");
                                loginCredential1.setText("");
                            }
                        } else {
                            progressDialog.dismiss();
                            loginCredential1.setText("");
                            loginCredential.setError("You are not registered");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            else{
                mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://buslook-5966f.firebaseio.com/UserList");
                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(checkExistence1(username1, dataSnapshot)){
                            if(checkPassword1(password)){
                                mDatabaseReference.child(user.getUserId()).child("status").setValue(true);
                                startActivity(new Intent(LoginActivity.this, UserActivity.class).putExtra("UserName", user.getUserId()));

                            }
                            else{
                                progressDialog.dismiss();
                                loginCredential1.setError("password is wrong");
                                loginCredential1.setText("");
                            }
                        }
                        else{
                            progressDialog.dismiss();
                            loginCredential1.setText("");
                            loginCredential.setError("You are not registered");

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }
    }


    private void signUp(){
        if(flag==2) {
            Intent intent = new Intent(LoginActivity.this, User_SignUp.class);
            intent.putExtra("KEY4", loginCredential.getText().toString());
            startActivity(intent);
        }
        else if(flag==1){
            Intent intent = new Intent(LoginActivity.this, Bus_Signup.class);
            intent.putExtra("KEY5", loginCredential.getText().toString().toUpperCase());
            startActivity(intent);
        }
    }



    private boolean checkPassword1(String password) {
        boolean b = false;
        if(user.getPassword().equals(password)){
            b = true;
        }
        return b;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean checkExistence1(String userName, DataSnapshot dataSnapshot) {
        boolean b = false;
        User user1 = new User();
        if(dataSnapshot.hasChildren()){
            for(DataSnapshot ds: dataSnapshot.getChildren()){
                user1.setEmail(Objects.requireNonNull(ds.getValue(User.class)).getEmail());
                if(user1.getEmail().equals(userName)){
                    b = true;
                    user = ds.getValue(User.class);
                    break;
                }
            }
        }
        return b;
    }

    private boolean checkPassword(String password) {
        boolean b = false;
        if(bus.getPassword().equals(password)){
            b = true;
        }
        return b;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean checkExistence(String username, DataSnapshot dataSnapshot) {
        boolean b = false;
        Bus bus1 = new Bus();
        if(dataSnapshot.hasChildren()) {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                bus1.setBusNumber(Objects.requireNonNull(ds.getValue(Bus.class)).getBusNumber());
                if(bus1.getBusNumber().equals(username)){
                    b = true;
                    bus = ds.getValue(Bus.class);
                    break;
                }
            }
        }
        return b;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(User_SignUp.progressDialog != null){
            User_SignUp.progressDialog.dismiss();
            Toast.makeText(this, "Sign Up Successfully", Toast.LENGTH_SHORT).show();
            User_SignUp.progressDialog = null;
        }
        if(Bus_Signup.progressDialog != null){
            Bus_Signup.progressDialog.dismiss();
            Toast.makeText(this, "Sign Up Successfully", Toast.LENGTH_SHORT).show();
            Bus_Signup.progressDialog = null;
        }

    }
}
