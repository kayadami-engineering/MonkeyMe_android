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


class MultiAdapter extends BaseAdapter{
    LayoutInflater mInflater;
    ArrayList<ListItem> arSrc;
    Context context;
    Handler mhandler;

    public MultiAdapter(Context context, ArrayList<ListItem> arItem, Handler mainHandler){
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        arSrc = arItem;

        this.mhandler = mainHandler;
    }
    @Override
    public int getCount(){
        return arSrc.size();
    }
    @Override
    public ListItem getItem(int position){
        return arSrc.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemViewType(int position){
        return arSrc.get(position).Type;
    }
    @Override
    public int getViewTypeCount(){
        Log.i("size", String.valueOf(arSrc.size()));
        return arSrc.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            int res = 0;
            switch (arSrc.get(position).Type){
                case -1 :
                case 0 :
                    res = R.layout.custom_turn_list_item;
                    break;
                case 1 :
                    res = R.layout.custom_my_turn;
                    break;
                case 2 :
                    res = R.layout.custom_friends_turn;
                    break;
                case 3 :
                    res = R.layout.game_bar;
                    break;
            }
            convertView = mInflater.inflate(res, parent, false);
        }
        switch(arSrc.get(position).Type){
            case -1 :

            case 0 :
                TextView name = (TextView) convertView.findViewById(R.id.FriendName);
                name.setText(arSrc.get(position).Name);
                TextView round = (TextView) convertView.findViewById(R.id.Round);
                round.setText(arSrc.get(position).Round);
                final ImageView picture = (ImageView) convertView.findViewById(R.id.Pic);
                if(arSrc.get(position).ProfileImage == null) {
                    Handler imgHandler = new Handler() {
                        public void handleMessage(Message msg) {
                            if (msg.what == 0) {
                                Log.i("tag", "getIMG!");
                                arSrc.get(position).ProfileImage = (Bitmap)msg.obj; //caching
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

                //picture.setImageResource(arSrc.get(position).getPicture());
                break;

           case 3 :

               arSrc.get(position).setMonkeyBtn(convertView);
               arSrc.get(position).setPuzzleBtn(convertView);
               /**
                Log.i("tag", "button1");
                Button MonkeyBtn = (Button) convertView.findViewById(R.id.MonkeyBtn);
                Log.i("tag", "button2");**/
                /**MonkeyBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Log.i("tag", "button3");
                        Message msg = new Message();
                        msg.what = 1;
                        mhandler.sendMessage(msg);
                    }
                });**/


                break;
        }
        return convertView;
    }
}