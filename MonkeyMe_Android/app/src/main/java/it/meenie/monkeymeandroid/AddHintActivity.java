package it.meenie.monkeymeandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import it.neokree.monkeymeandroid.R;

/**
 * Created by MacMeenie on 15. 2. 23
 * Refactoring at 15. 3. 3
 * This Activity is for Add Hint Page in game step.
 * After user takes a photo, they can add hint at photo.
 * Associated with ui_add_hint.xml
 */
public class AddHintActivity extends Activity {
   ImageView SelectBtn; //button has a event going to next step(located at the top-right,@drawable/ok)
   EditText HintTxt; //text field for user input(located at middle)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_add_hint); //Layout xml
        /* connect with layout component */
        SelectBtn = (ImageView) this.findViewById(R.id.SelectBtn);
        HintTxt = (EditText) this.findViewById(R.id.HintTxt);

        /* set event */
        SelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hint = HintTxt.getText().toString(); //get hint from text field
                HintTxt.setText(""); //reset the text field
                /* 네트워크 연결 부분 : 힌트와 사진을 서버로 전송할 것 */
                Intent intent = new Intent(getBaseContext(), EndingActivity.class); //set new intent
                /* 기능이 추가됨에 따라 변경할 것. 새로 시작하고 완전히 끝내는 것이 이상적인 작동인가?*/
                startActivity(intent); //start new EndingActivity
                finish(); //finish AddHintActivity
            }
        });
    }
}
