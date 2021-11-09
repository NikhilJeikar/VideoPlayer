package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ForgotPassword extends AppCompatActivity {
    EditText mail,new_password;
    Button update;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        update = findViewById(R.id.update);
        mail = findViewById(R.id.email);
        new_password = findViewById(R.id.NPassword);
        db = openOrCreateDatabase("USERS_DB", Context.MODE_PRIVATE, null);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mail.getText().toString().trim().length() == 0 ||  new_password.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Please enter all values");
                }
                Cursor c = db.rawQuery("SELECT * FROM USERS  WHERE Email ='" + mail.getText() + "'", null);
                if (c.moveToFirst()) {
                    String mailID = c.getString(2);
                    String to=mail.getText().toString();
                    String subject="D Video Player Solutions";
                    String message="Your password is updated successfully.Any inconvenience mail us through 'DPlayer@gmail.com'";

                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                    email.putExtra(Intent.EXTRA_SUBJECT, subject);
                    email.putExtra(Intent.EXTRA_TEXT, message);

                    //need this to prompts email client only
                    email.setType("message/rfc822");
                    startActivity(Intent.createChooser(email, "Choose an Email client :"));
                    Intent i = new Intent(getApplicationContext(),Login.class);
                    startActivity(i);

                }
                else {
                    showMessage("Error", "No such email");
                }
            }
        });
    }

    public void showMessage(String title, String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }
}