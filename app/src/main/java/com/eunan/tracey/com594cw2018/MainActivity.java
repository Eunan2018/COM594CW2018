package com.eunan.tracey.com594cw2018;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    LoginDataBaseAdapter loginDatabaseAdapter;
    ProgressBar progressBarLogin;

    private TextInputLayout loginName;

    private TextInputLayout loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBarLogin = (ProgressBar) findViewById(R.id.progressBar_login);
        progressBarLogin.setVisibility(View.INVISIBLE);
        loginDatabaseAdapter = new LoginDataBaseAdapter(this);
        loginDatabaseAdapter = loginDatabaseAdapter.open();


        loginName = findViewById (R.id.loginName);
        loginPassword = findViewById(R.id.loginPassword);
    }

    public void logIn(View v){

        try{
             String userName = loginName.getEditText().getText().toString();
             String password = loginPassword.getEditText().getText().toString();
            if(userName.equals("") || password.equals("")){
                Toast.makeText(this,"Fill All Fields",Toast.LENGTH_LONG).show();
            }
            // Fetch the Password from the database for the respective user name
            if(!password.equals("")){
                String storedPassword = loginDatabaseAdapter.getSingleEntry(userName);
                // Check if the stored password matches with the password entered by the user
                if(password.equals(storedPassword)){
                    progressBarLogin.setVisibility(View.VISIBLE);
                    Toast.makeText(this,"Congrats: Login Successful",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this,Weather.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(this,"No records match, please sign up",Toast.LENGTH_LONG).show();
                }

            }
        }catch(Exception e){
            Log.e("Error","error login");
        }
    }

    public void register(View v){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginDatabaseAdapter.close();
    }
}
