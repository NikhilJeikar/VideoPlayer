package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.media.AudioFocusRequest;
import android.media.MediaFormat;
import android.media.MediaPlayer;
import android.media.SubtitleData;
import android.media.TimedText;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class VideoPlayerWindow extends AppCompatActivity implements SurfaceHolder.Callback{
    ImageButton BackButton;
    private MediaPlayer mp;
    private SurfaceView mPreview;
    private SurfaceHolder holder;
    boolean pausing = false;
    String Src = "";
    String SubtitleSrc = "";
    boolean IsSubtitle = false;

    private ImageButton Replay,Pause,Forward,Rewind,subtitle,add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player_window_vertical);

        Src = getIntent().getStringExtra("Path");

        SharedPreferences sf=getSharedPreferences("History", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=sf.edit();
        String h=sf.getString("yourHistory","");
        edit.putString("yourHistory",Src+"," + h);
        edit.apply();

        Replay = findViewById(R.id.replay);
        Pause = findViewById(R.id.PR);
        Forward = findViewById(R.id.forward);
        Rewind = findViewById(R.id.rewind);
        subtitle = findViewById(R.id.subtitle);
        add = findViewById(R.id.AddSubtitle);
        BackButton = findViewById(R.id.BackButton);

        Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pausing)
                {
                    mp.start();
                    Pause.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                }
                else
                {
                    mp.stop();
                    Pause.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                }
                pausing = !pausing;
            }
        });

        Replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.seekTo(0);
            }
        });

        Forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int duration = mp.getCurrentPosition() + 1000;
                if(mp.getDuration() < duration)
                {
                    duration = mp.getDuration();
                }
                mp.seekTo(duration);
            }
        });

        Rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int duration = mp.getCurrentPosition() - 1000;
                if(0 > duration)
                {
                    duration = 0;
                }
                mp.seekTo(duration);
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        subtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(IsSubtitle)
                {
                    try {
                        mp.addTimedTextSource(SubtitleSrc,MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    mp.deselectTrack(0);
                }

            }
        });

        try{
            Intent intent = getIntent();

            Uri fileuri = intent.getData();
            Src = fileuri.getPath();
        }catch(Exception ignored){}

        getWindow().setFormat(PixelFormat.UNKNOWN);
        mPreview = (SurfaceView)findViewById(R.id.Screen);
        holder = mPreview.getHolder();
        holder.setFixedSize(800, 480);
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mp = new MediaPlayer();
    }

    protected void onPause(){
        super.onPause();
        mp.release();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        mp.setDisplay(holder);
        play();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    void play(){
        try {
            mp.setDataSource(Src);
            mp.prepare();
        } catch (IllegalArgumentException | IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        mp.start();
    }
}