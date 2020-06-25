package com.example.syz.demo.homeFragment.picturePage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.syz.demo.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ‘。；op on 2019/2/24.
 */

public class PictureTitleFragment extends Fragment {
    private static final int UPDATE = 2;
    RecyclerView pictureTitleRecyclerView;
    List<PictureAdapter> pictureList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.picture_title_frag,container,false);
        sendRequestWithOkHttp();
        pictureTitleRecyclerView=(RecyclerView) view.findViewById(R.id.picture_title_recycler_view);

        return view;
    }

    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder()
                            .url("https://www.apiopen.top/satinGodApi?type=3&page=3")
                            .build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    parseJSONObject(responseData);
                    Log.d("jkjk",responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case UPDATE:
                    LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
                    pictureTitleRecyclerView.setLayoutManager(layoutManager);
                    NewsAdapter adapter=new NewsAdapter(pictureList);
                    pictureTitleRecyclerView.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        }
    };

    private int getScreen(){
        DisplayMetrics dm1 = getResources().getDisplayMetrics();
        int height1 = dm1.heightPixels;
        int width1 = dm1.widthPixels;
        return  width1;

    }

    private void parseJSONObject(String jsonData){
        try{
            JSONObject jsonObject=new JSONObject(jsonData);
            JSONArray jsonArray=jsonObject.getJSONArray("data");
            for(int i=0;i<jsonArray.length();i++){
                PictureAdapter picture=new PictureAdapter();
                JSONObject json=jsonArray.getJSONObject(i);
                String name=json.getString("username");
                String title=json.getString("text");
                String touxiang=json.getString("header");
                String pic=json.getString("image");

                picture.setTitle(title);
                picture.setUsername(name);
                picture.setTouxiang(touxiang);
                picture.setPic(pic);

                pictureList.add(picture);
                Log.d("lklk","code:"+name);
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.what = UPDATE;
                    handler.sendMessage(message);
                }
            }).start();


        }catch (Exception e){
            e.printStackTrace();
        }
    }



    private String getRandomLengthContent(String content){
        Random random =new Random();
        int length=random.nextInt(20)+1;
        StringBuilder builder=new StringBuilder();
        for(int i=1;i<length;i++){
            builder.append(content);
        }
        return builder.toString();
    }



    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{
        private List<PictureAdapter> mPicturesList;

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView picturesTitleText;
            TextView picturesUsername;
            ImageView picturesTouxiang;
            ImageView picturesPic;

            public ViewHolder(View view){
                super(view);
                picturesTitleText =(TextView) view.findViewById(R.id.picture_title);
                picturesUsername=(TextView) view.findViewById(R.id.picture_name);
                picturesTouxiang=(ImageView) view.findViewById(R.id.TouXiang);
                picturesPic=(ImageView)view .findViewById(R.id.picture_pic);

            }
        }

        public NewsAdapter(List<PictureAdapter> picturesList){
            mPicturesList=picturesList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
            View view=LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.picture_item,parent,false);
            final ViewHolder holder = new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    PictureAdapter picture =mPicturesList.get(holder.getAdapterPosition());

                }
            });
            return holder;
        }
        @Override
        public void onBindViewHolder(ViewHolder holder,int position){
            PictureAdapter picture =mPicturesList.get(position);
            holder.picturesTitleText.setText(picture.getTitle());
            holder.picturesUsername.setText(picture.getUsername());
            Glide.with(PictureTitleFragment.this)
                    .load(picture.getTouxiang())
                    .into(holder.picturesTouxiang);
            Glide.with(PictureTitleFragment.this)
                    .load(picture.getPic())
                    .override(getScreen(),750)
                    .centerCrop()
                    .into(holder.picturesPic);

        }

        @Override
        public int getItemCount(){
            return mPicturesList.size();
        }
    }

    public void onSaveInstanceState(Bundle outState){
        //super.onSaveInstanceState(outState);
    }
}