package com.example.nabilachowdhury.myfilemanager;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class VideoScreen extends Activity implements AdapterView.OnItemClickListener {

    private static final int VIDEO_GALLERY_REQUEST = 50;
    public MyAdapter videoAdapter;
    public ListView videoListView;
    ArrayList<String> videoFilename;
    ArrayList<Bitmap> videoFileImage;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_screen);
        Intent intent = getIntent();
        videoFilename=new ArrayList<String>();
        videoFileImage=new ArrayList<Bitmap>();
        getVideo();
        videoListView=findViewById(R.id.lv_video_list);
        videoAdapter=new MyAdapter(this,videoFilename);
        videoListView.setAdapter(videoAdapter);
        videoListView.setOnItemClickListener(this);
        videoAdapter.notifyDataSetChanged();


    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent picIntent = new Intent(Intent.ACTION_VIEW);
        Uri data=Uri.parse("file://"+videoFilename.get(position));
        picIntent.setDataAndType(data,"video/*");
        startActivityForResult(picIntent, VIDEO_GALLERY_REQUEST);
    }

    public void getVideo() {

        ContentResolver contentResolver = getContentResolver();
        Uri videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor videoCursor = contentResolver.query(videoUri, null, null, null, null);
        if (videoCursor != null && videoCursor.moveToFirst()) {
            int songTitle = videoCursor.getColumnIndex(MediaStore.Video.Media.DATA);
            do {
                String currentTitle = videoCursor.getString(songTitle);
                videoFilename.add(currentTitle);
//                bitmap = BitmapFactory.decodeFile(currentTitle);
//                videoFileImage.add(bitmap);
            } while (videoCursor.moveToNext());

        }


    }
}
