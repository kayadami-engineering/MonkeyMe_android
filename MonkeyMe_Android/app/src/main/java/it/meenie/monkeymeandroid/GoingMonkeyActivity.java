package it.meenie.monkeymeandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import it.neokree.monkeymeandroid.R;

/**
 * Created by MacMeenie on 15. 2. 23..
 */
public class GoingMonkeyActivity extends Activity {
    ImageView BackBtn;
    RelativeLayout Easy, Normal, Hard, Hell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_go_monkey);
        //System.out.println("고 몽키 호출");
        BackBtn = (ImageView)this.findViewById(R.id.GMBackBtn);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), it.meenie.monkeymeandroid.SelectFriendActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Easy = (RelativeLayout) this.findViewById(R.id.EasyBox);
        Easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), it.meenie.monkeymeandroid.SelectPhotoActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Normal = (RelativeLayout) this.findViewById(R.id.NormalBox);
        Hard = (RelativeLayout) this.findViewById(R.id.HardBox);
        Hell = (RelativeLayout) this.findViewById(R.id.HellBox);
    }

}
