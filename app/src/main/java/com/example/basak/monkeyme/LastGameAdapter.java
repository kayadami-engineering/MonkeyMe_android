package com.example.basak.monkeyme;

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

import java.util.ArrayList;

/**
 * Created by basak on 2015-04-15.
 */
public class LastGameAdapter extends BaseAdapter{
    Context context;
    LayoutInflater Inflater;
    ArrayList<GridItem> arSrc;
    public LastGameAdapter(Context context, ArrayList<GridItem> aarSrc, Handler mainHandler){
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
            convertView = Inflater.inflate(R.layout.item_last_game, parent, false);
        }



        final ImageView ImgLastGame = (ImageView) convertView.findViewById(R.id.ImgLastGame);
        ImgLastGame.setImageResource(R.drawable.icon);

        if((arSrc.get(position).ProfileImage == null) && arSrc.get(position).Picture.contains("jpeg")) {
            Handler imgHandler = new Handler() {
                public void handleMessage(Message msg) {
                    if (msg.what == 0) {
                        Log.i("tag", "getIMG!");
                        arSrc.get(pos).ProfileImage = (Bitmap)msg.obj; //caching
                        ImgLastGame.setImageBitmap((Bitmap) msg.obj);

                    }
                }
            };
            Log.i("RUL", arSrc.get(position).Picture);
            ImageThread imgThread = new ImageThread(arSrc.get(position).Picture, imgHandler, 0);
            imgThread.setDaemon(true);
            imgThread.start();
        } else if(arSrc.get(position).Picture.contains("jpeg")){
            ImgLastGame.setImageBitmap(arSrc.get(position).ProfileImage);
        }


        return convertView;
    }
}