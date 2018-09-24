package com.aperezf.soundplay;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private List<MediaData> mediaDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        IndexFastScrollRecyclerView rvListMedias = findViewById(R.id.fast_scroller_recycler);
        rvListMedias.setIndexbarMargin(0);
        rvListMedias.setIndexBarColor(R.color.colorPrimary);
        rvListMedias.setIndexBarCornerRadius(0);
        rvListMedias.setIndexBarTransparentValue(1);

        rvListMedias.setHasFixedSize(true);
        rvListMedias.setLayoutManager(new LinearLayoutManager(this));
        getMusic();
        ListMediasAdapter adapter = new ListMediasAdapter(mediaDataList, new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Intent intent = new Intent(MainActivity.this, MusicPlayerActivity.class);
                intent.putExtra("media", mediaDataList.get(pos).getPathFile());
                startActivity(intent);
            }
        });

        adapter.notifyDataSetChanged();

        rvListMedias.setAdapter(adapter);


    }

    private void getMusic(){
        mediaDataList = new ArrayList<>();
        ContentResolver cr = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        String[] projection = {MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST};
        String sortOrder = "lower("+MediaStore.Audio.Media.TITLE + ") ASC";

        Cursor songCursor = cr.query(songUri,projection,selection,null,sortOrder);

        if (songCursor != null && songCursor.moveToFirst()){
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int pathFile = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            do {

                String title = songCursor.getString(songTitle);
                String artist = songCursor.getString(songArtist);
                String path = songCursor.getString(pathFile);
                mediaDataList.add(new MediaData(path,title,artist));

            } while (songCursor.moveToNext());
            songCursor.close();
        }

    }

}
