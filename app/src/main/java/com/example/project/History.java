package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    ListView listView;
    SQLiteDatabase db;
    ImageButton BackButton;
    TextView AppBarText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        BackButton = findViewById(R.id.BackButton);
        AppBarText = findViewById(R.id.AppBarText);

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        AppBarText.setText(R.string.app_name);

        listView = findViewById(R.id.lv_history);
        db = openOrCreateDatabase("USERS_DB", Context.MODE_PRIVATE, null);
        String ml = "";
        ml = getIntent().getStringExtra("mailid");
        Cursor c = db.rawQuery("SELECT * FROM USERHISTORY WHERE Email ='" + ml + "'", null);
        List<String> str = new ArrayList<>();
        if (c.moveToFirst()) {
            Log.d("Datablah ",c.getString(0) + " " + c.getString(1));
            String mailID = c.getString(0);
            String history = c.getString(1);
            if (ml.trim().equals(mailID)) {
                str.add(history);
            }
        }
        for(String st: str)
        {
            Log.d("Datablah1",st);
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,str);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),VideoPlayerWindow.class);
                intent.putExtra("Path", str.get(i));
                startActivity(intent);
            }
        });

    }

}