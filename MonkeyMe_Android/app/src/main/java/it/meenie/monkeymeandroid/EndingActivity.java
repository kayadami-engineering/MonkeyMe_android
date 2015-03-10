package it.meenie.monkeymeandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import it.neokree.monkeymeandroid.R;

/**
 * Created by MacMeenie on 15. 2. 23
 * Refatoring at 15. 3. 3
 */
public class EndingActivity extends Activity {
    ImageView SelectBtn; //button for close the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_ending);
        /* connect with layout component */
        SelectBtn = (ImageView) this.findViewById(R.id.SelectBtn);
        /* set event */
        SelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 홈 화면으로 이동 */
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent); //start MainActivity
                finish(); //finish EndingActivity
            }
        });
    }
}
