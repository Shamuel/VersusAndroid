package com.vs.versus.versus;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DataForSignup extends AppCompatActivity {

    EditText Username,Country;
    Button Save;

    FirebaseAuth mAuth;
    DatabaseReference UserRef;
    String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_for_signup);
        Username = (EditText) findViewById(R.id.Username);
        Country = (EditText) findViewById(R.id.Country);
        Save = (Button) findViewById(R.id.Save);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveUserData();
            }
        });
    }
    void GotoPrincipalPage(){
        Intent PrincipalPageintent = new Intent(this, PrincipalPage.class);
        PrincipalPageintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(PrincipalPageintent);
        finish();
        //SignUpLogin.Anonimus=false;
    }

    //Saving Users Data
    private void SaveUserData() {
        String username = Username.getText().toString();
        String country = Country.getText().toString();

        if(TextUtils.isEmpty(username)){

            Toast.makeText(this, "Please Create your Username", Toast.LENGTH_SHORT).show();

        }

        else if (TextUtils.isEmpty(country)){

            Toast.makeText(this, "Please Select your Country", Toast.LENGTH_SHORT).show();

        }
        else {

            HashMap UsersDataMap = new HashMap();
            UsersDataMap.put("Username",username);
            UsersDataMap.put("Country",country);

            UserRef.updateChildren(UsersDataMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        Toast.makeText(DataForSignup.this, "Data Save Correctly", Toast.LENGTH_LONG).show();
                        GotoPrincipalPage();
                    }
                    else {
                        String message = task.getException().getMessage();
                        Toast.makeText(DataForSignup.this, "Error: "+message, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }
}
