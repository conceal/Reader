package com.example.syz.demo.util;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;

public class VideoSurfaceView extends SurfaceView implements SurfaceHolder.Callback,
        MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener {
    private SurfaceHolder mHolder;
    private MediaPlayer mediaPlayer;
    Handler handler;
    private int totalTime = 0;
    private String mUrl;

    public VideoSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init(){
        mHolder = getHolder();
        mHolder.addCallback(this);
    }
    public void playVideo(String url){
        if(mediaPlayer == null){
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(this);
        }
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.setDisplay(mHolder);
            mediaPlayer.prepareAsync();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void stop(){
        if(mediaPlayer != null){
            mediaPlayer.stop();
        }
    }
    public MediaPlayer getMediaPlayer(){
        return mediaPlayer;
    }
    public void pause(){
        if(mediaPlayer != null){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
            }else{
               mediaPlayer.start();
            }
        }
    }
    public int totalTime(){
        if(totalTime != 0){
            return totalTime;
        }else{
            return 0;
        }
    }
    public SurfaceHolder getSurfaceHolder(){
        return mHolder;
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(mediaPlayer!=null){
//            handler.removeMessages(10);
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
    }
}
