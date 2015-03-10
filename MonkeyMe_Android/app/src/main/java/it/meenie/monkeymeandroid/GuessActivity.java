package it.meenie.monkeymeandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import it.meenie.monkeymeandroid.CorrectActivity;
import it.neokree.monkeymeandroid.R;

/**
 * Created by MacMeenie on 15. 2. 24..
 */
public class GuessActivity extends Activity {

    RelativeLayout HintBox, WrongBox;
    ImageView MagicLeafBtn, OkBtn;
    EditText Answer;
    boolean hint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_guess);
        hint = false; //힌트를 봤는지 안 봤는지 받아올 것!
        HintBox = (RelativeLayout) this.findViewById(R.id.Hint);
        HintBox.setVisibility(View.INVISIBLE);
        WrongBox = (RelativeLayout) this.findViewById(R.id.Wrong);
        WrongBox.setVisibility(View.INVISIBLE);
        Answer = (EditText) this.findViewById(R.id.Answer);
        MagicLeafBtn = (ImageView) this.findViewById(R.id.LeafBtn);
        MagicLeafBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!hint){
                   //힌트를 보여준다
                   HintBox.setVisibility(View.VISIBLE);
                   hint = true;
               }
            }
        });
        OkBtn = (ImageView) this.findViewById(R.id.OkBtn);
        OkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = Answer.getText().toString();
                Answer.setText("");
                if(answer.equals("정답")){
                    //맞췄을 경우
                    Intent intent = new Intent(getBaseContext(), CorrectActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    WrongBox.setVisibility(View.VISIBLE);

                }
            }
        });

        Answer.setOnEditorActionListener(new EditText.OnEditorActionListener(){
            public boolean onEditorAction(TextView exampleView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    String answer = Answer.getText().toString();
                    Answer.setText("");
                    if(answer.equals("정답")){
                        //맞췄을 경우
                        Intent intent = new Intent(getBaseContext(), CorrectActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        WrongBox.setVisibility(View.VISIBLE);

                    }
                }
                return true;
            }
        });


    }
}
