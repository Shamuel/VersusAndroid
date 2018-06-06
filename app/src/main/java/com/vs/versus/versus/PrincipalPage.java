package com.vs.versus.versus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PrincipalPage extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference UserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_page);
        mAuth = FirebaseAuth.getInstance();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Checking if User exist in database
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser==null && SignUpLogin.Anonimus==false){
            GotoDataForSignUpPage();
        }
        else if(currentUser==null && SignUpLogin.Anonimus==true) {
            //GotoPrincipalPage();
        }
        else {
            CheckUserExistance();
        }
    }
    void GotoDataForSignUpPage(){
        Intent DataForSignUpPageintent = new Intent(this, DataForSignup.class);
        DataForSignUpPageintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(DataForSignUpPageintent);
        finish();
    }

    void GotoPrincipalPage(){
        Intent PrincipalPageintent = new Intent(this, PrincipalPage.class);
        PrincipalPageintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(PrincipalPageintent);
        finish();
    }

    //Checking if the User Exist in the Database X2
    private void CheckUserExistance() {

        final String userID = mAuth.getCurrentUser().getUid();
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!dataSnapshot.hasChild(userID) && SignUpLogin.Anonimus==false){
                    GotoDataForSignUpPage();
                } else if(!dataSnapshot.hasChild(userID) && SignUpLogin.Anonimus==true){
                    GotoDataForSignUpPage();
                }
                else {
                    //GotoPrincipalPage();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void OpenActivityBefore(View view)
    {
        mAuth.signOut();
        Intent LoginPageintent = new Intent(this, SignUpLogin.class);
        LoginPageintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(LoginPageintent);
        finish();
    }

    public void CreateVersus(View view)
    {
        //mAuth.signOut();
        Intent CreatingIntent = new Intent(this, CreatingVersus.class);
        CreatingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(CreatingIntent);
        finish();
    }

}
