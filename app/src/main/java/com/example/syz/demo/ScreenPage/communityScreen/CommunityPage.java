package com.example.syz.demo.screenPage.communityScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.syz.demo.R;
import com.example.syz.demo.adapter.CommunityAdapter;
import com.example.syz.demo.screenPage.communityScreen.community.Community;
import com.example.syz.demo.screenPage.communityScreen.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

public class CommunityPage extends Fragment implements View.OnClickListener{

    private RecyclerView recyclerView;
    private View search_bar;
    private List<Community> communityList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.community_page, container, false);
        initCommunity();
        recyclerView = (RecyclerView) view.findViewById(R.id.communiity_item_recycleView);
        search_bar = (View) view.findViewById(R.id.search_bar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        CommunityAdapter adapter = new CommunityAdapter(getContext(), communityList);
        recyclerView.setAdapter(adapter);
        search_bar.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_bar:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
        }
    }

    private void initCommunity() {
        Community item1 = new Community("http://ww1.sinaimg.cn/large/0077HGE3ly1g0e24u79azj30jg0jg75r.jpg", "社会新鲜事",
                "禁止发布含有暴力，血腥，政治映射，黄色...");
        communityList.add(item1);
        Community item2 = new Community("http://ww1.sinaimg.cn/large/0077HGE3ly1g0hcehncctj30go0bo438.jpg", "萌宠",
                "萌宠集中营，萌的一脸血:...");
        communityList.add(item2);
        Community item3 = new Community("http://ww1.sinaimg.cn/large/0077HGE3ly1g0hcgdlps7j30sg0sgk3d.jpg", "搞笑视频",
                "爆笑视频让你笑得飞起...");
        communityList.add(item3);
        Community item4 = new Community("http://ww1.sinaimg.cn/large/0077HGE3ly1g0hcgogrmrj30r50sgwke.jpg", "女生萌妹",
                "想看女神萌妹吗？来本版块是你最明智的选择...");
        communityList.add(item4);
        Community item5 = new Community("http://ww1.sinaimg.cn/large/0077HGE3ly1g0hch0z701j30gh0c9gma.jpg", "闲人诗社",
                "百思才子聚集地...");
        communityList.add(item5);
        Community item6 = new Community("http://ww1.sinaimg.cn/large/0077HGE3ly1g0hch8rmqfj30sg0il779.jpg", "影视分享",
                "观影爱好者欢乐多！");
        communityList.add(item6);
        Community item7 = new Community("http://ww1.sinaimg.cn/large/0077HGE3ly1g0hchiwq2vj30sg0lc49u.jpg", "知识分享",
                "生活小技巧，冷门知识，科普知识，想学习...");
        communityList.add(item7);
        Community item8 = new Community("http://ww1.sinaimg.cn/large/0077HGE3ly1g0hchq2sa5j30m80gjwh1.jpg", "原创段子手",
                "原创段子手聚集地，我们拼段子是认真的！");
        communityList.add(item8);
        Community item9 = new Community("http://ww1.sinaimg.cn/large/0077HGE3ly1g0hchxrykgj30rs0ijq7o.jpg", "吃鸡",
                "大吉大利，今晚吃鸡！");
        communityList.add(item9);
        Community item10 = new Community("http://ww1.sinaimg.cn/large/0077HGE3ly1g0hci4jm61j30rs0rsn2s.jpg", "牛人集锦",
                "无形装逼最致命！在这里你能看到各种民间...");
        communityList.add(item10);
        Community item11 = new Community("http://ww1.sinaimg.cn/large/0077HGE3ly1g0hciafmitj30lw0ms78f.jpg", "搞笑图片",
                "爆笑中体味多彩人生...");
        communityList.add(item11);
        Community item12 = new Community("http://ww1.sinaimg.cn/large/0077HGE3ly1g0hcih2n1cj30dw0jggmr.jpg", "汽车",
                "车友聚集地，汽车相关一手掌握！");
        communityList.add(item12);
        Community item13 = new Community("http://ww1.sinaimg.cn/large/0077HGE3ly1g0hciomjzdj30hs0dcdg7.jpg", "情感社区",
                "记忆中的很多东西，都会因岁月漂移而渐渐...");
        communityList.add(item13);
        Community item14 = new Community("http://ww1.sinaimg.cn/large/0077HGE3ly1g0hcj0c9ztj30sg0gvjwv.jpg", "创意脑洞",
                "萌宠集中营，萌的一脸血:...");
        communityList.add(item14);
        Community item15 = new Community("http://ww1.sinaimg.cn/large/0077HGE3ly1g0hcjanp5tj30rs0i7tat.jpg", "互动区",
                "这里我们素未平生，互相各不相识，与我们...");
        communityList.add(item15);
        Community item16 = new Community("http://ww1.sinaimg.cn/large/0077HGE3ly1g0hcjhv94xj30sg0hsn0m.jpg", "音乐汇",
                "这里的音乐最懂你的心～～");
        communityList.add(item16);
        Community item17 = new Community("http://ww1.sinaimg.cn/large/0077HGE3ly1g0hcjp56tkj31100q6tdy.jpg", "Gif专区",
                "爆笑Gif，搞笑/奇葩/逗比的才艺展示区");
        communityList.add(item17);
        Community item18 = new Community("http://ww1.sinaimg.cn/large/0077HGE3ly1g0hch0z701j30gh0c9gma.jpg", "游戏",
                "最新游戏资讯，全平台全网游资讯发布！");
        communityList.add(item18);
        Community item19 = new Community("http://ww1.sinaimg.cn/large/0077HGE3ly1g0e24u79azj30jg0jg75r.jpg", "美食频道",
                "美食大杂烩！");
        communityList.add(item19);


    }
}
