package com.waoqi.msettopboxs.ui.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.waoqi.msettopboxs.R;
import com.waoqi.msettopboxs.presenter.VideoViewPresenter;
import com.waoqi.msettopboxs.view.MyMediaController;
import com.waoqi.mvp.mvp.XActivity;

import me.jessyan.autosize.internal.CustomAdapt;

public class VideoViewActivty extends XActivity<VideoViewPresenter> implements CustomAdapt {


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
//            videoView.initVideo(localVideoPath, "http://cs.vmoiver.com/Uploads/Banner/2016/12/27/58622f0fd756b.jpg", "标题");

            videoView.setVideoPath(localVideoPath);
        } else {
//            videoView.initVideo(path, "http://cs.vmoiver.com/Uploads/Banner/2016/12/27/58622f0fd756b.jpg", "标题");
            videoView.setVideoPath(path);
        }

//        videoView.setVideoPath(localVideoPath);
        //读取放在raw目录下的文件
//        videoView.setVideoURI(Uri.parse("android.resource://com.jay.videoviewdemo/" + R.raw.lesson));
        videoView.setMediaController(new MyMediaController(this));
//        videoView.setMediaController(new MediaController(this));

        videoView.start();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_video_view;
    }

    @Override
    public VideoViewPresenter newP() {
        return new VideoViewPresenter();
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 667;
    }


}
