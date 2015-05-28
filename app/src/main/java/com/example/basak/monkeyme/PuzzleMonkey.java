package com.example.basak.monkeyme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by basak on 2015-04-27.
 */
public class PuzzleMonkey extends Activity {
    ArrayList<HashMap<String, String>> hashArray;
    HashMap<String, String> InfoTable;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_puzzle);
        BackThread thread = new BackThread(5, mHandler);
        thread.setDaemon(true);
        thread.start();
        final EditText GuessInput = (EditText)findViewById(R.id.GuessInput);
        ImageButton GuessOk = (ImageButton)findViewById(R.id.GuessOk);
        GuessOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                if(GuessInput.getText().toString().equals(hashArray.get(0).get("keyword"))){
                    GuessInput.setText("");
                    BackThread test = new BackThread(handler, 6,hashArray.get(0).get("rnd_no"), hashArray.get(0).get("m_no"), null);
                    test.setDaemon(true);
                    test.start();
                    Intent intent = new Intent(getBaseContext(), PuzzleCorrect.class);
                    intent.putExtra("InfoList", InfoTable);
                    startActivity(intent);
                    finish();
                    /*
                    BackThread thread = new BackThread(5, mHandler);
                    thread.setDaemon(true);
                    thread.start();*/

                } else{
                    GuessInput.setText("");
                    final FrameLayout GuessWrong = (FrameLayout)findViewById(R.id.GuessWrong);
                    GuessWrong.setVisibility(View.VISIBLE);
                    new CountDownTimer(2000, 2000){
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            GuessWrong.setVisibility(View.INVISIBLE);
                        }
                    }.start();
                }
            }
        });
    }
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                Log.i("tag", "getMSG!");
                hashArray = (ArrayList<HashMap<String, String>>)msg.obj;
                InfoTable = hashArray.get(0);
                if(!hashArray.isEmpty()){
                    Log.i("name", hashArray.get(0).get("name"));
                    Log.i("hint", hashArray.get(0).get("hint"));
                    Log.i("keyword", hashArray.get(0).get("keyword"));
                    setUi(hashArray.get(0));
                }
            }
        }


    };
    void setUi(HashMap<String, String> InfoTable){
        TextView GuessName = (TextView)findViewById(R.id.GuessName);
        GuessName.setText(InfoTable.get("name"));
        TextView GuessHint = (TextView)findViewById(R.id.GuessHint);
        GuessHint.setText(InfoTable.get("hint"));
        TextView GuessKey = (TextView)findViewById(R.id.GuessKey);
        GuessKey.setText(InfoTable.get("keyword"));
        TextView PuzzleRound = (TextView)findViewById(R.id.PuzzleRound);
        PuzzleRound.setText("Round "+InfoTable.get("rnd_no"));
        ImageButton HintBtn = (ImageButton)findViewById(R.id.HintBtn);
        HintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView GuessHint = (TextView)findViewById(R.id.GuessHint);
                GuessHint.setVisibility(View.VISIBLE);
            }
        });
        final EditText GuessInput = (EditText)findViewById(R.id.GuessInput);
        ImageButton GuessOk = (ImageButton)findViewById(R.id.GuessOk);
        final ImageView GuessProfile = (ImageView)findViewById(R.id.GuessProfile);
        final ImageView GuessImg = (ImageView)findViewById(R.id.GuessImg);

        Handler imgHandler = new Handler(){
            public void handleMessage(Message msg){
                if(msg.what == 2){
                    Log.i("tag", "getIMG!");
                    GuessProfile.setImageBitmap((Bitmap)msg.obj);
                } else if(msg.what == 3){
                    GuessImg.setImageBitmap((Bitmap)msg.obj);
                }

            }
        };

        ImageThread profile = new ImageThread(InfoTable.get("profile"), imgHandler, 2);
        ImageThread quiz = new ImageThread(InfoTable.get("image"), imgHandler, 3);
        profile.setDaemon(true);
        profile.start();
        quiz.setDaemon(true);
        quiz.start();
    }

}
