package com.example.nabilachowdhury.myfilemanager;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

public class MusicScreen extends Activity implements AdapterView.OnItemClickListener {
    private static final int MUSIC_GALLERY_REQUEST = 30;
    public MyAdapter musicAdapter;
    public ListView musicListView;
    ArrayList<String> musicFilename;
    ArrayList<Bitmap>musicFileImage;
    Bitmap bitmap;
    EditText rename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_screen);
        Intent intent =getIntent();
        musicFilename=new ArrayList<String>();
        musicFileImage=new ArrayList<Bitmap>();
        getMusic();
        musicListView=findViewById(R.id.lv_music_list);
        musicAdapter=new MyAdapter(this,musicFilename);
        musicListView.setAdapter(musicAdapter);
        musicListView.setOnItemClickListener(this);
        musicAdapter.notifyDataSetChanged();


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent picIntent = new Intent(Intent.ACTION_VIEW);
        Uri data=Uri.parse("file://"+musicFilename.get(position));
        picIntent.setDataAndType(data,"audio/*");
        startActivityForResult(picIntent, MUSIC_GALLERY_REQUEST);

    }
    public  void getMusic(){

        ContentResolver contentResolver=getContentResolver();
        Uri songUri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri,null,null,null,null);
        if(songCursor != null && songCursor.moveToFirst()){
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            do {
                String currentTitle=songCursor.getString(songTitle);
                musicFilename.add(currentTitle);
//                bitmap = BitmapFactory.decodeFile(currentTitle);
//                musicFileImage.add(bitmap);

            } while (songCursor.moveToNext());

        }


    }


}

