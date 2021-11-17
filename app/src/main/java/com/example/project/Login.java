package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Ref;

public class Login extends AppCompatActivity {
    Button Register;
    TextView FP;
    Button Login;
    EditText mail,password;
    SQLiteDatabase db;
    CheckBox cb;
    ImageButton BackButton;
    TextView AppBarText;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Register = findViewById(R.id.SignUp);
        Login = findViewById(R.id.login);
        mail = findViewById(R.id.email);
        cb = findViewById(R.id.rememberMe);
        password = findViewById(R.id.password);

        BackButton = findViewById(R.id.BackButton);
        AppBarText = findViewById(R.id.AppBarText);

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        AppBarText.setText(R.string.app_name);

        db = openOrCreateDatabase("USERS_DB", Context.MODE_PRIVATE, null);
        FP = findViewById(R.id.forgotPass);
        FP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(),ForgotPassword.class);
                startActivity(i1);
            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sf=getSharedPreferences("Credentials", Context.MODE_PRIVATE);
                String p=sf.getString("UserName","");
                String q=sf.getString("Password","");
                mail.setText(p);
                password.setText(q);
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Register.class);
                startActivity(i);
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mail.getText().toString().trim().length() == 0 || password.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Please enter all values");
                }
                Cursor c = db.rawQuery("SELECT * FROM USERS WHERE Email ='" + mail.getText() + "'", null);
                if (c.moveToFirst()) {
                    String mailID = c.getString(2);
                    String pass = c.getString(4);
                    if((!mail.getText().toString().equals(mailID)) || (!password.getText().toString().equals(pass))){
                        showMessage("Error", "Invalid Credentials");
                    }
                    else if(mail.getText().toString().equals(mailID) && password.getText().toString().equals(pass)){
                        if(cb.isChecked()){
                            SharedPreferences sf=getSharedPreferences("Credentials", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit=sf.edit();
                            edit.putString("UserName",mail.getText().toString());
                            edit.putString("Password",password.getText().toString());
                            edit.commit();
                        }
                        SharedPreferences sfLogin=getSharedPreferences("LoginState", Context.MODE_PRIVATE);
                        SharedPreferences.Editor et=sfLogin.edit();
                        et.putString("LoginState","1");
                        et.commit();
                        Intent i=new Intent(getApplicationContext(),MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }
                }
                else {
                    showMessage("Error", "No such email");
                }

            }
        });

    }
    public void showMessage(String title, String message){
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }
}