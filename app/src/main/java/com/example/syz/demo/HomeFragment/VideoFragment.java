package com.example.syz.demo.homeFragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.syz.demo.R;
import com.example.syz.demo.adapter.VideoAdapter;
import com.example.syz.demo.util.VideoData;
import com.example.syz.demo.util.mHttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class VideoFragment extends Fragment {
    private static final int UPDATE = 4;
    private static final int REUPDATE = 5;
    private List<VideoData> mVideo = new ArrayList<>();
    private RecyclerView videoRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    VideoAdapter videoAdapter;
    public String title;
    public String description;
    public int collectionCount;
    public int shareCount;
    public int replyCount;
    public String authorname;
    public String authorImg;
    public String playUrl;
    public String cover;
    public String nextUrl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_fragment, null);
        sendOkHttpRequest("http://baobab.kaiyanapp.com/api/v4/tabs/selected");
        videoRecyclerView =(RecyclerView)view.findViewById(R.id.video_recycler);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.video_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(nextUrl.equals("null")){
                    refresh("http://baobab.kaiyanapp.com/api/v4/tabs/selected");
                }else{
                    refresh(nextUrl);
                }
            }
        });
    }
    public void refresh(final String nextUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        sendOkHttpRequest(nextUrl);
                        videoAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

            }

        }).start();
    }
        public void sendOkHttpRequest(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mHttpUtil.sendOKHttpRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("haha",e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        parseJson(responseData);
                    }
                });
            }
        }).start();
    }
    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case UPDATE:
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    videoRecyclerView.setLayoutManager(layoutManager);
                    videoAdapter = new VideoAdapter(mVideo);
                    videoRecyclerView.setAdapter(videoAdapter);
                    break;
                default:
                    break;
            }
        }
    };
    private void parseJson(String jsonData){
        mVideo.clear();
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            nextUrl = jsonObject.getString("nextPageUrl");
            JSONArray jsonArray = jsonObject.getJSONArray("itemList");
            for(int i = 0 ; i < jsonArray.length() ; i++){
                JSONObject item = jsonArray.getJSONObject(i);
                String mType = item.getString("type");
                if(mType.equals("video")){
                    VideoData videoC = new VideoData();
                    JSONObject data = item.getJSONObject("data");
                    title = data.getString("title");
                    videoC.setTitle(title);
                    description = data.getString("description");
                    videoC.setDescription(description);
                    playUrl = data.getString("playUrl");
                    videoC.setPlayUrl(playUrl);
                    JSONObject consumption = data.getJSONObject("consumption");
                    collectionCount = consumption.getInt("collectionCount");
                    videoC.setCollectionCount(collectionCount);
                    shareCount = consumption.getInt("shareCount");
                    videoC.setShareCount(shareCount);
                    replyCount = consumption.getInt("replyCount");
                    videoC.setReplyCount(replyCount);
                    JSONObject author = data.getJSONObject("author");
                    authorname = author.getString("name");
                    videoC.setAuthorname(authorname);
                    authorImg = author.getString("icon");
                    videoC.setAuthorImg(authorImg);
                    JSONObject coverImg = data.getJSONObject("cover");
                    cover = coverImg.getString("feed");
                    videoC.setCover(cover);
                    mVideo.add(videoC);
                }else{
                    Log.d("haha","不是video类型");
                }
            }

            Message message = new Message();
            message.what = UPDATE;
            handler.sendMessage(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
