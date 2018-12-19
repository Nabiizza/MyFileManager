package com.example.nabilachowdhury.myfilemanager;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

public class PictureScreen extends Activity implements AdapterView.OnItemClickListener {
    private static final int IMAGE_GALLERY_REQUEST = 10;
    public MyAdapter picAdapter;
    public ListView picListView;
    private ArrayList<String> picFilename;
    private ArrayList<Bitmap> picFileImage;
    EditText rename;
    //ArrayList<Uri>fileUri;
    Bitmap bitmap;

    //Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_screen);
        picFilename = new ArrayList<String>();
        Intent intent = getIntent();
        getPic();
        picListView = findViewById(R.id.lv_pic_list);
        picAdapter = new MyAdapter(this, picFilename);
        picListView.setAdapter(picAdapter);
        picListView.setOnItemClickListener(this);
        picAdapter.notifyDataSetChanged();
        picListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                removeItemFromList(position);
                return true;
            }
        });


    }

    public void getPic() {
        ContentResolver contentResolver = getContentResolver();
        Uri picUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        final Cursor picCursor = contentResolver.query(picUri, null, null, null, null);
        if (picCursor != null && picCursor.moveToFirst()) {
            do {
                int picTitle = picCursor.getColumnIndex(MediaStore.Images.Media.DATA);
                String currentTitle = picCursor.getString(picTitle);
                picFilename.add(currentTitle);
                } while (picCursor.moveToNext());

        }

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent picIntent = new Intent(Intent.ACTION_VIEW);
        //picIntent.setAction(Intent.ACTION_GET_CONTENT);
        final File file=new File(picFilename.get(position));
        Uri uri=Uri.fromFile(file);
        //Uri data = Uri.parse("content://" + picFilename.get(position));
        Log.v("URI",uri.toString());
        picIntent.setDataAndType(uri, "image/*").addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //startActivity(picIntent);
        startActivityForResult(picIntent, IMAGE_GALLERY_REQUEST);

    }

    protected void removeItemFromList(final int position) {
        final int deletePosition = position;
        final AlertDialog.Builder alert = new AlertDialog.Builder(
                PictureScreen.this);

        final String message = "Delete And Rename";
        alert.setTitle("Delete and Rename");
        alert.setMessage(message);
        alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri data = Uri.parse("file://"+picFilename.get(position));
                Log.v("URIDATA",data.toString());
                String path =data.toString();
                Log.v("String",path.toString());
                try {
                    File file=new File(new URI(path));
                    Log.v("mmfile",file.toString());
                    if (file.exists()) {
                        if(file.delete()) {
                            Toast.makeText(PictureScreen.this, "File Deleted", Toast.LENGTH_LONG).show();
                            File imageFile=new File(picFilename.get(position));
                            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(String.valueOf(imageFile)))));
                        }
                        else
                           Toast.makeText(PictureScreen.this, "File Not Deleted", Toast.LENGTH_LONG).show();
                        }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                picFilename.remove(deletePosition);

//               Log.v("Array",picFilename.remove(deletePosition).toString());
                picAdapter.notifyDataSetChanged();
            }
        });
        alert.setNegativeButton("Rename", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                rename=(EditText)findViewById(R.id.et_rename);
                rename=new EditText(PictureScreen.this);
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        PictureScreen.this);
                alert.setTitle("Rename");
                alert.setView(rename);
                final File file=new File(picFilename.get(position));
                Log.v("mehh",file.toString());
                rename.setText(""+file.getName());
                alert.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(file.exists()){
                            String txt = rename.getText().toString();
                            File existingName = new File(file.getPath());
                            Log.v("Old", existingName.toString());
                            File newName = new File(file.getParent() + "/" + txt);
                            Log.v("New", newName.toString());
                            Boolean rename = existingName.renameTo(newName);
                            if (!rename) {
                                Toast.makeText(PictureScreen.this, "Failed", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(PictureScreen.this, "Successfully renamed file", Toast.LENGTH_LONG).show();
                                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(existingName)));
                                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(newName)));
                                }
                            }
                        getPic();

                        picAdapter.notifyDataSetChanged();
                        }
                });

                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                alert.show();
                }
        });
        alert.show();
    }





}

