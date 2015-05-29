package com.example.basak.monkeyme;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by basak on 2015-05-29.
 */
public class MyProfile extends Activity {
    View LayoutPhoto, LayoutGame, LayoutAchieve, BtnPhotob, BtnGameb, BtnAchieveb;
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

    }

    View.OnClickListener mClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
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
    }
