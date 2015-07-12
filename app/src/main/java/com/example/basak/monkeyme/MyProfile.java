package com.example.basak.monkeyme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by basak on 2015-05-29.
 */
public class MyProfile extends Activity {
    View LayoutPhoto, LayoutGame, LayoutAchieve, BtnPhotob, BtnGameb, BtnAchieveb;
    ArrayList<HashMap<String, String>> hashArray;
    ArrayList<GridItem> arItem = new ArrayList<GridItem>();
    GridView LastGameGrid;
    LastGameAdapter MyAdapter;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_my_profile);

        LayoutPhoto = findViewById(R.id.LayoutPhoto);
        LayoutGame = findViewById(R.id.LayoutGame);
        LayoutAchieve = findViewById(R.id.LayoutAchieve);

        BtnPhotob = findViewById(R.id.BtnPhotob);
        BtnGameb = findViewById(R.id.BtnGameb);
        BtnAchieveb = findViewById(R.id.BtnAchieveb);

        findViewById(R.id.BtnPhotog).setOnClickListener(mClickListener);
        findViewById(R.id.BtnGameg).setOnClickListener(mClickListener);
        findViewById(R.id.BtnAchieveg).setOnClickListener(mClickListener);

        findViewById(R.id.EditProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), EditProfile.class);;
                startActivity(intent);
                finish();
            }
        });

        MyAdapter = new LastGameAdapter(this, arItem, mHandler);
        BackThread thread = new BackThread(9, mHandler);
        thread.setDaemon(true);
        thread.start();

    }

    View.OnClickListener mClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {   //클릭 리스너 달아줌
            LayoutPhoto.setVisibility(View.INVISIBLE);
            LayoutGame.setVisibility(View.INVISIBLE);
            LayoutAchieve.setVisibility(View.INVISIBLE);
            BtnPhotob.setVisibility(View.INVISIBLE);
            BtnGameb.setVisibility(View.INVISIBLE);
            BtnAchieveb.setVisibility(View.INVISIBLE);

            switch(v.getId()){
                case R.id.BtnPhotog:
                    LayoutPhoto.setVisibility(View.VISIBLE);
                    BtnPhotob.setVisibility(View.VISIBLE);
                    break;
                case R.id.BtnGameg:
                    LayoutGame.setVisibility(View.VISIBLE);
                    BtnGameb.setVisibility(View.VISIBLE);
                    break;
                case R.id.BtnAchieveg:
                    LayoutAchieve.setVisibility(View.VISIBLE);
                    BtnAchieveb.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {

                hashArray = (ArrayList<HashMap<String, String>>) msg.obj;

                if(!hashArray.isEmpty()){
                    //Log.i("name", hashArray.get(0).get("name"));
                    setList(hashArray);
                    LastGameGrid = (GridView) findViewById(R.id.LayoutPhoto);
                    LastGameGrid.setAdapter(MyAdapter);
                    LastGameGrid.setOnItemClickListener(mItemClickListener);
                }

            }
        }
    };

    public void setList(ArrayList<HashMap<String, String>> downlist) {
        for (int i = 0; i < downlist.size(); i++) {
            Log.i("keyword", downlist.get(i).get("keyword"));
            arItem.add(new GridItem(downlist.get(i).get("g_no"), downlist.get(i).get("imageUrl"), downlist.get(i).get("keyword"), downlist.get(i).get("hint"), downlist.get(i).get("date")));
        }
    }


    AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @SuppressWarnings("unchecked")
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(), arItem.get(position).Keyword, Toast.LENGTH_SHORT).show();
            Log.i("clicked", arItem.get(position).Keyword);

        }
    };
    }
