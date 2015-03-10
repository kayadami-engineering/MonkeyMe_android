package it.meenie.monkeymeandroid;

import android.app.Activity;
import android.content.Intent;
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
 * Created by MacMeenie on 15. 2. 26
 * Refactoring at 15. 3. 3
 * This Activity is for clicked photo page in profile page - photo tab.
 * When user clicks a photo item, they can see information of the photo, edit privacy level, share with another app, and delete.
 * Associated with ui_click_photo.xml and custom_popup_photo_menu.xml
 */
public class ClickPhotoActivity extends Activity {
    /** variable
     * BackBtn : button has a event going to prev step(located at the top-left,@drawable/back)
     * PhotoEditBtn : button has a event showing popup menu(located at the top-right,@drawable/photoedic)
     * PhotoImg : view for showing photo
     * KeywordTxt : view for showing keyword
     * PublicBtn : button for setting privacy mode to public (in popup)
     * SecretBtn : button for setting privacy mode to secret (in popup)
     * FriendsBtn : button for setting privacy mode to friends (in popup)
     * ShareBtn : button for sharing photo with another app (in popup)
     * DeleteBtn : button for deleting photo (in popup)
     * CancelBtn : button for closing popup
     * PhotoEditPopup : view about popup editor
     * PhotoEditBox : window for popup editor
     * photoEditClick : for check popup window (true:showing / false:closing)
     * privacy : for check privacy mode (-1:secret / 0:friends / 1:public)
     * */
    ImageView BackBtn, PhotoEditBtn, PhotoImg;
    TextView KeywordTxt, PublicBtn, SecretBtn, FriendsBtn, ShareBtn, DeleteBtn;
    LinearLayout CancelBtn;
    View PhotoEditPopup;
    PopupWindow PhotoEditBox;
    boolean photoEditClick;
    int privacy = 0; //default privacy mode is friends
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_click_photo);
        /* connect with layout component */
        BackBtn = (ImageView) this.findViewById(R.id.BackBtn);
        PhotoEditBtn = (ImageView) this.findViewById(R.id.PhotoEditBtn);
        PhotoImg = (ImageView) findViewById(R.id.PhotoView);
        KeywordTxt = (TextView) findViewById(R.id.KeywordTxt);

        photoEditClick = false; //default popup window status is close
        /* 수정할 것 : 이전 액티비티에서 정보 받아오기 -> 네트워크에서 받아오기 */
        Intent intent = getIntent(); //이전 액티비티로부터 정보 받아오기
        String keyword = intent.getStringExtra("Image"); //이전 엑티비티로부터 받아온 키워드
        PhotoImg.setImageResource(R.drawable.photosample); //사진 설정
        KeywordTxt.setText(keyword); //키워드 설정

        /* set event */
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 네트워크 연결 : 변경된 정보(공개 모드/삭제 여부) 서버에 저장 및 적용할 것 */
                setResult(1); //정상 종료
                finish(); //finish ClickPhotoActivity
            }
        });
        PhotoEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!photoEditClick){
                    /* 팝업이 닫혀있는 경우, 팝업 열기 */
                    photoEditClick = true; //팝업 상태 : 열림
                    photoEditPopupWindow(); //팝업 윈도우 띄우기
                }

            }
        });
    }
    /* PhotoEditPopupWindow() : open and set popup window menu */
    private void photoEditPopupWindow() {
        int width, height; //size of popup window
        try {
            /*connect with layout component */
            CancelBtn = (LinearLayout)PhotoEditPopup.findViewById(R.id.CancelBtn); //로그인 창 닫기 버튼
            PublicBtn = (TextView) PhotoEditPopup.findViewById(R.id.PublicBtn);
            SecretBtn = (TextView) PhotoEditPopup.findViewById(R.id.SecretBtn);
            FriendsBtn = (TextView) PhotoEditPopup.findViewById(R.id.FriendsBtn);
            /* get divice pixels */
            DisplayMetrics displayMetrics = new DisplayMetrics();
            this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            width = displayMetrics.widthPixels;
            height = displayMetrics.heightPixels;
            /* set popup window */
            PhotoEditPopup = View.inflate(this,R.layout.custom_popup_photo_menu,null);
            PhotoEditBox = new PopupWindow(PhotoEditPopup, width, height,true);
            /* show popup window */
            PhotoEditBox.showAtLocation(PhotoEditPopup, Gravity.NO_GRAVITY, 0, 0);
            /* set event */
            CancelBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View V)
                {
                    photoEditClick = false; //팝업 상태 : 닫힘
                    PhotoEditBox.dismiss(); //팝업 종료
                }
            });
            /* set privacy mode listener */
            View.OnTouchListener listener = new View.OnTouchListener(){
                @Override
                public boolean onTouch(View v, MotionEvent m){
                    switch(v.getId()){
                        case R.id.SecretBtn :
                            privacy = -1;
                            SecretBtn.setPressed(true); //파란색
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
                            PublicBtn.setPressed(true); //파란색
                            break;

                    }
                    return true;
                }
            };
            /* connect with listener */
            PublicBtn.setOnTouchListener(listener);
            SecretBtn.setOnTouchListener(listener);
            FriendsBtn.setOnTouchListener(listener);

            FriendsBtn.setPressed(true); //default privacy mode is friends

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
