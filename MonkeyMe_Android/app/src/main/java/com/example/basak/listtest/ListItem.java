package com.example.basak.listtest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;


class ListItem {
    int Type;
    String Name;
    String Level, Round;
    String Picture;
    Button MonkeyBtn;
    Button PuzzleBtn;
    Bitmap ProfileImage;
    String GameNum;
    String IsSolved;


    ListItem(int Type, String Name)
    {
        this.Type = Type;
        this.Name = Name;
    }
    ListItem(int Type, String Name, String Level, String Round, String Picture, String GameNum, String IsSolved)
    {
        this.Type = Type;
        this.Name = Name;
        this.Level = Level;
        this.Round = Round;
        this.Picture = Picture;
        this.GameNum = GameNum;
        this.ProfileImage = null;
        this.IsSolved = IsSolved;
    }

    void setMonkeyBtn(View v){
        MonkeyBtn = (Button) v.findViewById(R.id.MonkeyBtn);
        MonkeyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SelectFriendActivity.class);
                v.getContext().startActivity(intent);

            }
        });

    }

    void setPuzzleBtn(View v){
        PuzzleBtn = (Button) v.findViewById(R.id.RandomBtn);
       /** PuzzleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GuessActivity.class);
                v.getContext().startActivity(intent);

            }
        });**/

    }

}

