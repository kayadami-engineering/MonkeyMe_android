package it.meenie.monkeymeandroid;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import it.neokree.monkeymeandroid.R;

/**
 * Created by MacMeenie on 15. 2. 23..
 */
public class FacebookFriend extends Activity{
    ArrayList<MyFacebookFriends> ffa = new ArrayList<MyFacebookFriends>();
    GridView gridView;
    Activity act = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_facebook_friends);
        ffa.add(new MyFacebookFriends(R.drawable.friend1, "Seungwon Cha"));
        ffa.add(new MyFacebookFriends(R.drawable.friend2, "Gianna Jun"));
        ffa.add(new MyFacebookFriends(R.drawable.friend3, "Yuri Sung"));
        ffa.add(new MyFacebookFriends(R.drawable.friend4, "Taeguen Gwak"));
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new gridAdapter());
    }
    class MyFacebookFriends{
        int img;
        String name;
        MyFacebookFriends(int img, String name){
            this.img = img;
            this.name = name;
        }
        int getImg(){
            return img;
        }
        String getName(){
            return name;
        }
    }

    public class gridAdapter extends BaseAdapter {

        LayoutInflater inflater;

        public gridAdapter() {

            inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return ffa.size();    //그리드뷰에 출력할 목록 수
        }

        @Override
        public Object getItem(int position) {
            return ffa.get(position);    //아이템을 호출할 때 사용하는 메소드
        }

        @Override
        public long getItemId(int position) {
            return position;    //아이템의 아이디를 구할 때 사용하는 메소드
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null) {

                convertView = inflater.inflate(R.layout.custom_grid_friend_list, parent, false);

            }


            ImageView imageView = (ImageView) convertView.findViewById(R.id.Photo);

            TextView textView = (TextView) convertView.findViewById(R.id.FriendNameTxt);


            imageView.setImageResource(ffa.get(position).getImg());

            textView.setText(ffa.get(position).getName());


            convertView.setLayoutParams( new GridView.LayoutParams(getCellWidthDP(), getCellWidthDP()+getCellWidthDP()/4));



            return convertView;

        }
        private int getCellWidthDP() {
            int width = getResources().getDisplayMetrics().widthPixels - 4;
            int cellWidth = width / 3;
            return cellWidth;
        }



    }

}
