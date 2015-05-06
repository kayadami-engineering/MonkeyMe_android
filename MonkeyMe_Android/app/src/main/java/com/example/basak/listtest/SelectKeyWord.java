package com.example.basak.listtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by basak on 2015-04-10.
 */
public class SelectKeyWord extends Activity {
    HashMap<String, String> table;
    TextView KeyWord;
    ImageView GMBackBtn;
    HashMap<String, String> InfoTable;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_select_keyword);
        InfoTable = (HashMap<String, String>)getIntent().getSerializableExtra("InfoList");
        Log.i("g_no", InfoTable.get("g_no"));
        Log.i("m_no", InfoTable.get("m_no"));
        //Log.i("b_count", InfoTable.get("b_count"));
        BackThread thread = new BackThread(3, mHandler);
        thread.setDaemon(true);
        thread.start();
        final TextView EasyKeyword = (TextView) findViewById(R.id.EasyKeyword);
        final TextView NormalKeyword = (TextView) findViewById(R.id.NormalKeyword);
        final TextView DifKeyword = (TextView) findViewById(R.id.DifKeyword);
        final TextView HellKeyword = (TextView) findViewById(R.id.HellKeyword);

        EasyKeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoTable.remove("keyword");
                InfoTable.remove("b_count");
                InfoTable.put("keyword", EasyKeyword.getText().toString());
                InfoTable.put("b_count", "1");
                Intent intent = new Intent(getBaseContext(), TakeMovie.class);
                intent.putExtra("InfoList", InfoTable);
                startActivity(intent);
                finish();
            }
        });
        NormalKeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoTable.remove("keyword");
                InfoTable.remove("b_count");
                InfoTable.put("keyword", NormalKeyword.getText().toString());
                InfoTable.put("b_count", "2");
                Intent intent = new Intent(getBaseContext(), TakePhoto.class);
                intent.putExtra("InfoList", InfoTable);
                startActivity(intent);
                finish();
            }
        });
        DifKeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoTable.remove("keyword");
                InfoTable.remove("b_count");
                InfoTable.put("keyword", DifKeyword.getText().toString());
                InfoTable.put("b_count", "3");
                Intent intent = new Intent(getBaseContext(), TakePhoto.class);
                intent.putExtra("InfoList", InfoTable);
                startActivity(intent);
                finish();
            }
        });
        HellKeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoTable.remove("keyword");
                InfoTable.remove("b_count");
                InfoTable.put("keyword", HellKeyword.getText().toString());
                InfoTable.put("b_count", "4");
                Intent intent = new Intent(getBaseContext(), TakePhoto.class);
                intent.putExtra("InfoList", InfoTable);
                startActivity(intent);
                finish();
            }
        });
        GMBackBtn = (ImageView)findViewById(R.id.GMBackBtn);
        GMBackBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Test.class);
                startActivity(intent);
                finish();
            }
        });
    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                Log.i("tag", "getMSG!");
                table = (HashMap<String, String>)msg.obj;
                KeyWord = (TextView)findViewById(R.id.EasyKeyword);
                KeyWord.setText(table.get("1"));
                KeyWord = (TextView)findViewById(R.id.NormalKeyword);
                KeyWord.setText(table.get("2"));
                KeyWord = (TextView)findViewById(R.id.DifKeyword);
                KeyWord.setText(table.get("3"));
                KeyWord = (TextView)findViewById(R.id.HellKeyword);
                KeyWord.setText(table.get("4"));
            }

        }
    };
}

