package com.eunan.tracey.com594cw2018;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    LoginDataBaseAdapter loginDatabaseAdapter;
    ProgressBar progressBarReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Get instance of database adapter
        progressBarReg = findViewById(R.id.progressBar);
        loginDatabaseAdapter = new LoginDataBaseAdapter(this);
        loginDatabaseAdapter =loginDatabaseAdapter.open();
        progressBarReg.setVisibility(View.INVISIBLE);
    }

    public void register_OK(View v){
        String userName = ((EditText)findViewById(R.id.editText_r_username)).getText().toString();
        String email = ((EditText)findViewById(R.id.editText_r_email)).getText().toString();
        String password = ((EditText)findViewById(R.id.editText_r_password)).getText().toString();
        String m = ((RadioButton) findViewById(R.id.radioButton_m)).getText().toString();
        String f = ((RadioButton) findViewById(R.id.radioButton_f)).getText().toString();

        if(userName.equals("") || email.equals("") || password.equals("")|| m.equals("")){
            Toast.makeText(Register.this,"Fill Alll Fields",Toast.LENGTH_LONG).show();
            return;
        }else{
            progressBarReg.setVisibility(View.VISIBLE);
            // Save data in the database
            loginDatabaseAdapter.insertEntry(userName,email,password,m);
            Toast.makeText(getApplicationContext(),"Your Account is Successfully Created. you can Sign In now",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Register.this,MainActivity.class);
            startActivity(intent);
        }
    }

    public void register(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginDatabaseAdapter.close();
    }


}
