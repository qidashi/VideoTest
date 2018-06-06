package com.xtm.call.videotest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class OtherViewActivity extends AppCompatActivity implements MediaPlayer.OnInfoListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

    private TextView bufferPercent;
    private TextView netSpeed;
    private VideoView videoView;
    private String video_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 1.检查Vitamio框架是否可用
        if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        }
        setContentView(R.layout.activity_other_view);
        initView();
        initData();
    }

    private void initData() {
        setTitle("Vitamio展示视频");
        Intent intent = getIntent();
        video_path = intent.getStringExtra("video_path");
        if (!TextUtils.isEmpty(video_path)) {
            if (Vitamio.isInitialized(this)) {
                videoView.setVideoURI(Uri.parse(video_path));// 2.VideoView设置视频源
                videoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
                videoView.setMediaController(new MediaController(this));
//                videoView.setBufferSize(1024); //设置视频缓冲大小。默认1024KB，单位byte
                videoView.requestFocus();
                videoView.setOnCompletionListener(this);
                videoView.setOnInfoListener(this);
                videoView.setOnErrorListener(this);
            }else {
                Toast.makeText(this, "Vitamio 未初始化！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "播放地址无效！", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        videoView = findViewById(R.id.vitamio);
        bufferPercent =  findViewById(R.id.buffer_percent);
        netSpeed =  findViewById(R.id.net_speed);
    }

    @Override
    public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                bufferPercent.setVisibility(View.VISIBLE);
                netSpeed.setVisibility(View.VISIBLE);
                mediaPlayer.pause();
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                bufferPercent.setVisibility(View.GONE);
                netSpeed.setVisibility(View.GONE);
                mediaPlayer.setPlaybackSpeed(1.0f);
//                mediaPlayer.setLooping(true);
                mediaPlayer.start();
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                netSpeed.setText("当前网速:" + extra + "kb/s");
                break;
        }
        return true;
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
