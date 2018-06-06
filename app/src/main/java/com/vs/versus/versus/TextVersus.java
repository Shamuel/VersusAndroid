package com.vs.versus.versus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class TextVersus extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private EditText Comment,VersusText,VersusText2,VersusText3,VersusText4;
    private ImageView add,add2;
    private Button SaveVersus;

    private static final String REQUIRED = "Required";
    private static final String TAG = "TextVersusActivity";
    //variable for validations
    private boolean empty=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_versus);

       Comment = findViewById(R.id.comment);
       VersusText = findViewById(R.id.versusText);
       VersusText2 = findViewById(R.id.versusText2);
       VersusText3 = findViewById(R.id.versusText3);
       VersusText4 = findViewById(R.id.versusText4);
       add = findViewById(R.id.addtext);
       add2 = findViewById(R.id.addtext2);
       SaveVersus = findViewById(R.id.Save);

// [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
// [END initialize_database_ref]
        mAuth = FirebaseAuth.getInstance();

        //UI disappearing objects and appearing
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add.setVisibility(View.INVISIBLE);
                VersusText3.setVisibility(View.VISIBLE);
                add2.setVisibility(View.VISIBLE);
            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add2.setVisibility(View.INVISIBLE);
                VersusText4.setVisibility(View.VISIBLE);
            }
        });
        //Save Button
        SaveVersus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SavingVersus();

            }
        });

    }

    private void SavingVersus() {

        //Getting Objects from outside to can use them here
        final String comment = Comment.getText().toString();
        final String versus = VersusText.getText().toString();
        final String versus2 = VersusText2.getText().toString();
        final String versus3 = VersusText3.getText().toString();
        final String versus4 = VersusText4.getText().toString();

        // Validations
        if (TextUtils.isEmpty(versus)||TextUtils.isEmpty(versus2)) {

            empty=true;
            Toast.makeText(this, "Writeeeeeeeeeeeeeee", Toast.LENGTH_SHORT).show();

            //When is only 2 texts
        }else if(!TextUtils.isEmpty(versus)&&!TextUtils.isEmpty(versus2)&&TextUtils.isEmpty(versus3)
                &&TextUtils.isEmpty(versus4)) {
            empty = false;

            // Disable button so there are no multi-posts
            //setEditingEnabled(false);
            Toast.makeText(this, "Publishing...", Toast.LENGTH_SHORT).show();

            // [START single_value_read]
            final String userId = mAuth.getUid();
            final FirebaseUser User = mAuth.getCurrentUser();
            mDatabase.child("").child(userId).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            // [START_EXCLUDE]
                            if (User == null) {
                                // User is null, error out
                                Log.e(TAG, "User " + userId + " is unexpectedly null");
                                Toast.makeText(TextVersus.this,
                                        "Error: could not fetch user.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                // Write new post
                                SetNewTextVersusFor2Texts(userId, comment, versus, versus2);
                            }

                            // Finish this Activity, back to the stream
                            //setEditingEnabled(true);
                            finish();
                            // [END_EXCLUDE]
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                            // [START_EXCLUDE]
                            //setEditingEnabled(true);
                            // [END_EXCLUDE]
                        }
                    });
        }

        //When is only 3 texts
        else if(!TextUtils.isEmpty(versus)&&!TextUtils.isEmpty(versus2)&&!TextUtils.isEmpty(versus3)
                &&TextUtils.isEmpty(versus4)){

            empty = false;

            // Disable button so there are no multi-posts
            //setEditingEnabled(false);
            Toast.makeText(this, "Publishing...", Toast.LENGTH_SHORT).show();

            // [START single_value_read]
            final String userId = mAuth.getUid();
            final FirebaseUser User = mAuth.getCurrentUser();
            mDatabase.child("").child(userId).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            // [START_EXCLUDE]
                            if (User == null) {
                                // User is null, error out
                                Log.e(TAG, "User " + userId + " is unexpectedly null");
                                Toast.makeText(TextVersus.this,
                                        "Error: could not fetch user.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                // Write new post
                                SetNewTextVersusFor3Texts(userId, comment, versus, versus2,versus3);
                            }

                            // Finish this Activity, back to the stream
                            //setEditingEnabled(true);
                            finish();
                            // [END_EXCLUDE]
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                            // [START_EXCLUDE]
                            //setEditingEnabled(true);
                            // [END_EXCLUDE]
                        }
                    });

        }

        //When is 4 texts
        else if(!TextUtils.isEmpty(versus)&&!TextUtils.isEmpty(versus2)&&!TextUtils.isEmpty(versus3)
                &&!TextUtils.isEmpty(versus4)){

            empty = false;

            // Disable button so there are no multi-posts
            //setEditingEnabled(false);
            Toast.makeText(this, "Publishing...", Toast.LENGTH_SHORT).show();

            // [START single_value_read]
            final String userId = mAuth.getUid();
            final FirebaseUser User = mAuth.getCurrentUser();
            mDatabase.child("").child(userId).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            // [START_EXCLUDE]
                            if (User == null) {
                                // User is null, error out
                                Log.e(TAG, "User " + userId + " is unexpectedly null");
                                Toast.makeText(TextVersus.this,
                                        "Error: could not fetch user.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                // Write new post
                                SetNewTextVersusFor4Texts(userId, comment, versus, versus2,versus3,versus4);
                            }

                            // Finish this Activity, back to the stream
                            //setEditingEnabled(true);
                            finish();
                            // [END_EXCLUDE]
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                            // [START_EXCLUDE]
                            //setEditingEnabled(true);
                            // [END_EXCLUDE]
                        }
                    });

        }

        // [END single_value_read]
    }

   /* private void setEditingEnabled(boolean enabled) {
        Comment.setEnabled(enabled);
        SaveVersus.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }*/

   //Method to save Texts in Firebase Database
    private void SetNewTextVersusFor2Texts(String userId, String comment, String versus,
    String versus2) {

        // Create new post at /user-versustext/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("").push().getKey();
        HashMap versustextValue = new HashMap<>();

        versustextValue.put("id",userId);
        versustextValue.put("comment",comment);
        versustextValue.put("versus",versus);
        versustextValue.put("versus2",versus2);

        //IF  validations dont match
        if(!empty)
        mDatabase.child("TextVersus").child(key).updateChildren(versustextValue);
    }
    private void SetNewTextVersusFor3Texts(String userId, String comment, String versus,
                                           String versus2,String versus3) {

        // Create new post at /user-versustext/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("").push().getKey();
        HashMap versustextValue = new HashMap<>();

        versustextValue.put("id",userId);
        versustextValue.put("comment",comment);
        versustextValue.put("versus",versus);
        versustextValue.put("versus2",versus2);
        versustextValue.put("versus3",versus3);

        if(!empty)
            mDatabase.child("TextVersus").child(key).updateChildren(versustextValue);
    }
    private void SetNewTextVersusFor4Texts(String userId, String comment, String versus,
                                           String versus2,String versus3,String versus4) {

        // Create new post at /user-versustext/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("").push().getKey();
        HashMap versustextValue = new HashMap<>();

        versustextValue.put("id",userId);
        versustextValue.put("comment",comment);
        versustextValue.put("versus",versus);
        versustextValue.put("versus2",versus2);
        versustextValue.put("versus3",versus3);
        versustextValue.put("versus4",versus4);

        if(!empty)
            mDatabase.child("TextVersus").child(key).updateChildren(versustextValue);
    }
    // [END write_fan_out]
}


