package com.example.syz.demo.screenPage.attentionScreen;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.syz.demo.R;
import com.example.syz.demo.adapter.VideoAdapter;
import com.example.syz.demo.util.GlideCircleTransform;
import com.example.syz.demo.util.VideoData;
import com.example.syz.demo.util.mHttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AttentionPage extends Fragment {
    private static final int UPDATE = 4;
    private static final int REUPDATE = 5;
    private static final int Attention=6;
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
    ImageView image1,image2,image3,image4,image5;
    TextView name1,name2,name3,name4,name5;

    private String username1,username2,username3,username4,username5;
    private String pic1,pic2,pic3,pic4,pic5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.attention_page, container, false);
        sendOkHttpRequest("http://baobab.kaiyanapp.com/api/v4/tabs/selected");
        videoRecyclerView =(RecyclerView)view.findViewById(R.id.attention_recycleview);
        image1=(ImageView) view.findViewById(R.id.attention_usertou1);
        image2=(ImageView) view.findViewById(R.id.attention_usertou2);
        image3=(ImageView) view.findViewById(R.id.attention_usertou3);
        image4=(ImageView) view.findViewById(R.id.attention_usertou4);
        image5=(ImageView) view.findViewById(R.id.attention_usertou5);

        name1=(TextView) view.findViewById(R.id.attention_username1);
        name2=(TextView) view.findViewById(R.id.attention_username2);
        name3=(TextView) view.findViewById(R.id.attention_username3);
        name4=(TextView) view.findViewById(R.id.attention_username4);
        name5=(TextView) view.findViewById(R.id.attention_username5);
        sendOkHttpRequestAttention("https://www.apiopen.top/satinGodApi?type=1&page=1");

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
    private void sendOkHttpRequestAttention(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder()
                            .url(url)
                            .build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    parseJsonAttentin(responseData);
                    Log.d("jkjk",responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJsonAttentin(String jsonData){
        try{
            JSONObject jsonObject=new JSONObject(jsonData);
            JSONArray jsonArray=jsonObject.getJSONArray("data");
            JSONObject json1=jsonArray.getJSONObject(1);
            username1=json1.getString("username");
            pic1=json1.getString("header");
            Log.d("name",pic1);
            JSONObject json2=jsonArray.getJSONObject(2);
            username2=json2.getString("username");
            pic2=json2.getString("header");

            JSONObject json3=jsonArray.getJSONObject(3);
            username3=json3.getString("username");
            pic3=json3.getString("header");

            JSONObject json4=jsonArray.getJSONObject(4);
            username4=json4.getString("username");
            pic4=json4.getString("header");

            JSONObject json5=jsonArray.getJSONObject(5);
            username5=json5.getString("username");
            pic5=json5.getString("header");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.what = Attention;
                    handler.sendMessage(message);
                }
            }).start();


        }catch (Exception e){
            e.printStackTrace();
        }


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
            Context mContext = null;
            switch (message.what) {
                case UPDATE:
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    videoRecyclerView.setLayoutManager(layoutManager);
                    videoAdapter = new VideoAdapter(mVideo);
                    videoRecyclerView.setAdapter(videoAdapter);
                    break;
                case Attention:
                    name1.setText(username1);
                    Glide.with(AttentionPage.this).load(pic1).transform(new GlideCircleTransform(mContext))
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(image1);
                    name2.setText(username2);
                    Glide.with(AttentionPage.this).load(pic2).transform(new GlideCircleTransform(mContext))
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(image2);
                    name3.setText(username3);
                    Glide.with(AttentionPage.this).load(pic3).transform(new GlideCircleTransform(mContext))
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(image3);
                    name4.setText(username4);
                    Glide.with(AttentionPage.this).load(pic4).transform(new GlideCircleTransform(mContext))
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(image4);
                    name5.setText(username5);
                    Glide.with(AttentionPage.this).load(pic5).transform(new GlideCircleTransform(mContext))
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(image5);
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
