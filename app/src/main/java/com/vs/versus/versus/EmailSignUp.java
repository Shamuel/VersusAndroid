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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class EmailSignUp extends AppCompatActivity {

    FirebaseAuth mAuth;

    Button SignUp;
    EditText Email,Password,ConfirmPassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_sign_up);

        mAuth = FirebaseAuth.getInstance();

        SignUp = (Button) findViewById(R.id.SignUp);
        Email = (EditText) findViewById(R.id.Email);
        Password = (EditText) findViewById(R.id.Password);
        ConfirmPassWord = (EditText) findViewById(R.id.ConfirmPassword);

        //The Sign Up Button
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
                /*SignUpLogin.Anonimus=false;*/
            }
        });

    }

    void GotoPrincipalPage(){
        Intent PrincipalPageintent = new Intent(this, PrincipalPage.class);
        PrincipalPageintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(PrincipalPageintent);
        finish();
    }
        // Method that Create a Authentication in the MBaaS for a User
    private void CreateAccount() {

        String email = Email.getText().toString();
        String password = Password.getText().toString();
        String confirmpassword = ConfirmPassWord.getText().toString();

        if(TextUtils.isEmpty(email)){

            Toast.makeText(this, "Please write your Email", Toast.LENGTH_SHORT).show();

        }

        else if (TextUtils.isEmpty(password)){

            Toast.makeText(this, "Please write your password", Toast.LENGTH_SHORT).show();

        }

        else if (TextUtils.isEmpty(confirmpassword)){

            Toast.makeText(this, "Please confirm your password", Toast.LENGTH_SHORT).show();

        }

        else if (!password.equals(confirmpassword)){

            Toast.makeText(this, "Passwords aren't Similar", Toast.LENGTH_SHORT).show();

        }
        else {

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(EmailSignUp.this, "Your Account has been Created Corecctly", Toast.LENGTH_SHORT).show();
                                GotoPrincipalPage();
                            }
                            else {
                                String ErrorMessage = task.getException().toString();
                                Toast.makeText(EmailSignUp.this, "Error: "+ErrorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }
}
