package it.meenie.monkeymeandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import it.meenie.monkeymeandroid.ClickPhotoActivity;
import it.neokree.monkeymeandroid.R;

/**
 * Created by neokree on 16/12/14.
 */
public class PhotoTabFragment extends Fragment{

//이미지 배열 선언
    ArrayList<MyPhoto> picArr = new ArrayList<MyPhoto>();
    GridView gridView;
    gridAdapter adapter;
    Activity act;

    PhotoTabFragment(Activity act){
        this.act = act;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View convertView = inflater.inflate(R.layout.ui_picture,container, false);
        /*TextView text = new TextView(container.getContext());
        text.setText("Fragment content");
        text.setGravity(Gravity.CENTER);

*/
        Bitmap bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.word);
        Bitmap bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.photosample);


        picArr.add(new MyPhoto(bm1,1));
        picArr.add(new MyPhoto(bm2,0));


        gridView = (GridView) convertView.findViewById(R.id.gridView);
        adapter = new gridAdapter(inflater);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(act, ClickPhotoActivity.class);
                //ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //picArr.get(position).getBm().compress(Bitmap.CompressFormat.JPEG, 100, stream);
                //byte[] b = stream.toByteArray();
                    intent.putExtra("Image","딸기빙수");
                    startActivityForResult(intent, 1);
                    //act.finish();

            }
        });

        return convertView;
    }
    class MyPhoto{
        Bitmap bm;
        int lock; //0:잠금 1:안잠금
        MyPhoto(Bitmap bm, int lock){
            this.bm = bm;
            this.lock = lock;
        }
        public int getLock(){
            return lock;
        }
        public Bitmap getBm(){
            return bm;
        }
    }

    public class gridAdapter extends BaseAdapter {

        LayoutInflater inflater;



        public gridAdapter(LayoutInflater inflater) {

            this.inflater = inflater;

        }



        @Override

        public int getCount() {

            return picArr.size();    //그리드뷰에 출력할 목록 수

        }



        @Override

        public Object getItem(int position) {

            return picArr.get(position);    //아이템을 호출할 때 사용하는 메소드

        }



        @Override

        public long getItemId(int position) {

            return position;    //아이템의 아이디를 구할 때 사용하는 메소드

        }



        @Override

        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null) {

                convertView = inflater.inflate(R.layout.custom_grid_photo, parent, false);

            }
            ImageView imageView = (ImageView) convertView.findViewById(R.id.Photo);

            imageView.setImageBitmap(picArr.get(position).getBm());

            ImageView lock = (ImageView) convertView.findViewById(R.id.Lock);

            if(picArr.get(position).getLock()==1){
                lock.setVisibility(View.INVISIBLE);
            }

            convertView.setLayoutParams( new GridView.LayoutParams(getCellWidthDP(), getCellWidthDP()));



            return convertView;

        }
        private int getCellWidthDP() {
            int width = getResources().getDisplayMetrics().widthPixels - 4;
            int cellWidth = width / 3;
            return cellWidth;
        }



    }
}



