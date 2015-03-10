package it.meenie.monkeymeandroid;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import it.neokree.monkeymeandroid.R;

/**
 * Created by neokree on 30/12/14.
 */
public class IconTabActivity extends ActionBarActivity implements MaterialTabListener {
    private ViewPager pager;
    private ViewPagerAdapter pagerAdapter;
    MaterialTabHost tabHost;
    private Resources res;
    ImageView backBtn;
    TextView ProfileEditBtn, UserName;
    Activity act = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icons);
        UserName = (TextView)findViewById(R.id.EditTxt);
        UserName.setText("Hyekyo Song");
        res = this.getResources();
        backBtn = (ImageView) findViewById(R.id.BackBtn);
        ProfileEditBtn = (TextView)findViewById(R.id.ProfileEditBtn);
        ProfileEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ProfileEditActivity.class);
                startActivityForResult(intent,1);
            }
        });
/*
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        */
        // init toolbar (old action bar)

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        //this.setSupportActionBar(toolbar);




        tabHost = (MaterialTabHost) this.findViewById(R.id.tabHost);
        pager = (ViewPager) this.findViewById(R.id.pager);
        // init view pager
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);
                tabHost.getCurrentTab().setIconColor(Color.parseColor("#07B3C5"));
            }
        });
        // insert all tabs from pagerAdapter data
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setIcon(getIcon(i))
                            .setTabListener(this)
            );

        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent;
                //intent = new Intent(getBaseContext(), MainActivity.class);
                //startActivity(intent);
                setResult(RESULT_OK);
                finish();
            }
        });
       tabHost.setSelectedNavigationItem(0);
       tabHost.getCurrentTab().setIconColor(Color.parseColor("#07B3C5"));


    }
    @Override
    public void onTabSelected(MaterialTab tab) {
// when the tab is clicked the pager swipe content to the tab position
        pager.setCurrentItem(tab.getPosition());
        tab.setIconColor(Color.parseColor("#07B3C5"));
    }

    @Override
    public void onTabReselected(MaterialTab tab) {
        tab.setIconColor(Color.parseColor("#07B3C5"));
    }

    @Override
    public void onTabUnselected(MaterialTab tab) {
           tab.setIconColor(Color.parseColor("#7B827E"));
    }

   private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        public Fragment getItem(int num) {
            switch (num) {
                case 0:
                    return new PhotoTabFragment(act);
                case 1:
                    return new FragmentTwo();
                case 2:
                    return new FragmentTwo();
            }
            return new FragmentTwo();
        }
        @Override
        public int getCount() {
            return 3;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0: return "tab 1";
                case 1: return "tab 2";
                case 2: return "tab 3";
                default: return null;
            }
        }
   }
    /*
    * It doesn't matter the color of the icons, but they must have solid colors
    */
    private Drawable getIcon(int position) {
        switch(position) {
            case 0:
                return res.getDrawable(R.drawable.photog);
            case 1:
                return res.getDrawable(R.drawable.gameg);
            case 2:
                return res.getDrawable(R.drawable.achieveg);
        }
        return null;
    }

}