package com.xtm.call.videotest;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoViewActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    private String video_path;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initData();
    }

    private void initData() {
        setTitle("VideoView展示视频");
//        String video_path = "android.resource://"+getPackageName()+"/"+R.raw.qcw;//播放本地视频
        Intent intent = getIntent();
        video_path = intent.getStringExtra("video_path");
        if(!TextUtils.isEmpty(video_path)){
            videoView = findViewById(R.id.video_view);
            videoView.setOnPreparedListener(this);
            videoView.setOnCompletionListener(this);
            videoView.setOnErrorListener(this);
            videoView.setMediaController(new MediaController(this));
            Uri uri=Uri.parse(video_path);
            videoView.setVideoURI(uri);// 1.VideoView设置视频源
        }else {
            Toast.makeText(this, "视频地址无效！", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Toast.makeText(this, "播放完毕！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        videoView.start();// 2.VideoView调用start()
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Toast.makeText(this, "播放出错！", Toast.LENGTH_SHORT).show();
        return false;
    }
}
