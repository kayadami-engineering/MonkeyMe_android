package it.meenie.monkeymeandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.meenie.monkeymeandroid.ExpandableListView.AnimatedExpandableListView;
import it.meenie.monkeymeandroid.anim.CloseAnimation;
import it.meenie.monkeymeandroid.anim.ExpandAnimation;
import it.neokree.monkeymeandroid.R;

/**
 * Created by neokree on 30/12/14.
 */
public class MainActivity extends Activity {
    ArrayList<MyList> feeds; //feed List
    ArrayList<MenuList> menu;
    List<GroupItem> items; //친구 리스트
    ImageView ShopBtn, MenuBtn, FriendListBtn;
    Button MonkeyBtn, RandomBtn;
    TextView UserName;
    ExampleAdapter exListAdapter;
    AnimatedExpandableListView listView;
    SearchView searchView;
    ViewGroup ProfileBar;

    /* slide menu */
    private DisplayMetrics metrics;
    private LinearLayout slidingPanel;
    private LinearLayout leftMenuPanel;
    private RelativeLayout rightMenuPanel;
    private FrameLayout.LayoutParams slidingPanelParameters;
    private FrameLayout.LayoutParams leftMenuPanelParameters;
    private FrameLayout.LayoutParams rightMenuPanelParameters;
    private int panelWidth;
    private static boolean isLeftExpanded;
    public static boolean isRightExpanded;
    private ImageView bt_left, bt_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_home);
        //사용자 이름 설정
        UserName = (TextView)findViewById(R.id.EditTxt);
        UserName.setText("Hyekyo Song");

        //친구 리스트 추가
        feeds = new ArrayList<MyList>();
        //MyList newFeed;
        feeds.add(new MyList(3, "게임버튼")); // 게임 버튼
        feeds.add(new MyList(1, "내 턴")); // 내 턴
        feeds.add(new MyList(0, "Seungwon Cha", R.drawable.friend1));
        feeds.add(new MyList(0, "Gianna Jun", R.drawable.friend2));
        feeds.add(new MyList(2, "친구들 턴")); // 친구들 턴
        feeds.add(new MyList(-1, "Yuri Sung",R.drawable.friend3));
        feeds.add(new MyList(-1, "Taeguen Gwak",R.drawable.friend4));
        //어댑터 생성
        final MyFeedAdapter adapter = new MyFeedAdapter(this, feeds);
        //리스트뷰
        ListView feedList = (ListView)findViewById(R.id.HomeList);
        //리스트뷰에 어트리뷰트 추가
        setListViewAttributes(feedList);
        //어댑터 설정
        feedList.setAdapter(adapter);
        //내가 게임할 차례의 친구 클릭시
        feedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) adapter.getItem(position);
                if(adapter.getItemViewType(position)==0){
                    Intent intent = new Intent(getBaseContext(), GuessActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


        //사이드 메뉴 리스트 추가
        menu = new ArrayList<MenuList>();
        menu.add(new MenuList(2,"Inbox"));
        menu.add(new MenuList(3,"Rankings"));
        menu.add(new MenuList(4,"Add your monkey"));
        menu.add(new MenuList(1,"Shop"));
        menu.add(new MenuList(6,"Settings"));
        menu.add(new MenuList(7,"Help"));
        ProfileBar = (ViewGroup)findViewById(R.id.ProfileBar);
        //어댑터 생성
        MenuAdapter menuAdapter = new MenuAdapter(this, menu);
        //리스트뷰
        ListView leftsidemenu = (ListView)findViewById(R.id.LeftMenuList);

        //어댑터 설정
        leftsidemenu.setAdapter(menuAdapter);
        //본인 프로필 클릭시
        ProfileBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), IconTabActivity.class);
                startActivityForResult(intent,RESULT_OK);
            }
        });

        //친구 리스트
        items = new ArrayList<GroupItem>();
        GroupItem category1 = new GroupItem();
        GroupItem category2 = new GroupItem();
        GroupItem category3 = new GroupItem();
        category1.title = "페이스북 친구";
        category1.items.add(new ChildItem("Seungwon Cha",R.drawable.friend1));
        category1.items.add(new ChildItem("Taeguen Gwak",R.drawable.friend4));
        items.add(category1);
        category2.title = "팔로잉";
        category2.items.add(new ChildItem("Yuri Sung",R.drawable.friend3));
        items.add(category2);
        category3.title = "팔로워";
        category3.items.add(new ChildItem("Gianna Jun",R.drawable.friend2));
        items.add(category3);
        exListAdapter = new ExampleAdapter(this);
        exListAdapter.setData(items);

        listView = (AnimatedExpandableListView) findViewById(R.id.listView);
        listView.setAdapter(exListAdapter);

        // In order to show animations, we need to use a custom click handler
        // for our ExpandableListView.
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group
                // expansion/collapse.
                if (listView.isGroupExpanded(groupPosition)) {
                    listView.collapseGroupWithAnimation(groupPosition);
                } else {
                    listView.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }

        });
        //친구 목록 화살표 위치 바꾸기
        //Display arrow = getWindowManager().getDefaultDisplay();
        //int width = arrow.getWidth();
        //listView.setIndicatorBounds(width/2-50, width/2-10);


//slide
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        panelWidth = (int) ((metrics.widthPixels) * 0.75);

        bt_left = (ImageView) findViewById(R.id.Menu);
        bt_right = (ImageView) findViewById(R.id.Friends);
        bt_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        menuLeftSlideAnimationToggle();
                }
        });
       bt_right.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               menuRightSlideAnimationToggle();
           }
       });
        View ic_leftslidemenu = (View) findViewById(R.id.ic_leftslidemenu);
        // sliding view Initialize
        slidingPanel = (LinearLayout) findViewById(R.id.slidingPanel);
        slidingPanelParameters = (FrameLayout.LayoutParams) slidingPanel
                .getLayoutParams();
        slidingPanelParameters.width = metrics.widthPixels;
        slidingPanel.setLayoutParams(slidingPanelParameters);

        // left slide menu initialize
        leftMenuPanel = (LinearLayout) findViewById(R.id.leftMenuPanel);
        leftMenuPanelParameters = (FrameLayout.LayoutParams) leftMenuPanel
                .getLayoutParams();
        leftMenuPanelParameters.width = panelWidth;
        leftMenuPanel.setLayoutParams(leftMenuPanelParameters);

        // right slide menu initialize
        rightMenuPanel = (RelativeLayout) findViewById(R.id.rightMenuPanel);
        rightMenuPanelParameters = (FrameLayout.LayoutParams) rightMenuPanel
                .getLayoutParams();
        rightMenuPanelParameters.width = panelWidth;
        rightMenuPanel.setLayoutParams(rightMenuPanelParameters);

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

    void setListViewAttributes(ListView feedList) {
        if (feedList!=null){
            /*
            feedList.setCacheColorHint(0);
            feedList.setDividerHeight(0);
            feedList.setSelector(android.R.color.transparent);
            feedList.setVerticalFadingEdgeEnabled(false);
            */

        }
    }

    class MyList{
        int Type; // -1 : friends turn item 0: my turn item 1: my turn line 2: friends turn line 3: game bar
        String Name;
        int Level, Round;
        int Picture;
        Button MonkeyBtn;
        MyList (int Type) { this.Type = Type; }
        MyList(int Type, String Name)
        {
            this.Type = Type;
            this.Name = Name;
        }
        MyList(int Type, String Name, int Picture)
        {
            this.Type = Type;
            this.Name = Name;
            this.Picture = Picture;
        }
        MyList(String Name, int Level, int Round, int Picture)
        {
            this.Name = Name;
            this.Level = Level;
            this.Round = Round;
            this.Picture = Picture;
        }
        int getPicture(){
            return Picture;
        }
        void setMonkeyBtn(View v){
            MonkeyBtn = (Button) v.findViewById(R.id.MonkeyBtn);
            MonkeyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), SelectFriendActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

        }
    }
    class MenuList{
        int img;
        String text;
        MenuList(int img, String text){
            switch (img){
                case 1 :
                    img = R.drawable.shop2;
                    break;
                case 2 :
                    img = R.drawable.inbox;
                    break;
                case 3 :
                    img = R.drawable.ranking;
                    break;
                case 4 :
                    img = R.drawable.word;
                    break;
                case 5 :
                    img = R.drawable.achieve;
                    break;
                case 6 :
                    img = R.drawable.settings;
                    break;
                case 7 :
                    img = R.drawable.help;
                    break;
            }
            this.img = img;
            this.text = text;
        }
    }

    class MyFeedAdapter extends BaseAdapter {
        Context con;
        LayoutInflater inflater;
        ArrayList<MyList> arf;
        int layout;
        //생성자
        public MyFeedAdapter(Context con, ArrayList<MyList> aarf) {
            this.con = con;
            inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            arf = aarf;
        }

        //어댑터 개수 조사
        @Override
        public int getCount() {
            return arf.size();
        }

        //position 위치의 항목  반환
        @Override
        public Object getItem(int position) {
            return arf.get(position).Name;
        }
        //position 위치의 항목 아이디????
        @Override
        public long getItemId(int position) {
            return position;
        }
        //Type 받아오기
        public int getItemViewType(int position){
            return arf.get(position).Type;
        }
        //뷰 개수 반환
        public int getViewTypeCount(){
            return 4;
        }


        //각 항목의 뷰 생성 후 반환
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            int i;
            TextView name;
            ImageView picture;

            if (convertView == null) {
                i = getItemViewType(position); //type 확인
                switch (i){
                    case -1 :
                    case 0 :
                        i = R.layout.custom_turn_list_item;
                        break;
                    case 1 :
                        i = R.layout.custom_my_turn;
                        break;
                    case 2 :
                        i = R.layout.custom_friends_turn;
                        break;
                    case 3 :
                        i = R.layout.game_bar;
                        break;
                }
                convertView = inflater.inflate(i, parent, false);
            }
            i = getItemViewType(position);
            switch (i) {
                case -1 :

                case 0 :
                    name = (TextView) convertView.findViewById(R.id.FriendName);
                    name.setText(arf.get(position).Name);
                    picture = (ImageView) convertView.findViewById(R.id.Pic);
                    picture.setImageResource(arf.get(position).getPicture());
                    break;
                case 3 :
                    arf.get(position).setMonkeyBtn(convertView);
                    break;

            }
            return convertView;
        }

    }

    class MenuAdapter extends BaseAdapter {
        Context con;
        LayoutInflater inflater;
        ArrayList<MenuList> arf;
        int layout;
        //생성자
        public MenuAdapter(Context con, ArrayList<MenuList> aarf) {
            this.con = con;
            inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            arf = aarf;
        }

        //어댑터 개수 조사
        @Override
        public int getCount() {
            return arf.size();
        }

        //position 위치의 항목  반환
        @Override
        public Object getItem(int position) {
            return arf.get(position).text;
        }
        //position 위치의 항목 아이디????
        @Override
        public long getItemId(int position) {
            return position;
        }
        //img num 받아오기
        public int getItemViewType(int position){
            return arf.get(position).img;
        }
        //뷰 개수 반환
        public int getViewTypeCount(){
            return 1;
        }


        //각 항목의 뷰 생성 후 반환
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView==null) {
                convertView = inflater.inflate(R.layout.custom_menu_list_item, parent, false);
                ImageView menuImg = (ImageView) convertView.findViewById(R.id.MenuItemImg);
                menuImg.setImageResource(arf.get(position).img);
                TextView name = (TextView) convertView.findViewById(R.id.MenuItemName);
                name.setText(arf.get(position).text);
            }
            return convertView;
        }

    }
    /**
     * 좌측 메뉴 토글시 처리
     */
    void menuLeftSlideAnimationToggle() {

        if (!isLeftExpanded) {

            // networkRequestTimeLineGetNewCnt();

            isLeftExpanded = true;
            rightMenuPanel.setVisibility(View.GONE);
            leftMenuPanel.setVisibility(View.VISIBLE);

            // Expand
            new ExpandAnimation(slidingPanel, panelWidth, "left",
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.75f, 0, 0.0f, 0, 0.0f);

            // disable all of main view
            // LinearLayout viewGroup = (LinearLayout) findViewById(
            FrameLayout viewGroup = (FrameLayout) findViewById(R.id.ll_fragment)
                    .getParent();
            enableDisableViewGroup(viewGroup, false);

            // enable empty view
            ((LinearLayout) findViewById(R.id.ll_empty))
                    .setVisibility(View.VISIBLE);

            findViewById(R.id.ll_empty).setEnabled(true);
            findViewById(R.id.ll_empty).setOnTouchListener(
                    new View.OnTouchListener() {

                        @Override
                        public boolean onTouch(View arg0, MotionEvent arg1) {
                            menuLeftSlideAnimationToggle();
                            return true;
                        }
                    });

        } else {
            isLeftExpanded = false;

            // Collapse
            new CloseAnimation(slidingPanel, panelWidth,
                    TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
                    TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f, 0, 0.0f);

            // enable all of main view
            // LinearLayout viewGroup = (LinearLayout) findViewById(
            FrameLayout viewGroup = (FrameLayout) findViewById(R.id.ll_fragment)
                    .getParent();
            enableDisableViewGroup(viewGroup, true);

            // disable empty view
            ((LinearLayout) findViewById(R.id.ll_empty))
                    .setVisibility(View.GONE);
            findViewById(R.id.ll_empty).setEnabled(false);

        }
    }

    /**
     * 오른쪽 메뉴 토글에 대한 처리
     */
    public void menuRightSlideAnimationToggle() {
        if (!isRightExpanded) {
            isRightExpanded = true;
            rightMenuPanel.setVisibility(View.VISIBLE);
            leftMenuPanel.setVisibility(View.GONE);

            // Expand
            new ExpandAnimation(slidingPanel, panelWidth, "right",
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, -0.75f, 0, 0.0f, 0, 0.0f);

            FrameLayout viewGroup = (FrameLayout) findViewById(R.id.ll_fragment)
                    .getParent();
            enableDisableViewGroup(viewGroup, false);

            // enable empty view
            ((LinearLayout) findViewById(R.id.ll_empty))
                    .setVisibility(View.VISIBLE);

            findViewById(R.id.ll_empty).setEnabled(true);
            findViewById(R.id.ll_empty).setOnTouchListener(
                    new View.OnTouchListener() {

                        @Override
                        public boolean onTouch(View arg0, MotionEvent arg1) {
                            menuRightSlideAnimationToggle();
                            return true;
                        }
                    });

        } else {
            isRightExpanded = false;

            // Collapse
            new CloseAnimation(slidingPanel, panelWidth,
                    TranslateAnimation.RELATIVE_TO_SELF, -0.75f,
                    TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f, 0, 0.0f);

            // enable all of main view
            FrameLayout viewGroup = (FrameLayout) findViewById(R.id.ll_fragment)
                    .getParent();
            enableDisableViewGroup(viewGroup, true);

            // disable empty view
            ((LinearLayout) findViewById(R.id.ll_empty))
                    .setVisibility(View.GONE);
            findViewById(R.id.ll_empty).setEnabled(false);
        }
    }

    /**
     * 뷰의 동작을 제어한다. 하위 모든 뷰들이 enable 값으로 설정된다.
     *
     * @param viewGroup
     * @param enabled
     */
    public static void enableDisableViewGroup(ViewGroup viewGroup,
                                              boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);

            if (view.getId() != R.id.Menu) {
                view.setEnabled(enabled);
                if (view instanceof ViewGroup) {
                    enableDisableViewGroup((ViewGroup) view, enabled);
                }
            }
        }
    }

    protected void onActivityResult(int reqestCode, int resultCode, Intent Data){
        if(resultCode == RESULT_OK){
            //메인 엑티비티 실행시 해야될 일! 네트워크연결 정보 다시 받아오기
        }
    }

    //오른쪽 친구 리스트 코드
    public static class GroupItem {
        String title;
        List<ChildItem> items = new ArrayList<ChildItem>();
    }

    public static class ChildItem {
        String name;
        int img;
        ChildItem(String name, int img){
            this.name = name;
            this.img = img;
        }
    }

    public static class ChildHolder {
        TextView name;
        ImageView img;
    }

    public static class GroupHolder {
        TextView title;
    }

    /**
     * Adapter for our list of {@link GroupItem}s.
     */
    private class ExampleAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
        private LayoutInflater inflater;

        private List<GroupItem> items;

        public ExampleAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void setData(List<GroupItem> items) {
            this.items = items;
        }

        @Override
        public ChildItem getChild(int groupPosition, int childPosition) {
            return items.get(groupPosition).items.get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder holder;
            ChildItem item = getChild(groupPosition, childPosition);
            if (convertView == null) {
                holder = new ChildHolder();
                convertView = inflater.inflate(R.layout.custom_friend_list_item, parent, false);
                holder.name = (TextView) convertView.findViewById(R.id.FriendNameTxt);
                holder.img = (ImageView) convertView.findViewById(R.id.Pic);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }

            holder.name.setText(item.name);
            holder.img.setImageResource(item.img);

            return convertView;
        }

        @Override
        public int getRealChildrenCount(int groupPosition) {
            return items.get(groupPosition).items.size();
        }

        @Override
        public GroupItem getGroup(int groupPosition) {
            return items.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return items.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder holder;
            GroupItem item = getGroup(groupPosition);
            if (convertView == null) {
                holder = new GroupHolder();
                convertView = inflater.inflate(R.layout.custom_group_item, parent, false);
                holder.title = (TextView) convertView.findViewById(R.id.GroupName);
                convertView.setTag(holder);
            } else {
                holder = (GroupHolder) convertView.getTag();
            }

            holder.title.setText(item.title);

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int arg0, int arg1) {
            return true;
        }

    }

}
