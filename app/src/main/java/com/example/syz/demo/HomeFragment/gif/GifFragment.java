package com.example.syz.demo.homeFragment.gif;

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

import com.example.syz.demo.util.Gif;
import com.example.syz.demo.adapter.GifAdapter;
import com.example.syz.demo.util.mHttpUtil;
import com.example.syz.demo.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GifFragment extends Fragment {
    private static final int UPDATE = 2;
    private List<Gif> mGif = new ArrayList<>();
    private RecyclerView gifRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    GifAdapter gifAdapter;
    private StringBuilder stringBuilder;
    private String[] text;
    private String[] type;
    private String[] username;
    private String[] header;
    private int[] comment;
    private String[] top_commentsContent;
    private String[] top_commentsHeader;
    private String[] top_commentsName;
    private String[] gifImage;
    private String[] thumb;
    private int[] up;
    private int[] down;
    private int[] forward;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gif_fragment, container, false);
        stringBuilder = new StringBuilder("https://www.apiopen.top/satinGodApi?type=4&page=");
        sendRequestWithOKHttp(stringBuilder.append("1").toString());
        gifRecyclerView = (RecyclerView) view.findViewById(R.id.gif_recycler);
        return view;
    }
    public void refresh(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Random random = new Random();
                        int raNum = random.nextInt(9)+1;
                        sendRequestWithOKHttp(stringBuilder.append(String.valueOf(raNum)).toString());
                        stringBuilder.deleteCharAt(stringBuilder.length()-1);
                        gifAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

            }

        }).start();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.gif_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case UPDATE:
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    gifRecyclerView.setLayoutManager(layoutManager);
                    gifAdapter = new GifAdapter(mGif);
                    gifRecyclerView.setAdapter(gifAdapter);
                    break;
                default:
                    break;
            }
        }
    };

    private void parseJson (String jsonData){
        mGif.clear();
        try {
            text = new String[20];
            type = new String[20];
            username = new String[20];
            header = new String[20];
            comment = new int[20];
            top_commentsContent = new String[20];
            top_commentsHeader = new String[20];
            top_commentsName = new String[20];
            up = new int[20];
            down = new int[20];
            forward = new int[20];
            gifImage = new String[20];
            thumb = new String[20];
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                text[i] = json.getString("text");
                type[i] = json.getString("type");
                username[i] = json.getString("username");
                header[i] = json.getString("header");
                comment[i] = json.getInt("comment");
                top_commentsContent[i] = json.getString("top_commentsContent");
                top_commentsHeader[i] = json.getString("top_commentsHeader");
                top_commentsName[i] = json.getString("top_commentsName");
                up[i] = json.getInt("up");
                down[i] = json.getInt("down");
                forward[i] = json.getInt("forward");
                thumb[i] = json.getString("thumbnail");
                gifImage[i] = json.getString("gif");
                Gif gifC = new Gif();
                gifC.setText(text[i]);
                gifC.setType(type[i]);
                gifC.setUsername(username[i]);
                gifC.setHeader(header[i]);
                gifC.setComment(comment[i]);
                gifC.setTop_commentsContent(top_commentsContent[i]);
                gifC.setTop_commentsHeader(top_commentsHeader[i]);
                gifC.setTop_commentsName(top_commentsName[i]);
                gifC.setUp(up[i]);
                gifC.setDown(down[i]);
                gifC.setForward(forward[i]);
                gifC.setThumbnail(thumb[i]);
                gifC.setGifImage(gifImage[i]);
                mGif.add(gifC);
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.what = UPDATE;
                    handler.sendMessage(message);
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void sendRequestWithOKHttp (final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mHttpUtil.sendOKHttpRequest(url, new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        parseJson(responseData);
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("haha", "error:" + e);
                    }
                });
            }
        }).start();
    }

}
