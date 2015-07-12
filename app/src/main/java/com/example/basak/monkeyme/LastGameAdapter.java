package com.example.basak.monkeyme;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.DisplayMetrics;
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
    int gridWidth;
    int gridHeight;
    public LastGameAdapter(Context context, ArrayList<GridItem> aarSrc, Handler mainHandler){
        this.context = context;
        Inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        arSrc = aarSrc;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        gridWidth = (metrics.widthPixels/3) +1;
        gridHeight = gridWidth;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final ImageView ImgLastGame;
        if (convertView == null) {    //이 if문 안에 데이터를 set하는 코드를 넣어놓음으로 인해 아이템 재사용시 같은 부분이 뒤에 또 나올수도 있음 추후 문제생길때 체크하자
            convertView = Inflater.inflate(R.layout.item_last_game, parent, false);
            ImgLastGame = (ImageView) convertView.findViewById(R.id.ImgLastGame);
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) ImgLastGame.getLayoutParams();
            params.width = gridWidth;
            params.height = gridHeight;
            ImgLastGame.setLayoutParams(params);
            //ImgLastGame.setImageResource(R.drawable.icon);
            if (arSrc.get(position).Picture.contains("jpeg")) {

                Log.i("RUL", arSrc.get(position).Picture);
                //new BitmapTask(ImgLastGame, arSrc.get(position).Picture, gridWidth, arSrc.get(position).ProfileImage).execute();
                new BitmapTask(ImgLastGame, arSrc.get(position).Picture, gridWidth, arSrc.get(position).ProfileImage) {
                    protected void onPostExecute(Bitmap bitmap) {
                        ImgLastGame.setImageBitmap(bitmap);
                        arSrc.get(position).ProfileImage = bitmap;
                        try {
                            istream.close();
                            istream2.close();
                        } catch (Exception e) {
                            Log.e("Error", "Closing Image istream Error");
                        }
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);    //for multiThread
            }
        } else {
            ImgLastGame = (ImageView) convertView.findViewById(R.id.ImgLastGame);
                if ((arSrc.get(position).ProfileImage == null) && arSrc.get(position).Picture.contains("jpeg")){
                    Log.i("RUL", arSrc.get(position).Picture);
                    //new BitmapTask(ImgLastGame, arSrc.get(position).Picture, gridWidth, arSrc.get(position).ProfileImage).execute();
                    new BitmapTask(ImgLastGame, arSrc.get(position).Picture, gridWidth, arSrc.get(position).ProfileImage) {
                        protected void onPostExecute(Bitmap bitmap) {
                            ImgLastGame.setImageBitmap(bitmap);
                            arSrc.get(position).ProfileImage = bitmap;
                            try {
                                istream.close();
                                istream2.close();
                            } catch (Exception e) {
                                Log.e("Error", "Closing Image istream Error");
                            }
                        }
                    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else{
                    Log.i("else", arSrc.get(position).Picture);
                    ImgLastGame.setImageBitmap(arSrc.get(position).ProfileImage);
                }
            }
        return convertView;
    }
}