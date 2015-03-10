package it.meenie.monkeymeandroid;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import it.neokree.monkeymeandroid.R;

/**
 * Created by MacMeenie on 15. 2. 25..
 */
public class ProfileEditActivity extends Activity {
    int privacy = 0; // 0: 친구 공개 -1: 비공개 1: 공개
    TextView  CancelBtn, OkBtn, PublicBtn, SecretBtn, FriendsBtn;
    ImageView PhotoEditBtn;
    LinearLayout TopPopupCloseBtn,BottomPopupCloseBtn;
    View PhotoEditPopup;
    PopupWindow PhotoEditBox;
    boolean photoEditClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_edit_profile);
        OkBtn = (TextView) this.findViewById(R.id.OkBtn);
        OkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getBaseContext(), IconTabActivity.class);
                //startActivity(intent);
                setResult(1);
                finish();
            }
        });
        CancelBtn = (TextView) this.findViewById(R.id.CancelBtn);
        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getBaseContext(), IconTabActivity.class);
                //startActivity(intent);
                setResult(1);
                finish();
            }
        });




        //몽키 사진 공개범위 기본 설정
        PublicBtn = (TextView) findViewById(R.id.PublicBtn);
        SecretBtn = (TextView) findViewById(R.id.SecretBtn);
        FriendsBtn = (TextView) findViewById(R.id.FriendsBtn);
        View.OnTouchListener listener = new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent m){
                switch(v.getId()){
                    case R.id.SecretBtn :
                        privacy = -1;
                        SecretBtn.setPressed(true);
                        FriendsBtn.setPressed(false);
                        PublicBtn.setPressed(false);
                        break;

                    case R.id.FriendsBtn :
                        privacy = 0;
                        SecretBtn.setPressed(false);
                        FriendsBtn.setPressed(true); //파란색
                        PublicBtn.setPressed(false);
                        break;

                    case R.id.PublicBtn :
                        privacy = 1 ;
                        SecretBtn.setPressed(false);
                        FriendsBtn.setPressed(false);
                        PublicBtn.setPressed(true);
                        break;

                }
                return true;
            }
        };
        PublicBtn.setOnTouchListener(listener);
        SecretBtn.setOnTouchListener(listener);
        FriendsBtn.setOnTouchListener(listener);
        FriendsBtn.setPressed(true);
        PhotoEditBtn = (ImageView) this.findViewById(R.id.PhotoEditBtn);
        PhotoEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!photoEditClick){
                    photoEditClick = true;
                    PhotoEditPopupWindow();
                }

            }
        });
    }
    private void PhotoEditPopupWindow() {
        try {
            //We need to get the instance of the LayoutInflater, use the context of this activity
            //LayoutInflater inflater = (LayoutInflater) BeginningActivity.this
            //        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            //int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getDisplayMetrics().widthPixels, dm);
            //int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getDisplayMetrics().heightPixels, dm);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;
            int height = displayMetrics.heightPixels;



            PhotoEditPopup = View.inflate(this,R.layout.custom_popup_profile_photo,null);
            // create a 300px width and 470px height PopupWindow
            PhotoEditBox = new PopupWindow(PhotoEditPopup, width, height,true);
            // display the popup in the center
            PhotoEditBox.showAtLocation(PhotoEditPopup, Gravity.NO_GRAVITY, 0, 0);
            TopPopupCloseBtn = (LinearLayout)PhotoEditPopup.findViewById(R.id.TopPopupCloseBtn); //로그인 창 닫기 버튼
            TopPopupCloseBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View V)
                {
                    photoEditClick = false;
                    PhotoEditBox.dismiss();
                }
            });
            BottomPopupCloseBtn = (LinearLayout)PhotoEditPopup.findViewById(R.id.BottomPopupCloseBtn); //로그인 창 닫기 버튼
                BottomPopupCloseBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View V)
                {
                    photoEditClick = false;
                    PhotoEditBox.dismiss();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
