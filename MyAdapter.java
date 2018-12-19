package com.example.nabilachowdhury.myfilemanager;



import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class MyAdapter extends BaseAdapter {
    ArrayList<String> fileNames;
    ArrayList<Bitmap> fileimage;
    Bitmap bitmap;
    Context context;
    //int type;

    @Override
    public int getCount() {
        return fileNames.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater imageInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = imageInflater.inflate(R.layout.list_view, parent, false);
            viewHolder.cImage = (ImageView) convertView.findViewById(R.id.ig_thumb_nail);
            viewHolder.cText = (TextView) convertView.findViewById(R.id.tx_file_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.cText.setText(fileNames.get(position));

//        Thread thread = new Thread(new Runnable() {
//
//            @Override
//            public void run() {

//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inSampleSize = 4;
//                bitmap = BitmapFactory.decodeFile(fileNames.get(position).toString(), options);

        //fileimage.add(position,bitmap);

//            }
//        });
//
//        thread.start();
//        viewHolder.cImage.setImageBitmap(bitmap);
        Glide.with(context)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .placeholder(R.mipmap.ic_launcher)
                                .error(R.mipmap.ic_launcher)
                                .diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop()
                )
                .load(fileNames.get(position))
                .into(viewHolder.cImage);
        //viewHolder.cImage.setImageURI(Uri.parse(fileNames.get(position).toString()));


        return convertView;
    }


    public class ViewHolder {
        ImageView cImage;
        TextView cText;
    }


    public MyAdapter(Context context, ArrayList<String> phoneNames) {
        super();
        this.context = context;
        this.fileNames = phoneNames;
        this.fileimage = new ArrayList<>();
        //this.type=type;
    }


}

