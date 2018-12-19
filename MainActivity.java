package com.example.nabilachowdhury.myfilemanager;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends Activity implements View.OnClickListener {

    Button picture;
    Button music;
    Button video;
    TextView imageCount;
    TextView musicCount;
    TextView videoCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        picture =(Button)findViewById(R.id.bt_fl_pic);
        music =(Button) findViewById(R.id.bt_fl_music);
        video=(Button)findViewById(R.id.bt_fl_video);
        imageCount=(TextView)findViewById(R.id.tx_image_count);
        musicCount=(TextView)findViewById(R.id.tx_music_count);
        videoCount=(TextView)findViewById(R.id.tx_video_count);
        picture.setOnClickListener(this);
        music.setOnClickListener(this);
        video.setOnClickListener(this);
        getPic();
       getMusic();
        getVideo();
    }

    @Override
    public void onClick(View v) {

        if(v==picture){
            Intent picIntent=new Intent(MainActivity.this,PictureScreen.class);
            startActivity(picIntent);
        }
        else if (v==music){
            Intent musicIntent=new Intent(MainActivity.this,MusicScreen.class);
            startActivity(musicIntent);
        }
        else if (v == video){
            Intent videoIntent=new Intent(MainActivity.this,VideoScreen.class);
            startActivity(videoIntent);

        }

    }

    public void getPic() {
        ContentResolver contentResolver = getContentResolver();
        Uri picUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor picCursor = contentResolver.query(picUri, null, null, null, null);
        if (picCursor != null && picCursor.moveToFirst()) {

            do {
                int test=picCursor.getCount();
                imageCount.setText("Pictures:"+test);
                } while (picCursor.moveToNext());
            }
            else
                imageCount.setText("No Image");

    }

    public void getMusic(){
        ContentResolver contentResolver=getContentResolver();
        Uri songUri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri,null,null,null,null);
            if(songCursor != null && songCursor.moveToFirst()){

            do {
                int test=songCursor.getCount();
                musicCount.setText("Musics:"+test);

                } while (songCursor.moveToNext());
            }
        else
            musicCount.setText("No Music");

            }

    public void getVideo() {

        ContentResolver contentResolver = getContentResolver();
        Uri videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor videoCursor = contentResolver.query(videoUri, null, null, null, null);
        if (videoCursor != null && videoCursor.moveToFirst()) {
            do {
                int test=videoCursor.getCount();
                videoCount.setText("Videos:"+test);
                } while (videoCursor.moveToNext());
            }
        else
            videoCount.setText("No Video");


    }


}
