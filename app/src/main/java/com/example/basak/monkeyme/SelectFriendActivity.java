package com.example.basak.monkeyme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by basak on 2015-04-03.
 */
public class SelectFriendActivity extends Activity {
    ArrayList<HashMap<String, String>> hashArray;
    ArrayList<GridItem> arItem = new ArrayList<GridItem>();
    Button MonkeyFriend;
    Button FaceFriend;
    GridView FriendGrid;
    TextView FriendText;
    ImageView BackBtn;
    GridAdapter MyAdapter;
    Intent intent;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_select_friend);
        MyAdapter = new GridAdapter(this, arItem, mHandler);
        BackThread thread = new BackThread(4, mHandler);
        thread.setDaemon(true);
        thread.start();

        MonkeyFriend = (Button)findViewById(R.id.MonkeyFriendBtn);
        FaceFriend = (Button)findViewById(R.id.FaceFriendBtn);
        FriendGrid = (GridView)findViewById(R.id.FriendGrid);
        FriendText = (TextView)findViewById(R.id.FriendText);
        BackBtn = (ImageView)findViewById(R.id.BackBtn);

        BackBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Test.class);
                startActivity(intent);
                finish();
            }
        });
        MonkeyFriend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MonkeyFriend.setBackgroundColor(Color.parseColor("#07B3C5"));
                MonkeyFriend.setTextColor(Color.parseColor("#FFFFFF"));
                FaceFriend.setBackgroundColor(Color.parseColor("#FFFFFF"));
                FaceFriend.setTextColor(Color.parseColor("#07B3C5"));
                FriendText.setVisibility(View.INVISIBLE);
                FriendGrid.setVisibility(View.VISIBLE);
            }
        });

        FaceFriend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FaceFriend.setBackgroundColor(Color.parseColor("#07B3C5"));
                FaceFriend.setTextColor(Color.parseColor("#FFFFFF"));
                MonkeyFriend.setBackgroundColor(Color.parseColor("#FFFFFF"));
                MonkeyFriend.setTextColor(Color.parseColor("#07B3C5"));
                FriendGrid.setVisibility(View.INVISIBLE);
                FriendText.setVisibility(View.VISIBLE);
            }
        });
    }
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                Log.i("tag", "getMSG!");
                hashArray = (ArrayList<HashMap<String, String>>)msg.obj;
                if(!hashArray.isEmpty()){
                    Log.i("name", hashArray.get(0).get("name"));
                    setList(hashArray);
                    FriendGrid = (GridView) findViewById(R.id.FriendGrid);
                    FriendGrid.setAdapter(MyAdapter);
                    FriendGrid.setOnItemClickListener(mItemClickListener);
                }
            }
        }
        public void setList(ArrayList<HashMap<String, String>> downlist) {
            Log.i("name", hashArray.get(1).get("name"));
            for (int i = 0; i < downlist.size(); i++) {
                Log.i("loop", "loopIn");
                arItem.add(new GridItem(downlist.get(i).get("profile"), downlist.get(i).get("name")));
            }
        }
    };

    AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @SuppressWarnings("unchecked")
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Log.i("position", String.valueOf(position));
            hashArray.get(position).put("g_no", "0");
            hashArray.get(position).put("keyword", "blank");
            hashArray.get(position).put("hint", "hint");
            hashArray.get(position).put("b_count", "1");
            intent = new Intent(getBaseContext(), SelectKeyWord.class);
            intent.putExtra("InfoList", hashArray.get(position));
            startActivity(intent);
            finish();

        }
    };

}
