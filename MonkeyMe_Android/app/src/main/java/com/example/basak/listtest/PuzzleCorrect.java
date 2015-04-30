package com.example.basak.listtest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by basak on 2015-04-29.
 */
public class PuzzleCorrect extends Activity {
    ArrayList<HashMap<String, String>> hashArray;
    HashMap<String, String> InfoTable;
    ArrayList<ReplyItem> arItem = new ArrayList<ReplyItem>();
    ReplyAdapter replyAdapter;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_puzzle_correct2);
        InfoTable = (HashMap<String, String>)getIntent().getSerializableExtra("InfoList");
        replyAdapter = new ReplyAdapter(this, arItem);


        Handler mhandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    Log.i("tag", "getMSG!");
                    hashArray = (ArrayList<HashMap<String, String>>)msg.obj;
                    if(!hashArray.isEmpty()){
                        for(int i=0; i<hashArray.size(); i++){
                            arItem.add(new ReplyItem(hashArray.get(i).get("name"), hashArray.get(i).get("contents"), hashArray.get(i).get("date"), hashArray.get(i).get("likeCount")));
                        }

                        ListView MyList;
                        MyList = (ListView)findViewById(R.id.ReplyList);
                        MyList.setAdapter(replyAdapter);
                    }
                }
            }
        };

        BackThread test = new BackThread(mhandler, 7,InfoTable.get("g_no"), null, null);
        test.setDaemon(true);
        test.start();


    }


}

class ReplyItem{
    int Type = 0;
    String Name="";
    String Reply="";
    String Date="";
    String Like="";

    ReplyItem(String Name, String Reply, String Date, String Like){
        this.Name = Name;
        this.Reply = Reply;
        this.Date = Date;
        this.Like = Like;
    }
}

class ReplyAdapter extends BaseAdapter{
    LayoutInflater mInflater;
    ArrayList<ReplyItem> arSrc;
    Context context;

    public ReplyAdapter(Context context, ArrayList<ReplyItem> arItem){
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        arSrc = arItem;
        this.context = context;
    }
    @Override
    public int getCount(){
        return arSrc.size();
    }
    @Override
    public ReplyItem getItem(int position){
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
            convertView = mInflater.inflate(R.layout.ui_puzzle_correct2, parent, false);
        }
        TextView text = (TextView)convertView.findViewById(R.id.Name);
        text.setText(arSrc.get(position).Name);
        Log.i("name", arSrc.get(position).Reply);
        //TextView text2 = (TextView)convertView.findViewById(R.id.Reply);
        //text2.setText(arSrc.get(position).Reply);
        text = (TextView)convertView.findViewById(R.id.Date);
        text.setText(arSrc.get(position).Date);
        text = (TextView)convertView.findViewById(R.id.Like);
        text.setText(arSrc.get(position).Like);
        return convertView;
    }
}