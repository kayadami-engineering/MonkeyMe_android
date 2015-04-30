package com.example.basak.listtest;

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

import java.util.HashMap;

/**
 * Created by basak on 2015-04-06.
 */
public class GuessActivity extends Activity{
    HashMap<String, String> InfoTable;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_guess);
        InfoTable = (HashMap<String, String>)getIntent().getSerializableExtra("InfoList");
        Log.i("m_no", InfoTable.get("m_no"));

        TextView GuessName = (TextView)findViewById(R.id.GuessName);
        GuessName.setText(InfoTable.get("name"));
        TextView GuessHint = (TextView)findViewById(R.id.GuessHint);
        GuessHint.setText(InfoTable.get("hint"));
        TextView GuessKey = (TextView)findViewById(R.id.GuessKey);
        GuessKey.setText(InfoTable.get("keyword"));
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

        //친구가 보낸 문제를 맞췄을때 정답화면으로
        GuessOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                if(GuessInput.getText().toString().equals(InfoTable.get("keyword"))){
                    BackThread test = new BackThread(handler, 2,InfoTable.get("g_no"), InfoTable.get("m_no"), InfoTable.get("b_count"));
                    Log.i("g_no", InfoTable.get("g_no"));
                    Log.i("m_no", InfoTable.get("m_no"));
                    Log.i("b_count", InfoTable.get("b_count"));
                    test.setDaemon(true);
                    test.start();
                    Intent intent = new Intent(getBaseContext(), GuessCorrect.class);
                    intent.putExtra("InfoList", InfoTable);
                    startActivity(intent);
                    finish();
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
        ImageThread quiz = new ImageThread(InfoTable.get("imageUrl"), imgHandler, 3);
        profile.setDaemon(true);
        profile.start();
        quiz.setDaemon(true);
        quiz.start();


    }
}

class BackThread2 extends Thread{
    Handler mHandler;
    String g_no;
    String m_no;
    String b_count;
    String mPath;
    HashMap<String, String> InfoTable;

    BackThread2(Handler mHandler,String mPath, HashMap<String, String> InfoTable){
        this.mHandler = mHandler;
        this.mPath = mPath;
        this.InfoTable = InfoTable;
    }

    public void run(){
        Network net = new Network();
        net.downNetwork2(mHandler, 2, mPath, InfoTable);
    }
}