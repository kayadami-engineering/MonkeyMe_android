package it.meenie.monkeymeandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import it.neokree.monkeymeandroid.R;

/**
 * Created by MacMeenie on 15. 2. 24
 * Refactoring at 15. 3. 3
 */
public class CorrectActivity extends Activity{
    int timer = 0; //check time
    int sec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_correct);
        sec = 5; //5초 제한
        /* 5초 뒤 종료 타이머 */
        new CountDownTimer(sec*1000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer++;
            }
            public void onFinish() {
                /* 타이머 완료시 GoingMonkeyActivity 실행 */
                Intent intent = new Intent(getBaseContext(), GoingMonkeyActivity.class);
                startActivity(intent); //start GoingMonkeyActivity
                finish(); //finish CorrectActivity
            }
        }.start();

    }
}
