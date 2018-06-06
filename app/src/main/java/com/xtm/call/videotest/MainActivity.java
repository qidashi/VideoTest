package com.xtm.call.videotest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xtm.call.api.VideoUrl;

public class MainActivity extends AppCompatActivity {
    private  String VIDEO_PATH = VideoUrl.VIDEO_PATH0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 1.调用系统播放器播放
    public void sys_play(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri  uri=Uri.parse(VIDEO_PATH);
        intent.setDataAndType(uri,"video/*");// 1.Intent设置视频源及类型
        startActivity(intent);// 2.启动Intent
    }

    // 2.使用VideoView播放
    public void vv_play(View view) {
        Intent intent = new Intent(this, VideoViewActivity.class);
        intent.putExtra("video_path",VIDEO_PATH);
        startActivity(intent);
    }

    // 3.利用MediaPlayer+SurfaceView播放视频
    public void mp_sv_play(View view) {
        Intent intent = new Intent(this, SurfaceViewActivity.class);
        intent.putExtra("video_path",VIDEO_PATH);
        startActivity(intent);
    }

    // 4.利用MediaPlayer+TextureView播放视频
    public void mp_tv_play(View view) {
        Intent intent = new Intent(this, TextureViewActivity.class);
        intent.putExtra("video_path",VIDEO_PATH);
        startActivity(intent);
    }

    // 5.利用第三方库播放
    public void other_lib_play(View view) {
        Intent intent = new Intent(this, OtherViewActivity.class);
        intent.putExtra("video_path",VIDEO_PATH);
        startActivity(intent);
    }
}
