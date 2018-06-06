package com.xtm.call.videotest;

import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class TextureViewActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

    private TextureView textureView;
    private MediaPlayer mediaPlayer;
    private String video_path;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texture_view);
        initView();
        initData();
    }

    private void initData() {
        setTitle("TextureView展示视频");
        Intent intent = getIntent();
        video_path = intent.getStringExtra("video_path");
        if (!TextUtils.isEmpty(video_path)) {
            mediaPlayer = new MediaPlayer();
            textureView.setSurfaceTextureListener(this);
        } else {
            Toast.makeText(this, "播放地址无效！", Toast.LENGTH_SHORT).show();
        }
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

    private void initView() {
        textureView =  findViewById(R.id.textureView);
    }

    private void play() {
        if(currentPosition>0){
            mediaPlayer.seekTo(currentPosition);
            mediaPlayer.start();
        }else {
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(video_path);// 1.MediaPlayer设置视频源
                mediaPlayer.setSurface(new Surface(textureView.getSurfaceTexture()));// 2.MediaPlayer关联TextureView
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
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        play();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mediaPlayer.stop();
        mediaPlayer.release();
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();// 3.调用MediaPlayer的start()
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Toast.makeText(this, "播放完毕！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Toast.makeText(this, "播放出错！", Toast.LENGTH_SHORT).show();
        return false;
    }
}
