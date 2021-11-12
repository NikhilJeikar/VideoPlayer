package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class Register extends AppCompatActivity {
    EditText fn,ln,mail,mobile,password,C_password;
    Button Reg;
    SQLiteDatabase db;
    ImageButton BackButton;
    TextView AppBarText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fn = findViewById(R.id.firstName);
        ln = findViewById(R.id.lastName);
        mail = findViewById(R.id.Email);
        mobile = findViewById(R.id.PhoneNumber);
        password = findViewById(R.id.Password);
        C_password = findViewById(R.id.ConfirmPassword);

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
        db.execSQL("CREATE TABLE IF NOT EXISTS USERS(FirstName VARCHAR,LastName VARCHAR,Email VARCHAR,MobileNumber INTEGER,Password VARCHAR);");
        Reg = findViewById(R.id.Register);
        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fn.getText().toString().trim().length() == 0 || ln.getText().toString().trim().length() == 0 || mail.getText().toString().trim().length() == 0 || password.getText().toString().trim().length() == 0 || mobile.getText().toString().trim().length() == 0 || C_password.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Please enter all values");
                }
                if (C_password.getText().toString().trim().equals(password.getText().toString().trim()) == false) {
                    showMessage("Error", "Password not confirmed");
                }
                else {
                    // Inserting record
                    db.execSQL("INSERT INTO USERS VALUES('" + fn.getText() + "','" + ln.getText() + "','" + mail.getText() + "','" + mobile.getText() + "','" + password.getText() + "');");
                    Intent i = new Intent(getApplicationContext(), Login.class);
                    startActivity(i);
                }
            }
        });


    }
    public void showMessage(String title, String message){
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }
}