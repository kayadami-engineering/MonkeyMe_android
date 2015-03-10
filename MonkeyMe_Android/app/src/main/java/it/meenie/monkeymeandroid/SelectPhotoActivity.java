package it.meenie.monkeymeandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import it.neokree.monkeymeandroid.R;

/**
 * Created by MacMeenie on 15. 2. 23..
 */
public class SelectPhotoActivity extends Activity {
    TextView UseBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_select_photo);
        UseBtn = (TextView) this.findViewById(R.id.UseBtn);
        UseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddHintActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
