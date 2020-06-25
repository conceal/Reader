package com.example.syz.demo.screenPage.communityScreen.search;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.syz.demo.R;
import com.example.syz.demo.adapter.SearchFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager searchViewPage;
    private EditText searchText;
    private TextView cancelText;
    private TextView cardTab;
    private TextView userTab;
    private ImageView lineTab;
    private View bindingView;
    private List<Fragment> fragmentList = new ArrayList<>();
    private int moveOne;
    private Boolean isScrolling = false;
    private Boolean isBackScrolling = false;
    private long startTime = 0;
    private long currentTime = 0;
    private SearchFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        initView();
        initLineTab();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initLineTab() {
        /**
         * 获取屏幕高度
         */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;

        /**
         * 重新设置下划线宽度
         */
        ViewGroup.LayoutParams lp = lineTab.getLayoutParams();
        lp.width = screenW/12;
        lineTab.setLayoutParams(lp);

        moveOne = screenW/2;
    }

    private void initView() {
        searchViewPage = (ViewPager) findViewById(R.id.search_viewpage);
        searchText = (EditText) findViewById(R.id.search_text);
        cancelText = (TextView) findViewById(R.id.cancel);
        cardTab = (TextView) findViewById(R.id.search_card);
        userTab = (TextView) findViewById(R.id.search_user);
        lineTab = (ImageView) findViewById(R.id.search_line);
        bindingView = (View) findViewById(R.id.bindingView);

        CardFragment cardFragment = new CardFragment();
        UserFragment userFragment = new UserFragment();
        fragmentList.add(cardFragment);
        fragmentList.add(userFragment);
        adapter = new SearchFragmentAdapter(getSupportFragmentManager(), fragmentList);
        searchViewPage.setAdapter(adapter);

        cardTab.setTextColor(Color.BLACK);
        userTab.setTextColor(Color.GRAY);
        searchViewPage.setCurrentItem(0);

        cardTab.setOnClickListener(this);
        userTab.setOnClickListener(this);
        cancelText.setOnClickListener(this);

        searchViewPage.setOnPageChangeListener(this);
        searchText.addTextChangedListener(new EditChangeListener());
        LightStatusbar();

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case 0:
                isScrolling = true;
                isBackScrolling = false;
                break;
            case 1:
                isScrolling = false;
                isBackScrolling = true;
                break;
            default:
                isScrolling = false;
                isBackScrolling = false;
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffSet, int positionOffSetPixels) {
        currentTime = System.currentTimeMillis();
        if (isScrolling && (currentTime - startTime) > 0) {
            movePositionX(position, moveOne*positionOffSet);
            startTime = currentTime;
        }
        if (isBackScrolling){
            movePositionX(position, 0);
        }

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                cardTab.setTextColor(Color.BLACK);
                userTab.setTextColor(Color.GRAY);
                movePositionX(0, 0);
                break;
            case 1:
                cardTab.setTextColor(Color.GRAY);
                userTab.setTextColor(Color.BLACK);
                movePositionX(1, 0);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_card:
                searchViewPage.setCurrentItem(0);
                break;
            case R.id.search_user:
                searchViewPage.setCurrentItem(1);
                break;
            case R.id.cancel:
                if (cancelText.getText().equals("取消")) {
                    finish();
                } else {
                    CardInfoFragment cardInfoFragment = new CardInfoFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("searchData", searchText.getText().toString());
                    cardInfoFragment.setArguments(bundle);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.card_fragment, cardInfoFragment).commit();
                    fragmentList.clear();
                    fragmentList.add(cardInfoFragment);
                    fragmentList.add(new UserFragment());
                    adapter.setmFragmentList(fragmentList);
                    adapter.notifyDataSetChanged();
                    searchViewPage.invalidate();
                }
                break;
            default:
                break;
        }
    }

    private void movePositionX(int toPosition, float positionOffSetPixels) {
        float curTranslationX = lineTab.getTranslationX();
        float toPositionX = moveOne * toPosition + positionOffSetPixels;
        ObjectAnimator animator = ObjectAnimator.ofFloat(lineTab, "translationX", curTranslationX, toPositionX);
        animator.setDuration(500);
        animator.start();
    }

    private void LightStatusbar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option =  View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        int statusBarHeight = getStatusBarHeight(this);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) bindingView.getLayoutParams();
        params.height = statusBarHeight;
        bindingView.setVisibility(View.VISIBLE);
        bindingView.setLayoutParams(params);
    }

    private void DarkStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        bindingView.setVisibility(View.GONE);
    }

    private int getStatusBarHeight(Activity activity) {
        int statusBarHeight = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }

        return statusBarHeight;
    }

    class EditChangeListener implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int after, int count) {
            if (!(TextUtils.isEmpty(searchText.getText().toString()))) {
                cancelText.setText("搜索");
                cancelText.setTextColor(Color.RED);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
