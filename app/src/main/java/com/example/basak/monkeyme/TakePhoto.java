package com.example.basak.monkeyme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * Created by basak on 2015-04-09.
 */
public class TakePhoto extends Activity {
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
                "/attachimage.jpeg";
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

                        BackThread2 thread = new BackThread2(mHandler, mPath, InfoTable);
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
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 10;
            BitmapImg = BitmapFactory.decodeFile(mPath, options);
            SaveBitmapToFileCache(BitmapImg, mPath);
            NewPhoto.setImageBitmap(BitmapImg);


        }


    }

    // Bitmap to File
    public  void SaveBitmapToFileCache(Bitmap bitmap, String mPath) {

        //File file = new File(strFilePath);
        /**
        // If no folders
        if (!file.exists()) {
            file.mkdirs();
            // Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }
         **/

        File fileCacheItem = new File(mPath);
        OutputStream out = null;

        try {
            fileCacheItem.createNewFile();
            out = new FileOutputStream(fileCacheItem);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void takePhoto() {
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
