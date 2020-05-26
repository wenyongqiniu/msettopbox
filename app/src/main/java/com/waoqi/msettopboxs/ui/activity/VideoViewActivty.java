package com.waoqi.msettopboxs.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.VideoView;

import com.waoqi.msettopboxs.R;
import com.waoqi.msettopboxs.presenter.VideoViewPresenter;
import com.waoqi.mvp.mvp.XActivity;

public class VideoViewActivty extends XActivity<VideoViewPresenter> {

    private VideoView videoView;

    @Override
    public void initView() {
        videoView = (VideoView) findViewById(R.id.videoView);

    }

    public String localVideoPath;

    @Override
    public void initData(Bundle savedInstanceState) {
        localVideoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/x264.mp4";

        String path = getIntent().getStringExtra("video");
        boolean local = getIntent().getBooleanExtra("local", false);

        if (local) {
            videoView.setVideoPath(localVideoPath);
        } else {
            videoView.setVideoPath(path);
        }

//        videoView.setVideoPath(localVideoPath);
        //读取放在raw目录下的文件
        //videoView.setVideoURI(Uri.parse("android.resource://com.jay.videoviewdemo/" + R.raw.lesson));
        videoView.setMediaController(new MediaController(this));
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_video_view;
    }

    @Override
    public VideoViewPresenter newP() {
        return new VideoViewPresenter();
    }


}
