package it.meenie.monkeymeandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.neokree.monkeymeandroid.R;

/**
 * Created by MacMeenie on 15. 2. 23..
 */
public class FragmentTwo extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View convertView = inflater.inflate(R.layout.ui_game_page,container, false);
        /*TextView text = new TextView(container.getContext());
        text.setText("Fragment content");
        text.setGravity(Gravity.CENTER);
*/
        return convertView;
    }
}