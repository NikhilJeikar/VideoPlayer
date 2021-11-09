package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.project.Adapter.RecyclerAdapter;
import com.example.project.Holder.FileHolder;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION = 101;
    private final String[] Permissions = {Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private ImageButton BackButton,QrButton;
    private TextView AppBarText;
    private RecyclerAdapter Adapter;
    private RecyclerView gridView;
    private ImageButton User;
    ArrayList<File> Final = new ArrayList<File>();


    private String[] PendingPermission()
    {
        ArrayList<String> list = new ArrayList<>();
        for(String Permission:Permissions)
        {
            if(ContextCompat.checkSelfPermission(MainActivity.this, Permission) != PackageManager.PERMISSION_GRANTED)
                list.add(Permission);
        }

        String[] str = new String[list.size()];
        for(int i = 0;i<list.size();i++)
        {
            str[i] = list.get(i);
        }
        return str;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Permissions
        String[] Permissions = PendingPermission();
        if(Permissions.length > 0)
        {
            ActivityCompat.requestPermissions(MainActivity.this,Permissions,PERMISSION);
        }

        PendingPermission();

        // AppBar
        BackButton = findViewById(R.id.BackButton);
        QrButton = findViewById(R.id.QRScanButton);
        AppBarText = findViewById(R.id.AppBarText);
        User = findViewById(R.id.user);                                                                                                                                                            final PopupMenu dropDownMenu = new PopupMenu(getApplicationContext(), User);

        final Menu menu = dropDownMenu.getMenu();
        menu.add(0, 0, 0, "Login");
        menu.add(0, 1, 0, "History");

        dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 0:
                        Intent iLogin = new Intent(getApplicationContext(),Login.class);
                        startActivity(iLogin);
                        return true;
                    case 1:
                        Intent iHistory = new Intent(getApplicationContext(),History.class);
                        startActivity(iHistory);
                        return true;
                }
                return false;
            }
        });

        User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownMenu.show();
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        AppBarText.setText(R.string.app_name);
        QrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),QR_Scanner.class);
                startActivity(intent);
            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        Adapter = new RecyclerAdapter(getApplicationContext());
        Adapter.setDisplayMetrics(displayMetrics);
        gridView = findViewById(R.id.Grid);
        gridView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        gridView.setAdapter(Adapter);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                File directory = Environment.getExternalStorageDirectory();
                LoadFiles(directory);
            }
        };
        runnable.run();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        File directory = Environment.getExternalStorageDirectory();
        LoadFiles(directory);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void LoadFiles(File dir) {
        File[] listFile = dir.listFiles();
        if (listFile != null) {
            for (File file : listFile) {
                if (file.isDirectory()) {
                    LoadFiles(file);
                } else {
                    if(file.getName().endsWith(".mp4")||file.getName().endsWith(".MP4"))
                    {
                        Adapter.Add(new FileHolder(file,FileHolder.VIDEO));
                        Log.d("Data", file.getName());
                        Adapter.notifyDataSetChanged();
                    }
                    else if(file.getName().endsWith(".mp3")||file.getName().endsWith(".MP3"))
                    {
                        Adapter.Add(new FileHolder(file,FileHolder.AUDIO));
                        Log.d("Data", file.getName());
                    }

                }
            }
        }
    }

}