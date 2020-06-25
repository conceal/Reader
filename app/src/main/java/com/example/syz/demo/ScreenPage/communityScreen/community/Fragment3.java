package com.example.syz.demo.screenPage.communityScreen.community;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.syz.demo.R;
import com.example.syz.demo.adapter.Fragment1Adapter;
import com.example.syz.demo.util.HttpUtil;
import com.example.syz.demo.util.Page1Data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Fragment3 extends Fragment {

    private RecyclerView recyclerView;
    private List<Page1Data> dataList = new ArrayList<>();
    private String TAG = "Fragment1";
    private String url = "https://www.apiopen.top/satinGodApi?type=1&page=1";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstancdState) {
        View view = inflater.inflate(R.layout.page3, null);

        requestData();
        recyclerView = (RecyclerView) view.findViewById(R.id.page1_item);
        return view;
    }


    private void requestData() {
        dataList.clear();
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resourceData = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(resourceData);
                    if (jsonObject.getString("msg").equals("成功!")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String type = jsonObject1.getString("type");
                            String header = jsonObject1.getString("header");
                            String userName = jsonObject1.getString("username");
                            String text = jsonObject1.getString("text");
                            String image = jsonObject1.getString("image");
                            String gif = jsonObject1.getString("gif");
                            int good_count = jsonObject1.getInt("up");
                            int commmity_count = jsonObject1.getInt("comment");
                            int share_count = jsonObject1.getInt("forward");
                            Page1Data data = new Page1Data(type, header, userName, text, gif, image, good_count, commmity_count, share_count);
                            dataList.add(data);
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }

                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        });
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    Fragment1Adapter adapter = new Fragment1Adapter(getContext(), dataList);
                    recyclerView.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        }
    };
}