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
import android.widget.TextView;

import com.google.firebase.database.*;
import com.himanshu.BusLook.R;
import com.himanshu.BusLook.DataClasses.User;

import java.util.Objects;

public class User_SignUp extends AppCompatActivity {
    public static ProgressDialog progressDialog = null;
    EditText userSignUpName, userMobile, userSignUpEmail, userSignUpPassword, userSignUpConfirm;
    Button userSignUpButton;
    TextView link_login;
    DatabaseReference mDataBaseRef;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.himanshu.BusLook.R.layout.activity_user_signup);
        Bundle bundle = getIntent().getExtras();
        userSignUpName = findViewById(com.himanshu.BusLook.R.id.userSignUpName);
        userMobile = findViewById(com.himanshu.BusLook.R.id.userMobile);
        userSignUpEmail = findViewById(com.himanshu.BusLook.R.id.userSignUpEmail);
        userSignUpPassword = findViewById(com.himanshu.BusLook.R.id.userSignUpPassword);
        userSignUpConfirm = findViewById(com.himanshu.BusLook.R.id.userSignUpConfirm);
        link_login = findViewById(com.himanshu.BusLook.R.id.link_login);

        assert bundle != null;
        if(!(Objects.requireNonNull(bundle.getString("KEY4"))).isEmpty())
            userSignUpEmail.setText(bundle.getString("KEY4"));

        userSignUpButton = findViewById(com.himanshu.BusLook.R.id.userSignUpButton);
        userSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
        link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(User_SignUp.this, LoginActivity.class).putExtra("KEY1", 2));
            }
        });
    }

    private void signUp() {
        String userName = userSignUpName.getText().toString();
        String mobile = userMobile.getText().toString();
        String email = userSignUpEmail.getText().toString();
        String pass = userSignUpPassword.getText().toString().trim();
        progressDialog = new ProgressDialog(User_SignUp.this, R.style.MyProgressDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        mDataBaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://buslook-5966f.firebaseio.com/UserList");
        if(!validate()){
            progressDialog.dismiss();
        }

        else{
                final User user = new User(email, userName, mobile, pass);
                mDataBaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!checkExistence(user.getEmail(), user.getMobile(), dataSnapshot)){
                            mDataBaseRef = mDataBaseRef.push();
                            user.setUserId(mDataBaseRef.getKey());
                            mDataBaseRef.setValue(user);
                            startActivity(new Intent(User_SignUp.this, LoginActivity.class).putExtra("KEY1", 2));
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
        String name = userSignUpName.getText().toString();
        String email = userSignUpEmail.getText().toString();
        String password = userSignUpPassword.getText().toString();
        String password1 = userSignUpConfirm.getText().toString();
        String mobile = userMobile.getText().toString();
        if (name.isEmpty() || name.length() < 3) {
            userSignUpName.setError("at least 3 characters");
            userSignUpPassword.setText("");
            userSignUpConfirm.setText("");
            valid = false;
        }
        else {
            userSignUpName.setError(null);
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userSignUpEmail.setError("enter a valid email address");
            valid = false;
            userSignUpPassword.setText("");
            userSignUpConfirm.setText("");
        }
        else {
            userSignUpEmail.setError(null);
        }
        if (password.isEmpty() || password.length() < 4 || password.length() > 10 || password1.isEmpty()) {
            userSignUpPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            userSignUpPassword.setError(null);
        }
        if(!password.equals(password1)){
            userSignUpConfirm.setError("password doesn't match");
            valid = false;
        } else{
            userSignUpConfirm.setError(null);
        }
        if(mobile.isEmpty() || mobile.length() != 10){
            userMobile.setError("must be a 10 digit number");
            valid = false;
            userSignUpPassword.setText("");
            userSignUpConfirm.setText("");
        } else{
            userMobile.setError(null);
        }
        return valid;
    }

    //to check whether email and mobile is already registered or not
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean checkExistence(String email, String mobile, DataSnapshot dataSnapshot){
        boolean b = false;
        User user1 = new User();
        if(dataSnapshot.hasChildren()) {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                user1.setEmail(Objects.requireNonNull(ds.getValue(User.class)).getEmail());
                user1.setMobile(Objects.requireNonNull(ds.getValue(User.class)).getMobile());
                if(user1.getEmail().equals(email)){
                    b = true;
                    userSignUpEmail.setError("Email Id already registered");
                    progressDialog.dismiss();
                    userSignUpPassword.setText("");
                    userSignUpConfirm.setText("");
                }
                if(user1.getMobile().equals(mobile)){
                    b = true;
                    userMobile.setError("Mobile Number already registered");
                    progressDialog.dismiss();
                    userSignUpPassword.setText("");
                    userSignUpConfirm.setText("");
                }
            }
        }
        return b;
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
