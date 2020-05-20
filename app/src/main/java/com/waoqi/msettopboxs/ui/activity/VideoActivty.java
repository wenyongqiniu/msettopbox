package com.waoqi.msettopboxs.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.widget.Toast;
import android.widget.VideoView;

import com.socks.library.KLog;
import com.waoqi.msettopboxs.R;
import com.waoqi.mvp.mvp.XActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.jzvd.JZUtils;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class VideoActivty extends XActivity {

    private JzvdStd jzvd;
    public String localVideoPath;

    @Override
    public void initView() {
        jzvd = (JzvdStd) findViewById(R.id.jzvd);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        localVideoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/local_video.mp4";
        if (!new File(localVideoPath).exists()) {
            cpAssertVideoToLocalPath();
        }

        jzvd.setUp(localVideoPath, "视频播放");
        jzvd.startVideo();
    }


//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        KLog.a("wlx", "按下  " + ev.getAction());
//        return super.dispatchTouchEvent(ev);
//    }

    @Override
    protected void onPause() {
        super.onPause();
        JZUtils.clearSavedProgress(this, null);
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }


    public void cpAssertVideoToLocalPath() {
        if (new File(localVideoPath).exists()) return;

        try {
            InputStream myInput;
            OutputStream myOutput = new FileOutputStream(localVideoPath);
            myInput = this.getAssets().open("local_video.mp4");
            byte[] buffer = new byte[1024];
            int length = myInput.read(buffer);
            while (length > 0) {
                myOutput.write(buffer, 0, length);
                length = myInput.read(buffer);
            }
            myOutput.flush();
            myInput.close();
            myOutput.close();
            Toast.makeText(this, "cp from assert to local path succ", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    public Object newP() {
        return null;
    }
}
