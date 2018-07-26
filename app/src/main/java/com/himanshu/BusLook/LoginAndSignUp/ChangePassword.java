package com.himanshu.BusLook.LoginAndSignUp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.himanshu.BusLook.ActivityClasses.UserActivity;
import com.himanshu.BusLook.R;

public class ChangePassword extends AppCompatActivity {
    EditText changeOldPassord, changeNewPassword, changeConfirmPassword;
    Button changePasswordButton;
    DatabaseReference databaseReference;
    String pss = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        final Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String bb = bundle.getString("userName1");
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://buslook-5966f.firebaseio.com/UserList/" + bb);
        changeOldPassord = findViewById(R.id.changeOldPassword);
        changeNewPassword = findViewById(R.id.changeNewPassword);
        changeConfirmPassword = findViewById(R.id.changeConfirmPassword);
        changePasswordButton = findViewById(R.id.changePasswordButton);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pss = dataSnapshot.child("password").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()) {
                    databaseReference.child("password").setValue(changeNewPassword.getText().toString());
                    Toast.makeText(ChangePassword.this, "Password has been changed", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private boolean validate() {
        boolean b = true;
        String oldPassword = changeOldPassord.getText().toString();
        String newPassword = changeNewPassword.getText().toString();
        String confirmPassword = changeConfirmPassword.getText().toString();
        if (oldPassword.isEmpty()) {
            changeOldPassord.setError("must have value");
            changeNewPassword.setText("");
            changeConfirmPassword.setText("");
            b = false;
        }
        else {
            changeOldPassord.setError(null);
        }
        if(!oldPassword.equals(pss)){
            changeOldPassord.setError("please enter correct password");
            changeNewPassword.setText("");
            changeConfirmPassword.setText("");
            b = false;
        }
        else {
            changeOldPassord.setError(null);
        }
        if (newPassword.isEmpty() || newPassword.length() < 4 || newPassword.length() > 10) {
            changeNewPassword.setError("between 4 and 10 alphanumeric characters");
            b = false;
        } else {
            changeNewPassword.setError(null);
        }
        if(!newPassword.equals(confirmPassword)){
            changeConfirmPassword.setError("password doesn't match");
            b = false;
        } else{
            changeConfirmPassword.setError(null);
        }
        return b;
    }
}
