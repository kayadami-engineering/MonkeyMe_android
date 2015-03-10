package it.meenie.monkeymeandroid;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import it.neokree.monkeymeandroid.R;

/**
 * Created by MacMeenie on 15. 2. 23..
 */
public class SelectFriendActivity extends TabActivity {
    SearchView searchView;
    TabHost tab;
    ImageView SelectBtn, BackBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_select_friend);
        SelectBtn = (ImageView)this.findViewById(R.id.SelectBtn);
        tab = (TabHost) this.findViewById(android.R.id.tabhost);
        tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                //다른 애들 초기
                for(int i=0; i<tab.getTabWidget().getChildCount();i++){
                    LinearLayout relativeLayout = (LinearLayout) tab.getTabWidget().getChildAt(i);
                    tab.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
                    TextView tv = (TextView) relativeLayout.getChildAt(1);
                    tv.setTextColor(Color.parseColor("#07B3C5"));
                    tv.setTypeface(Typeface.SANS_SERIF);
                    tv.setGravity(Gravity.CENTER);
                }
                //선택받은 애 색상
                tab.getTabWidget().getChildAt(tab.getCurrentTab()).setBackgroundColor(Color.parseColor("#07B3C5"));
                LinearLayout relativeLayout = (LinearLayout) tab.getTabWidget().getChildAt(tab.getCurrentTab());
                TextView tv = (TextView) relativeLayout.getChildAt(1);
                tv.setTextColor(Color.parseColor("#ffffff"));
                tv.setTypeface(Typeface.SANS_SERIF);
                tv.setGravity(Gravity.CENTER);
            }
        });

        TabSpec facebookFriend = tab.newTabSpec("페이스북 친구");
        TabSpec followingFriend = tab.newTabSpec("팔로잉");
        TabSpec followerFriend = tab.newTabSpec("팔로워");

        facebookFriend.setIndicator("페이스북 친구").setContent((new Intent(this,FacebookFriend.class)));
        followingFriend.setIndicator("팔로잉").setContent((new Intent(this,FollowingFriend.class)));
        followerFriend.setIndicator("팔로워").setContent((new Intent(this, FollowerFriend.class)));

        getTabHost().addTab(facebookFriend);
        getTabHost().addTab(followingFriend);
        getTabHost().addTab(followerFriend);

        //다른 애들 초기
        for(int i=0; i<tab.getTabWidget().getChildCount();i++){
            LinearLayout relativeLayout = (LinearLayout) tab.getTabWidget().getChildAt(i);
            tab.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
            TextView tv = (TextView) relativeLayout.getChildAt(1);
            tv.setTextColor(Color.parseColor("#07B3C5"));
            tv.setTypeface(Typeface.SANS_SERIF);
            tv.setGravity(Gravity.CENTER);
        }
        //선택받은 애 색상
        tab.getTabWidget().getChildAt(tab.getCurrentTab()).setBackgroundColor(Color.parseColor("#07B3C5"));
        LinearLayout relativeLayout = (LinearLayout) tab.getTabWidget().getChildAt(tab.getCurrentTab());
        TextView tv = (TextView) relativeLayout.getChildAt(1);
        tv.setTextColor(Color.parseColor("#ffffff"));
        tv.setTypeface(Typeface.SANS_SERIF);
        tv.setGravity(Gravity.CENTER);

        SelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), GoingMonkeyActivity.class);
                startActivity(intent);
                finish();
            }
        });
        BackBtn = (ImageView)this.findViewById(R.id.BackBtn);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        searchView = (SearchView) this.findViewById(R.id.searchView);
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        if (searchPlate!=null) {
            //searchPlate.setBackgroundColor(Color.DKGRAY);
            int searchTextId = searchPlate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            TextView searchText = (TextView) searchPlate.findViewById(searchTextId);
            if (searchText!=null) {
                searchText.setTextColor(Color.parseColor("#979797"));
                searchText.setTypeface(Typeface.SANS_SERIF);
                //searchText.setHintTextColor(Color.WHITE);
            }
        }

    }

}
