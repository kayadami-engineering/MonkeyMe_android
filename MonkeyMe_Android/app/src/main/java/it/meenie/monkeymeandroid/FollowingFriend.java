package it.meenie.monkeymeandroid;

import android.app.Activity;
import android.os.Bundle;

import it.neokree.monkeymeandroid.R;

/**
 * Created by MacMeenie on 15. 2. 23..
 */
public class FollowingFriend extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_friend_list_item);
    }
}
