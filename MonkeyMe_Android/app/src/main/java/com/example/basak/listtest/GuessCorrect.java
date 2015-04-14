package com.example.basak.listtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.HashMap;

/**
 * Created by basak on 2015-04-09.
 */
public class GuessCorrect extends Activity {
    HashMap<String, String> InfoTable;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_correct);
        InfoTable = (HashMap<String, String>)getIntent().getSerializableExtra("InfoList");
        ImageView img = (ImageView)findViewById(R.id.testimg);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SelectKeyWord.class);
                intent.putExtra("InfoList", InfoTable);
                startActivity(intent);
                finish();
            }
        });
    }
}
