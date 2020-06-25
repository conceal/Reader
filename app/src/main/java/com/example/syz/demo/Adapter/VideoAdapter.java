package com.example.syz.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.syz.demo.R;
import com.example.syz.demo.screenPage.VideoShowActivity;
import com.example.syz.demo.util.Gif;
import com.example.syz.demo.util.MyAppcation;
import com.example.syz.demo.util.VideoData;
import com.example.syz.demo.util.VideoSurfaceView;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.sharesdk.onekeyshare.OnekeyShare;
import de.hdodenhof.circleimageview.CircleImageView;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder>{
    private List<VideoData> mVideoList;
    private VideoSurfaceView mPlayView;
    private static final int UPDATE = 10;
    private int currentPosition = 0;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView headerImage;
        ImageView goodImage;
        ImageView shareImage;
        ImageView commentImage;
        ImageView cover;
        TextView username;
        TextView text;
        TextView goodNumber;
        TextView commentNumber;
        TextView shareNumber;
        TextView title;
        VideoSurfaceView mSurfaceView;
        ImageButton mPlayBtn;
        ImageView mPlayBtnPause;
        ImageView btnMax;
        TextView totalTime;
        TextView currentTime;
        SeekBar seekBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headerImage = (CircleImageView) itemView.findViewById(R.id.video_header);
            goodImage = (ImageView)itemView.findViewById(R.id.video_good_image);
            username = (TextView)itemView.findViewById(R.id.video_username);
            text = (TextView)itemView.findViewById(R.id.video_text);
            goodNumber = (TextView)itemView.findViewById(R.id.video_good_number);
            commentNumber = (TextView)itemView.findViewById(R.id.video_comment_number);
            shareNumber = (TextView)itemView.findViewById(R.id.video_share_number);
            commentImage = (ImageView)itemView.findViewById(R.id.video_comment_image);
            shareImage = (ImageView)itemView.findViewById(R.id.video_share_image);
            cover = (ImageView)itemView.findViewById(R.id.video_cover);
            title = (TextView)itemView.findViewById(R.id.video_title);
            mSurfaceView = (VideoSurfaceView)itemView.findViewById(R.id.video_view);
            mPlayBtn = (ImageButton)itemView.findViewById(R.id.image_clickplay_more);
            mPlayBtnPause = (ImageView)itemView.findViewById(R.id.image_clickplay);
            btnMax = (ImageView)itemView.findViewById(R.id.bt_maxsize);
            totalTime = (TextView)itemView.findViewById(R.id.tv_totalProgress);
            currentTime = (TextView)itemView.findViewById(R.id.tv_currentProgress);
            seekBar = (SeekBar)itemView.findViewById(R.id.seekBar);
        }
    }

    public VideoAdapter(List<VideoData> videoList) {
        mVideoList = videoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_video,viewGroup,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.goodImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                VideoData mVideo = mVideoList.get(position);
                int mUp = mVideo.getCollectionCount() + 1;
                holder.goodImage.setImageResource(R.drawable.text_good_fill);
                holder.goodImage.setColorFilter(Color.RED);
                holder.goodNumber.setText(mUp+" ");
            }
        });
        holder.btnMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                VideoData video = mVideoList.get(position);
                //holder.mSurfaceView.getMediaPlayer().release();
                Intent intent = new Intent(view.getContext(),VideoShowActivity.class);
                intent.putExtra("playUrl",video.getPlayUrl());
                intent.putExtra("imgUrl",video.getCover());
                view.getContext().startActivity(intent);
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final VideoData video = mVideoList.get(position);
        holder.mSurfaceView.stop();
        holder.mSurfaceView.setVisibility(View.VISIBLE);


        holder.mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler() {
                    public void handleMessage(Message message) {
                        switch (message.what) {
                            case UPDATE:
                                if(holder.mSurfaceView.getMediaPlayer() != null){
                                    int current = holder.mSurfaceView.getMediaPlayer().getCurrentPosition()/1000;
                                    holder.currentTime.setText(current/60+":"+current%60);
                                    holder.seekBar.setProgress(holder.mSurfaceView.getMediaPlayer().getCurrentPosition());//将媒体播放器当前播放的位置赋值给进度条的进度
                                }
                                break;
                            default:
                                break;
                        }
                    }
                };
                if(mPlayView != null){
                    mPlayView.stop();
                    mPlayView.setVisibility(View.VISIBLE);
                }
                holder.mSurfaceView.setVisibility(View.VISIBLE);
                holder.mSurfaceView.playVideo(video.getPlayUrl());
                holder.mPlayBtn.setVisibility(View.GONE);
                holder.cover.setVisibility(View.GONE);
                holder.mSurfaceView.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        holder.mPlayBtn.setVisibility(View.VISIBLE);
                        holder.cover.setVisibility(View.VISIBLE);
                    }
                });
                holder.mSurfaceView.getMediaPlayer().setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(final MediaPlayer mp) {
                            int mtotalTime = mp.getDuration();
                            holder.seekBar.setMax(mtotalTime);
                            int mTotal = mtotalTime/1000;
                            holder.totalTime.setText(mTotal/60+":"+mTotal%60);
                            Timer timer = new Timer();
                            TimerTask task = new TimerTask() {
                                public void run() {
                                        Message message = new Message();
                                        message.what = UPDATE;
                                        //message.arg1 = mp.getCurrentPosition()/1000;
                                        handler.sendMessage(message);
                                }
                            };
                            timer.schedule(task, 0, 100);//0秒后执行，每隔100ms执行一次

                    }
                });
                holder.mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                    @Override
                    public void surfaceCreated(SurfaceHolder mholder) {

                    }

                    @Override
                    public void surfaceChanged(SurfaceHolder mholder, int format, int width, int height) {
                    }

                    @Override
                    public void surfaceDestroyed(SurfaceHolder mholder) {
                        holder.mPlayBtn.setVisibility(View.VISIBLE);
                        holder.cover.setVisibility(View.VISIBLE);
                    }
                });
                mPlayView = holder.mSurfaceView;
            }
        });
        holder.mPlayBtnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayView.pause();
                holder.mPlayBtnPause.setVisibility(View.GONE);
            }
        });
        holder.mSurfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.mPlayBtnPause.getVisibility() == View.GONE){
                    holder.mPlayBtnPause.setVisibility(View.VISIBLE);
                }else{
                    holder.mPlayBtnPause.setVisibility(View.GONE);
                }
            }
        });
        holder.shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare(video.getTitle(),video.getPlayUrl());
            }
        });
        Glide.with(MyAppcation.getContext())
                .load(video.getAuthorImg())
                .into(holder.headerImage);
        holder.username.setText(video.getAuthorname());
        holder.text.setText(video.getDescription());
        holder.title.setText(video.getTitle());
        holder.goodNumber.setText(video.getCollectionCount()+" ");
        holder.commentNumber.setText(video.getReplyCount()+" ");
        holder.shareNumber.setText(video.getShareCount()+" ");
        Glide.with(MyAppcation.getContext())
                .load(video.getCover())
                .into(holder.cover);

    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }
    private void showShare(String text,String url) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("请食用：");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(text);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("http://ww1.sinaimg.cn/large/005T39qaly1g0ml0t8kkej30hs0hst92.jpg");//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl(url);
        // 启动分享GUI
        oks.show(MyAppcation.getContext());
    }

}
