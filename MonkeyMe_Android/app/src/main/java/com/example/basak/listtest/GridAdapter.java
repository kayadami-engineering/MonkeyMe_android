package com.example.basak.listtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by basak on 2015-04-15.
 */
public class GridAdapter extends BaseAdapter{
    Context context;
    LayoutInflater Inflater;
    ArrayList<GridItem> arSrc;
//Context context, ArrayList<ListItem> arItem, Handler mainHandler
    public GridAdapter(Context context, ArrayList<GridItem> aarSrc, Handler mainHandler){
        this.context = context;
        Inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        arSrc = aarSrc;
    }

    @Override
    public int getCount() {
        return arSrc.size();
    }

    @Override
    public Object getItem(int position) {
        return arSrc.get(position).Name;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if(convertView == null){
            convertView = Inflater.inflate(R.layout.custom_grid_friends, parent, false);
        }
        TextView GridName= (TextView)convertView.findViewById(R.id.GridName);
        GridName.setText(arSrc.get(position).Name);

        final ImageView picture = (ImageView) convertView.findViewById(R.id.GridProfile);
        if(arSrc.get(position).ProfileImage == null) {
            Handler imgHandler = new Handler() {
                public void handleMessage(Message msg) {
                    if (msg.what == 0) {
                        Log.i("tag", "getIMG!");
                        arSrc.get(pos).ProfileImage = (Bitmap)msg.obj; //caching
                        picture.setImageBitmap((Bitmap) msg.obj);
                    }
                }
            };

            ImageThread imgThread = new ImageThread(arSrc.get(position).Picture, imgHandler, 0);
            imgThread.setDaemon(true);
            imgThread.start();
        } else{
            picture.setImageBitmap(arSrc.get(position).ProfileImage);
        }

        return convertView;
    }
}