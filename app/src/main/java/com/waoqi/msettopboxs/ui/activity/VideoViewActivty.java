package com.waoqi.msettopboxs.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.socks.library.KLog;
import com.waoqi.msettopboxs.R;
import com.waoqi.msettopboxs.view.JzvdTvStd;
import com.waoqi.mvp.mvp.XActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.jzvd.JZMediaSystem;
import cn.jzvd.JzvdStd;

public class VideoViewActivty extends XActivity {

    private JzvdTvStd video;

    @Override
    public void initView() {
        video = (JzvdTvStd) findViewById(R.id.video);
    }

    public String localVideoPath;

    @Override
    public void initData(Bundle savedInstanceState) {
        localVideoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/x264.mp4";
        if (new File(localVideoPath).exists()) {
            KLog.a("wlx", "存在视频");
        } else {
            KLog.a("wlx", "不存在视频");
        }
        //根据文件路径播放
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            video.setUp(localVideoPath, "视频播放", JzvdStd.SCREEN_FULLSCREEN, JZMediaSystem.class);
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_video_view;
    }

    @Override
    public Object newP() {
        return null;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT://向左
                KLog.e("wlx", "向左快退");
                video.quickRetreatProgress();
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT://向右
                KLog.e("wlx", "向右快进");
                video.fastForwardProgress();
                break;

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER://中
                video.center();
                break;
//            case KeyEvent.KEYCODE_DPAD_LEFT://向左
//            case KeyEvent.KEYCODE_DPAD_RIGHT://向右
//                startDismissControlViewTimer();
//                break;
        }

        return super.onKeyUp(keyCode, event);

    }


}
