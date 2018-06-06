package com.xtm.call.videotest;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class SurfaceViewActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private SurfaceHolder holder;
    private String video_path;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface_view);
        initView();
        initData();
    }

    private void initData() {
        setTitle("SurfaceView展示视频");
        Intent intent = getIntent();
        video_path = intent.getStringExtra("video_path");
        if (!TextUtils.isEmpty(video_path)) {
            mediaPlayer = new MediaPlayer();
            holder = surfaceView.getHolder();
            holder.addCallback(this);
        } else {
            Toast.makeText(this, "播放地址无效！", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        surfaceView =  findViewById(R.id.surfaceView);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        play();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mediaPlayer.stop();
        mediaPlayer.release();
    }
    public void btn_play(View view) {
        play();
    }

    public void btn_pause(View view) {
        pause();
    }

    public void btn_stop(View view) {
        stop();
    }

    private void play() {
        if(currentPosition>0){
            mediaPlayer.seekTo(currentPosition);
            mediaPlayer.start();
        }else {
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(video_path);// 1.MediaPlayer设置视频源
                mediaPlayer.setDisplay(surfaceView.getHolder());// 2.MediaPlayer关联SurfaceView
                mediaPlayer.prepare();
                mediaPlayer.setOnPreparedListener(this);
                mediaPlayer.setOnCompletionListener(this);
                mediaPlayer.setOnErrorListener(this);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    private void pause() {
        if(mediaPlayer.isPlaying()){
            currentPosition = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
        }
    }
    private void stop() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            currentPosition=0;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Toast.makeText(this, "播放完毕！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();// 3.调用MediaPlayer的start()
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Toast.makeText(this, "播放出错！", Toast.LENGTH_SHORT).show();
        return false;
    }
}
