package com.example.syz.demo.screenPage.communityScreen.community;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.syz.demo.R;
import com.example.syz.demo.adapter.CFragmentAdapter;


import java.util.ArrayList;
import java.util.List;


public class CommunityInfoActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ImageView communityInfoImg;
    private ImageView communityBackImg;
    private TextView communityInfoTitle;
    private TextView communityInfoText;
    private View communityBack;
    private ViewPager mViewPage;
    private TextView tab1, tab2,tab3;
    private ImageView lineTab;
    private int moveOne = 0;
    private boolean isScrolling = false;
    private boolean isBackScrolling = false;
    private long startTime = 0;
    private long currentTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_info_activity);

        initView();
        initLineImage();
    }

    private void initLineImage() {
        /**
         * 获取屏幕高度
         */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;

        /**
         * 重新设置下划线的宽度
         */
        ViewGroup.LayoutParams lp = lineTab.getLayoutParams();
        lp.width = screenW/12;
        lineTab.setLayoutParams(lp);

        moveOne = screenW/3;  //滑动一个页面的距离
    }

    private void initView() {

        Intent intent = getIntent();
        communityInfoImg = (ImageView) findViewById(R.id.community_info_img);
        communityBackImg = (ImageView) findViewById(R.id.community_back_img);
        communityInfoTitle = (TextView) findViewById(R.id.community_info_title);
        communityInfoText = (TextView) findViewById(R.id.community_info_text);
        communityBack = (View) findViewById(R.id.community_back);
        mViewPage = (ViewPager) findViewById(R.id.community_viewpage);
        tab1 = (TextView) findViewById(R.id.community_tab1);
        tab2 = (TextView) findViewById(R.id.community_tab2);
        tab3 = (TextView) findViewById(R.id.community_tab3);
        lineTab = (ImageView) findViewById(R.id.community_line_tab);

        Glide.with(getApplicationContext()).load(intent.getStringExtra("img")).into(communityInfoImg);
        communityInfoTitle.setText(intent.getStringExtra("title"));
        communityInfoText.setText(intent.getStringExtra("text"));
        communityBackImg.setColorFilter(Color.WHITE);

        Fragment1 fragment1 = new Fragment1();
        Fragment2 fragment2 = new Fragment2();
        Fragment3 fragment3 = new Fragment3();
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        CFragmentAdapter fragmentAdapter = new CFragmentAdapter(getSupportFragmentManager(), fragmentList);
        mViewPage.setAdapter(fragmentAdapter);

        tab1.setTextColor(Color.BLACK);
        tab2.setTextColor(Color.GRAY);
        tab3.setTextColor(Color.GRAY);

        mViewPage.setCurrentItem(0);
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        communityBack.setOnClickListener(this);

        mViewPage.setOnPageChangeListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case 1:
                isScrolling = true;
                isBackScrolling = false;
                break;
            case 2:
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
    public void onPageScrolled(int position, float positiOffset, int positionOffsetPixels) {
        currentTime = System.currentTimeMillis();
        if (isScrolling && (currentTime - startTime) > 200) {
            movePositionX(position, moveOne * positiOffset);
            startTime = currentTime;
        }
        if (isBackScrolling) {
            movePositionX(position, 0 );
        }
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                tab1.setTextColor(Color.BLACK);
                tab2.setTextColor(Color.GRAY);
                tab3.setTextColor(Color.GRAY);
                movePositionX(0, 0);
                break;
            case 1:
                tab1.setTextColor(Color.GRAY);
                tab2.setTextColor(Color.BLACK);
                tab3.setTextColor(Color.GRAY);
                movePositionX(1, 0);
                break;
            case 2:
                tab1.setTextColor(Color.GRAY);
                tab2.setTextColor(Color.GRAY);
                tab3.setTextColor(Color.BLACK);
                movePositionX(2, 0);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.community_back:
                finish();
                break;
            case R.id.community_tab1:
                mViewPage.setCurrentItem(0);
                break;
            case R.id.community_tab2:
                mViewPage.setCurrentItem(1);
                break;
            case R.id.community_tab3:
                mViewPage.setCurrentItem(2);
                break;
            default:
                break;
        }
    }

    /**
     * 下划线跟随手指的滑动而移动
     * @param toPosition 要滑动到的tab序号（0，1，2）
     * @param positionOffsetPixels 滑动的距离
     */
    private void movePositionX(int toPosition, float positionOffsetPixels) {
        float curTranslationX = lineTab.getTranslationX();
        float toPositionX = moveOne * toPosition + positionOffsetPixels;
        ObjectAnimator animator = ObjectAnimator.ofFloat(lineTab, "translationX", curTranslationX, toPositionX);
        animator.setDuration(500);
        animator.start();
    }
}
