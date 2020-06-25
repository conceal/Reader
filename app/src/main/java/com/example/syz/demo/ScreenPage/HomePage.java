package com.example.syz.demo.screenPage;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.syz.demo.homeFragment.WordFragment;
import com.example.syz.demo.homeFragment.gif.GifFragment;
import com.example.syz.demo.homeFragment.picturePage.PictureFragment;
import com.example.syz.demo.homeFragment.VideoFragment;
import com.example.syz.demo.R;
import com.example.syz.demo.adapter.myFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager myViewPager;
    private TextView tab0, tab1, tab2, tab3;
    private ImageView line_tab;
    private ImageView translationLine;
    private int moveOne = 0;  //滑动一个页面的距离
    private boolean isScrolling = false; //正在滑动
    private boolean isBackScrolling = false; //手指离开屏幕
    private long currentTime = 0;  //此时的时间
    private long startTime = 0;   //程序开始的时间
    private Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page, container, false);

        initView(view);
        initLineImage();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tab0.setOnClickListener(this);
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        myViewPager.setOnPageChangeListener(this);
    }

    private void initLineImage() {

        //获取屏幕宽度
        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;

        //重新设置Line的宽度
        ViewGroup.LayoutParams lp = line_tab.getLayoutParams();
        lp.width = screenWidth/8;
        line_tab.setLayoutParams(lp);
        line_tab.setTranslationX(screenWidth/16);

        moveOne = screenWidth/4; //滑动一个page的距离
    }

    private void initView(View view) {
        myViewPager = (ViewPager) view.findViewById(R.id.myViewPage);

        WordFragment fragmentPage1 = new WordFragment();
        GifFragment fragmentPage2 = new GifFragment();
        PictureFragment fragmentPage3 = new PictureFragment();
        VideoFragment fragmentPage4 = new VideoFragment();

        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(fragmentPage1);
        fragmentList.add(fragmentPage2);
        fragmentList.add(fragmentPage3);
        fragmentList.add(fragmentPage4);

        myFragmentAdapter fragmentAdapter = new myFragmentAdapter(getChildFragmentManager(), fragmentList);
        myViewPager.setAdapter(fragmentAdapter);

        tab0 = (TextView) view.findViewById(R.id.tab0);
        tab1 = (TextView) view.findViewById(R.id.tab1);
        tab2 = (TextView) view.findViewById(R.id.tab2);
        tab3 = (TextView) view.findViewById(R.id.tab3);
        myViewPager.setCurrentItem(0);
        tab0.setTextColor(Color.BLACK);
        tab1.setTextColor(Color.GRAY);
        tab2.setTextColor(Color.GRAY);
        tab3.setTextColor(Color.GRAY);
        tab0.setTextSize(20);
        tab1.setTextSize(16);
        tab2.setTextSize(16);
        tab3.setTextSize(16);

        line_tab = (ImageView) view.findViewById(R.id.line_tab);
        translationLine = (ImageView) view.findViewById(R.id.translationLine);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab0:
                myViewPager.setCurrentItem(0);
                break;
            case R.id.tab1:
                myViewPager.setCurrentItem(1);
                break;
            case R.id.tab2:
                myViewPager.setCurrentItem(2);
                break;
            case R.id.tab3:
                myViewPager.setCurrentItem(3);
                break;
        }
    }

    //Page被选中时tab的状态
    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                tab0.setTextColor(Color.BLACK);
                tab1.setTextColor(Color.GRAY);
                tab2.setTextColor(Color.GRAY);
                tab3.setTextColor(Color.GRAY);
                tab0.setTextSize(20);
                tab1.setTextSize(16);
                tab2.setTextSize(16);
                tab3.setTextSize(16);
                movePositionX(0, 0);
                break;
            case 1:
                tab0.setTextColor(Color.GRAY);
                tab1.setTextColor(Color.BLACK);
                tab2.setTextColor(Color.GRAY);
                tab3.setTextColor(Color.GRAY);
                tab0.setTextSize(16);
                tab1.setTextSize(20);
                tab2.setTextSize(16);
                tab3.setTextSize(16);
                movePositionX(1, 0);
                break;
            case 2:
                tab0.setTextColor(Color.GRAY);
                tab1.setTextColor(Color.GRAY);
                tab2.setTextColor(Color.BLACK);
                tab3.setTextColor(Color.GRAY);
                tab0.setTextSize(16);
                tab1.setTextSize(16);
                tab2.setTextSize(20);
                tab3.setTextSize(16);
                movePositionX(2, 0);
                break;
            case 3:
                tab0.setTextColor(Color.GRAY);
                tab1.setTextColor(Color.GRAY);
                tab2.setTextColor(Color.GRAY);
                tab3.setTextColor(Color.BLACK);
                tab0.setTextSize(16);
                tab1.setTextSize(16);
                tab2.setTextSize(16);
                tab3.setTextSize(20);
                movePositionX(3, 0);
                break;
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

    //在真机上onPageScrolled方法的触发频率比模拟器上高了不知道多少倍，
    // 需要设定一个时间间隔，每隔了这个时间间隔才让动画执行一次
    @Override
    public void onPageScrolled(int position, float positionOffSet, int positionOffSetPixel) {

        currentTime = System.currentTimeMillis();
        if (isScrolling && (currentTime - startTime > 200)) {
            //positionOffSet为偏移量的百分比，positionOffSetPixel为偏移量的数值
            movePositionX(position, moveOne*positionOffSet);
            startTime = currentTime;
        }

        if (isBackScrolling) {
            movePositionX(position, 0);
        }
    }

    private void movePositionX(int toPosition, float positionOffSetPixle) {
        float curTranslationX = line_tab.getTranslationX();  //当前line——tab的横向位置
        float toPositionX = moveOne/4 + moveOne * toPosition + positionOffSetPixle;
        ObjectAnimator animator = ObjectAnimator.ofFloat(line_tab, "translationX", curTranslationX, toPositionX);
        animator.setDuration(500);
        animator.start();
    }

}
