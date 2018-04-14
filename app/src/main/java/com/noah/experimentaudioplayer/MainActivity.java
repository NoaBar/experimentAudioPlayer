package com.noah.experimentaudioplayer;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.sweet);

        Button pauseB = (Button) findViewById(R.id.pause);
        pauseB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });

        Button playB = (Button) findViewById(R.id.play);
        playB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
            }
        });

        Button skipB = (Button) findViewById(R.id.skip);
        skipB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int positionInSong = mediaPlayer.getCurrentPosition();
                mediaPlayer.seekTo(positionInSong + 10 * 1000);
            }
        });

        final SeekBar seekB = (SeekBar) findViewById(R.id.seekBar);
        seekB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int positionInSong = seekBar.getProgress();
                int durationOfSong = mediaPlayer.getDuration();
                mediaPlayer.seekTo((durationOfSong * positionInSong) / 100);
            }
        });

        final Handler mHandler = new Handler();
        //Make sure you update Seekbar on UI thread
        MainActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int positionInSong = mediaPlayer.getCurrentPosition();
                    int durationOfSong = mediaPlayer.getDuration();
                    int mCurrentPosition = positionInSong*100/durationOfSong;
                    seekB.setProgress(mCurrentPosition);
                }
                mHandler.postDelayed(this, 200);
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                Toast.makeText(MainActivity.this, "End of song", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
