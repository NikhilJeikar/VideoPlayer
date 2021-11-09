package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioFocusRequest;
import android.media.MediaFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.ArrayList;

public class VideoPlayerWindow extends AppCompatActivity{
    ImageButton BackButton;
    VideoView vw;
    ArrayList<Integer> videolist = new ArrayList<>();
    int currvideo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player_window_vertical);

        vw = (VideoView)findViewById(R.id.Screen);
        vw.setMediaController(new MediaController(this));
        vw.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

            }
        });

        BackButton = findViewById(R.id.BackButton);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        vw.setVideoURI(Uri.parse(getIntent().getStringExtra("Path")));
        vw.start();
    }

//    @Override
//    public void onCompletion(MediaPlayer mp) {
//        AlertDialog.Builder obj = new AlertDialog.Builder(this);
//        obj.setTitle("Playback Finished!");
//        obj.setIcon(R.mipmap.ic_launcher);
//        MyListener m = new MyListener();
//        obj.setPositiveButton("Replay", m);
//        obj.setNegativeButton("Next", m);
//        obj.setMessage("Want to replay or play next video?");
//        obj.show();
//    }
//
//
//    class MyListener implements DialogInterface.OnClickListener {
//        public void onClick(DialogInterface dialog, int which)
//        {
//            if (which == -1) {
//                vw.seekTo(0);
//                vw.start();
//            }
//            else {
//                ++currvideo;
//                if (currvideo == videolist.size())
//                    currvideo = 0;
//                setVideo(videolist.get(currvideo));
//            }
//        }
//    }

}