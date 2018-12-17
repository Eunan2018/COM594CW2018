package com.eunan.tracey.com594cw2018;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    private static final String TAG = "Register";
    private TextInputLayout userRegisterEmail;
    private TextInputLayout userRegisterPassword;

    // Create firebase reference
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userRegisterEmail = findViewById(R.id.registerEmail);
        userRegisterPassword = findViewById(R.id.registerPassword);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public boolean register_OK(View v) {
        Log.d(TAG, "register_OK: starts");

        String password = userRegisterPassword.getEditText().getText().toString();
        String email = userRegisterEmail.getEditText().getText().toString();

        if (!validateEmail(email)) {
            userRegisterEmail.setError("Not a valid email address!");
        } else if (!validatePassword(password)) {
            userRegisterPassword.setError("Not a valid password!");
        } else {
            userRegisterEmail.setErrorEnabled(false);
            userRegisterPassword.setErrorEnabled(false);
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(Register.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Successfully Registered, ", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error, could not create user", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

        Log.d(TAG, "register_OK: ends" + email + " " + " " + password);
        return true;
    }

    // Check length of password
    public boolean validatePassword(String password) {
        Log.d(TAG, "validatePassword: starts" + password);
        return password.length() > 5;
    }

    // Check email format
    public boolean validateEmail(String email) {
        Log.d(TAG, "validateEmail: starts" + email);
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }



    public void register(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
