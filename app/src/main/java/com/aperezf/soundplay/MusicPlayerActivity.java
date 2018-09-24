package com.aperezf.soundplay;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;


public class MusicPlayerActivity extends AppCompatActivity {

    public static final String TAG = "MusicPlayerActivity";

    private ImageButton btnPlayPause;
    private ImageButton btnPrev;
    private ImageButton btnNext;
    private SeekBar sbMedia;

    private Handler mSeekbarUpdateHandler = new Handler();
    private Runnable mUpdateSeekbar = new Runnable() {
        @Override
        public void run() {
            sbMedia.setProgress(player.getCurrentPosition());
            mSeekbarUpdateHandler.postDelayed(this, 50);
        }
    };

    private MusicPlayerService player;
    private boolean serviceBound = false;

    private View.OnClickListener onPlayPauseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (player.isPlaying()){
                player.resumeMedia();
                btnPlayPause.setImageResource(android.R.drawable.ic_media_pause);
            }
            else{
                player.pauseMedia();
                btnPlayPause.setImageResource(android.R.drawable.ic_media_play);
            }
        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicPlayerService.LocalBinder binder = (MusicPlayerService.LocalBinder) iBinder;
            player = binder.getService();
            initSeekBarMedia();
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            serviceBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        btnPlayPause = findViewById(R.id.btnPlayPause);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        sbMedia = findViewById(R.id.sbMedia);

        btnPlayPause.setOnClickListener(onPlayPauseListener);

        String media = getIntent().getExtras().getString("media");
        Log.i(TAG, media);
        playAudio(media);
    }

    private void playAudio(String media) {
        //Check is service is active
        if (!serviceBound) {
            Intent playerIntent = new Intent(this, MusicPlayerService.class);
            playerIntent.putExtra("media", media);
            startService(playerIntent);
            bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        } else {
            //Service is active
            //Send media with BroadcastReceiver
        }
    }

    public void initSeekBarMedia(){
        sbMedia.setMax(player.getDurationCurrentMedia());

        mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);

    }
}
