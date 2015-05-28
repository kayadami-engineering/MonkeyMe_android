package com.example.basak.monkeyme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by basak on 2015-05-18.
 */
class KeyWordAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    ArrayList<KeyWordItem> arSrc;
    Context context;

    public KeyWordAdapter(Context context, ArrayList<KeyWordItem> arItem){
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        arSrc = arItem;
        this.context = context;
    }
    @Override
    public int getCount(){
        return arSrc.size();
    }
    @Override
    public KeyWordItem getItem(int position){
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
        return arSrc.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.keyword_letter_item, parent, false);
        }
        //TextView text = (TextView)convertView.findViewById(R.id.Letter);
        //text.setText(arSrc.get(position).word);

        return convertView;
    }
}
