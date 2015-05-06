package com.example.basak.listtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by basak on 2015-04-09.
 */
public class GuessCorrect extends Activity {
    HashMap<String, String> InfoTable;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_guess_correct2);
        InfoTable = (HashMap<String, String>)getIntent().getSerializableExtra("InfoList");

        TextView MyTurnText = (TextView)findViewById(R.id.MyTurnTxt);
        MyTurnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SelectKeyWord.class);
                intent.putExtra("InfoList", InfoTable);
                startActivity(intent);
                finish();
            }
        });

        ImageButton SendReply = (ImageButton)findViewById(R.id.SendReply);
        SendReply.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
    }

}
