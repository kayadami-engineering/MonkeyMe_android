package com.example.basak.listtest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.HashMap;

/**
 * Created by basak on 2015-04-09.
 */
public class TakeMovie extends Activity {
    ImageView NewPhoto;
    String mPath;
    TextView Retake, UsePhoto;
    Intent intent;
    Bitmap BitmapImg;
    HashMap<String, String> InfoTable;
    //ImageButton OkHintBtn;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_take_photo);
        InfoTable = (HashMap<String, String>)getIntent().getSerializableExtra("InfoList");
        Retake = (TextView) findViewById(R.id.Retake);
        UsePhoto = (TextView) findViewById(R.id.UsePhoto);
        NewPhoto = (ImageView) findViewById(R.id.NewPhoto);

        mPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/attachmovie.mp4";
        Retake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        UsePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.ui_add_hint);
                NewPhoto = (ImageView) findViewById(R.id.NewPhoto);
                NewPhoto.setImageBitmap(BitmapImg);
                ImageButton OkHintBtn = (ImageButton)findViewById(R.id.OkHintBtn);
                OkHintBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        EditText edit = (EditText)findViewById(R.id.GuessHint);
                        InfoTable.remove("hint");
                        InfoTable.put("hint", edit.getText().toString());

                        BackThread3 thread = new BackThread3(mHandler, mPath, InfoTable);
                        thread.setDaemon(true);
                        thread.start();

                        Intent intent = new Intent(getBaseContext(), Test.class);
                        startActivity(intent);
                        finish();

                    }
                });
                 /**
                OkHintBtn = (ImageButton)findViewById(R.id.OkHintBtn);
                NewPhoto = (ImageView) findViewById(R.id.NewPhoto);
                NewPhoto.setImageBitmap(BitmapImg);
                BackThread2 thread = new BackThread2(mHandler, mPath, InfoTable);
                thread.setDaemon(true);
                thread.start();**/

            }
        });
        takePhoto();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            //BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inSampleSize = 6;
            //BitmapImg = BitmapFactory.decodeFile(mPath, options);
            //NewPhoto.setImageBitmap(BitmapImg);
        }


    }

    public void takePhoto() {
        intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 7);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mPath)));
        startActivityForResult(intent, 1);
    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                Log.i("tag", "getMSG!");


            }
        }

        ;
    };
}

class BackThread3 extends Thread{
    Handler mHandler;
    String g_no;
    String m_no;
    String b_count;
    String mPath;
    HashMap<String, String> InfoTable;

    BackThread3(Handler mHandler,String mPath, HashMap<String, String> InfoTable){
        this.mHandler = mHandler;
        this.mPath = mPath;
        this.InfoTable = InfoTable;
    }

    public void run(){
        Network net = new Network();
        net.downNetwork3(mHandler, 2, mPath, InfoTable);
    }
}