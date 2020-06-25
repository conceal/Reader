package com.example.syz.demo.screenPage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.syz.demo.R;
import com.example.syz.demo.util.MyAppcation;
import com.example.syz.demo.util.VideoSurfaceView;

import java.util.Timer;
import java.util.TimerTask;

public class VideoShowActivity extends AppCompatActivity {
    private static final int UPDATE = 11;
    private TextView currentTime;
    private VideoSurfaceView mSurfaceView;
    private View bindingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_show);
        Intent intent = getIntent();
        final String playUrl = intent.getStringExtra("playUrl");
        final String imgUrl = intent.getStringExtra("imgUrl");
        Log.d("playUrl",playUrl);
        final ImageView cover = (ImageView)findViewById(R.id.video_cover_show);
        mSurfaceView = (VideoSurfaceView)findViewById(R.id.video_view_show);
        final ImageButton mPlayBtn = (ImageButton)findViewById(R.id.image_clickplay_more_show);
        final ImageView mPlayBtnPause = (ImageView)findViewById(R.id.image_clickplay_show);
        final TextView totalTime =(TextView)findViewById(R.id.tv_totalProgress_show);
        currentTime =(TextView)findViewById(R.id.tv_currentProgress_show);
        bindingView = (View) findViewById(R.id.bindingView);
        final SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar_show);
        ImageView btnMax = (ImageView)findViewById(R.id.bt_maxsize_show);
        Glide.with(VideoShowActivity.this)
                .load(imgUrl)
                .into(cover);
        mSurfaceView.stop();
        mSurfaceView.setVisibility(View.VISIBLE);
        final Handler handler = new Handler() {
            public void handleMessage(Message message) {
                switch (message.what) {
                    case UPDATE:
                        if(mSurfaceView.getMediaPlayer() != null){
                            int current = mSurfaceView.getMediaPlayer().getCurrentPosition()/1000;
                            currentTime.setText(current/60+":"+current%60);
                            seekBar.setProgress(mSurfaceView.getMediaPlayer().getCurrentPosition());//将媒体播放器当前播放的位置赋值给进度条的进度
                        }
                       break;
                    default:
                        break;
                }
            }
        };
        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSurfaceView.playVideo(playUrl);
                mPlayBtn.setVisibility(View.GONE);
                cover.setVisibility(View.GONE);
                mSurfaceView.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mPlayBtn.setVisibility(View.VISIBLE);
                        cover.setVisibility(View.VISIBLE);
                    }
                });
                mSurfaceView.getMediaPlayer().setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                            int mtotalTime = mSurfaceView.getMediaPlayer().getDuration();
                            seekBar.setMax(mtotalTime);
                            int mTotal = mtotalTime/1000;
                            totalTime.setText(mTotal/60+":"+mTotal%60);
                            Timer timer = new Timer();
                            TimerTask task = new TimerTask() {
                                public void run() {
                                    Message message = new Message();
                                    message.what = UPDATE;
                                    handler.sendMessage(message);
                                }
                            };
                            timer.schedule(task, 0, 100);//0秒后执行，每隔100ms执行一次

                    }
                });

                mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                    @Override
                    public void surfaceCreated(SurfaceHolder mholder) {

                    }

                    @Override
                    public void surfaceChanged(SurfaceHolder mholder, int format, int width, int height) {
                    }

                    @Override
                    public void surfaceDestroyed(SurfaceHolder mholder) {
                        mPlayBtn.setVisibility(View.VISIBLE);
                        cover.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        mPlayBtnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSurfaceView.pause();
                mPlayBtnPause.setVisibility(View.GONE);
            }
        });
        mSurfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPlayBtnPause.getVisibility() == View.GONE){
                    mPlayBtnPause.setVisibility(View.VISIBLE);
                }else{
                    mPlayBtnPause.setVisibility(View.GONE);
                }

            }
        });

        DarkStatusBar();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    private void LightStatusbar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option =  View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        int statusBarHeight = getStatusBarHeight(this);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bindingView.getLayoutParams();
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
}
