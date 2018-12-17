package com.eunan.tracey.com594cw2018;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextInputLayout loginEmail;
    private TextInputLayout loginPassword;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    SignInButton signInButton;

    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);

        // firebase
        firebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        signInButton = findViewById(R.id.google_button);
        signInButton.setEnabled(false);
        radioButton = findViewById(R.id.googleRadio);
        View.OnClickListener first_radio_listener = new View.OnClickListener() {
            public void onClick(View v) {
                signInButton.setEnabled(true);
            }
        };
        radioButton.setOnClickListener(first_radio_listener);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setTitle("Login");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                progressDialog.setCancelable(false);
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, 101);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 101) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
      progressDialog.dismiss();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Successful Login", Toast.LENGTH_LONG).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), DisplayWeather.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error Login", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }


                    }
                });
    }


    public boolean logIn(View v) {

        NetworkInfo info = ((ConnectivityManager)
                getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null) {
            String email = loginEmail.getEditText().getText().toString();
            String password = loginPassword.getEditText().getText().toString();

            if (!validateEmail(email)) {
                loginEmail.setError("Not a valid email address!");
            } else if (!validatePassword(password)) {
                loginPassword.setError("Not a valid password!");
            } else {
                loginEmail.setErrorEnabled(false);
                loginPassword.setErrorEnabled(false);
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog = new ProgressDialog(MainActivity.this);
                        progressDialog.setMessage("Loading...");
                        progressDialog.setTitle("Login");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.show();
                        progressDialog.setCancelable(false);
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Successful Login", Toast.LENGTH_LONG).show();
                            finish();
                            Intent intent = new Intent(getApplicationContext(), DisplayWeather.class);
                            startActivity(intent);
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Error logging in", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
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
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

}
