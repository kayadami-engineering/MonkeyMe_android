package com.example.basak.listtest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by basak on 2015-04-03.
 */
public class SelectFriendActivity extends Activity {
    Button MonkeyFriend;
    Button FaceFriend;
    GridView FriendGrid;
    TextView FriendText;
    ImageView BackBtn;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_select_friend);

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
}
